"""
Implementation of policy and value networks.
"""

import layer_util as lu
from data_reader import DataReader
from hyper_param import param_dict as pd

import tensorflow.compat.v1 as tf

# Load dataset
DS = DataReader(pd["batch_size"])


class PolicyNetwork(object):
  """ Policy network for RL agent """
  def __init__(self, mode, unique_feature_num) -> None:
    """
    mode --- str, ["train", "eval"]
        Specify usage of this network.
    unique_feature_num --- int
        Number of unique features used for building networks.
    """
    # Place holders
    self.sph_user = tf.sparse_placeholder(tf.int32, name='sph_user')
    self.sph_doc = tf.sparse_placeholder(tf.int32, name='sph_doc')
    self.sph_con = tf.sparse_placeholder(tf.int32, name='sph_con')

    # Mode
    self.train_mode = mode == "train"

    # policy gradient
    self.a_grads = tf.placeholder(tf.float32)

    # policy network
    self.doc_embed, self.mpa, self.mea = self.build_net('main',
                                                        unique_feature_num)

    # target network
    _, self.tpa, self.tea = self.build_net('target', unique_feature_num)

    # optional supervised signal, to avoid instability of actions in extreme cases
    self.loss = tf.losses.mean_squared_error(self.doc_embed, self.mea)

    params = tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                               scope='main/policy')
    params.extend(
        tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                          scope='main/feat_embedding'))

    self.grads = tf.clip_by_global_norm(tf.gradients(self.loss, params),
                                        pd['grad_clip'])[0]
    policy_grads = \
        tf.clip_by_global_norm(tf.gradients(ys=self.mea, xs=params, grad_ys=self.a_grads), pd['grad_clip'])[0]

    opt1 = tf.train.AdamOptimizer(-pd['lr'])
    opt2 = tf.train.AdamOptimizer(pd['lr'])
    with tf.variable_scope("train_policy"):
      self.opt_a1 = opt1.apply_gradients(zip(policy_grads, params))
      self.opt_a2 = opt2.apply_gradients(zip(self.grads, params))

    self.m_params = tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                                      scope="main/policy")
    self.m_params.extend(
        tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                          scope='main/feat_embedding'))
    self.t_params = tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                                      scope="target/policy")
    self.t_params.extend(
        tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                          scope='target/feat_embedding'))

    alpha = pd['double_networks_sync_step']
    self.sync_op = [
        tf.assign(t, (1.0 - alpha) * t + alpha * m)
        for t, m in zip(self.t_params, self.m_params)
    ]
    self.total_loss, self.batch_counter = 0.0, 0

  def encode(self, seq_embed, query_embed):
    q = tf.layers.dropout(query_embed,
                          rate=pd['dropout'],
                          training=self.train_mode)
    kv = tf.layers.dropout(seq_embed,
                           rate=pd['dropout'],
                           training=self.train_mode)
    for i in range(pd['encoder_layer']):
      with tf.variable_scope('encoder_%d' % (i + 1)):
        # self-attention
        enc = lu.multihead_attention(queries=q,
                                     keys=kv,
                                     values=kv,
                                     num_heads=pd['head_num'],
                                     dropout_rate=pd['dropout'],
                                     training=self.train_mode,
                                     causality=False,
                                     scope='mha')
        ff_dim = enc.get_shape().as_list()[-1]
        # feed forward
        enc = lu.feed_forward(enc,
                              num_units=[ff_dim, ff_dim],
                              activation=tf.nn.tanh,
                              scope='ff')
    return enc

  def build_net(self, var_scope, unique_feature_num):
    with tf.variable_scope(var_scope, reuse=tf.AUTO_REUSE):
      feat_dict = lu.get_embeddings(unique_feature_num,
                                    pd['feat_dim'],
                                    scope='feat_embedding',
                                    zero_pad=False)
      with tf.variable_scope('policy'):
        usr_embed = tf.reshape(tf.nn.embedding_lookup_sparse(feat_dict,
                                                             self.sph_user,
                                                             sp_weights=None,
                                                             combiner='mean'),
                               shape=[
                                   pd['batch_size'], pd['rnn_max_len'],
                                   pd['user_field_num'] * pd['feat_dim']
                               ])
        doc_embed = tf.reshape(tf.nn.embedding_lookup_sparse(feat_dict,
                                                             self.sph_doc,
                                                             sp_weights=None,
                                                             combiner='mean'),
                               shape=[
                                   pd['batch_size'], pd['rnn_max_len'],
                                   pd['doc_field_num'] * pd['feat_dim']
                               ])
        con_embed = tf.reshape(tf.nn.embedding_lookup_sparse(feat_dict,
                                                             self.sph_con,
                                                             sp_weights=None,
                                                             combiner='mean'),
                               shape=[
                                   pd['batch_size'], pd['rnn_max_len'],
                                   pd['con_field_num'] * pd['feat_dim']
                               ])
        fi_embed = tf.concat([usr_embed, con_embed], axis=2)
        with tf.variable_scope('feature_interaction'):
          fi_embed = self.encode(fi_embed, fi_embed)
        fi_layer = tf.reshape(fi_embed,
                              [pd['batch_size'], pd['rnn_max_len'], -1])
        gru = tf.nn.rnn_cell.GRUCell(fi_layer.get_shape().as_list()[-1])
        drop = tf.nn.rnn_cell.DropoutWrapper(
            gru,
            output_keep_prob=1.0 - pd['dropout'] if self.train_mode else 1.)
        cell = tf.nn.rnn_cell.MultiRNNCell(
            [drop for _ in range(pd['rnn_layer'])])
        init_state = cell.zero_state(pd['batch_size'], tf.float32)
        outputs, _ = tf.nn.dynamic_rnn(cell,
                                       fi_layer,
                                       initial_state=init_state,
                                       time_major=False)
        rnn_out = tf.reshape(outputs,
                             [pd['batch_size'] * pd['rnn_max_len'], -1])
        hdd_out = tf.layers.dropout(rnn_out,
                                    rate=pd['dropout'],
                                    training=self.train_mode)
        p_action = tf.layers.dense(hdd_out,
                                   pd['doc_field_num'] * pd['feat_dim'],
                                   activation=tf.nn.tanh)
        p_action = tf.reshape(p_action,
                              shape=[pd['batch_size'], pd['rnn_max_len'], -1])
        explore_action = tf.truncated_normal(shape=[
            pd['batch_size'], pd['rnn_max_len'],
            pd['doc_field_num'] * pd['feat_dim']
        ],
                                             mean=0,
                                             stddev=pd['actor_explore_range'],
                                             dtype=tf.float32)
        # action with exploration
        e_action = p_action + explore_action
      return doc_embed, p_action, e_action

  def act(self, sess, ph_dict):
    return sess.run(self.mea if self.train_mode else self.mpa,
                    feed_dict={
                        self.sph_user: ph_dict['user'],
                        self.sph_con: ph_dict['con']
                    })

  def learn(self, sess, ph_dict):
    loss, _, _ = sess.run(
        [self.loss, self.opt_a1, self.opt_a2],
        feed_dict={
            self.a_grads: ph_dict['a_grads'],
            self.sph_user: ph_dict['user'],
            self.sph_doc: ph_dict['doc'],
            self.sph_con: ph_dict['con']
        })
    self.batch_counter += 1
    self.total_loss += loss


class ValueNetwork(object):
  def __init__(self, mode, unique_feature_num) -> None:
    """
    mode --- str, ["train", "eval"]
        Specify usage of this network.
    unique_feature_num --- int
        Number of unique features used for building networks.
    """
    # placeholder
    self.sph_user = tf.sparse_placeholder(tf.int32, name='sph_user')
    self.sph_doc = tf.sparse_placeholder(tf.int32, name='sph_doc')
    self.sph_con = tf.sparse_placeholder(tf.int32, name='sph_con')
    self.ph_reward = tf.placeholder(tf.float32, name='ph_reward')
    self.ph_nq = tf.placeholder(tf.float32,
                                shape=[pd['batch_size'], pd['rnn_max_len']],
                                name='ph_nq')

    # Mode
    self.train_mode = mode == "train"

    # main networks
    self.dst_embed, self.mq = self.build_net('main', unique_feature_num)

    # target networks
    _, self.tq = self.build_net('target', unique_feature_num)
    diff = tf.reshape(self.ph_reward, [-1]) + tf.scalar_mul(
        tf.constant(pd['gamma']), tf.reshape(self.ph_nq, [-1])) - tf.reshape(
            self.mq, [-1])

    self.loss = tf.reduce_mean(tf.square(diff))
    self.a_grads = tf.clip_by_global_norm(tf.gradients(self.mq, self.dst_embed),
                                          pd['grad_clip'])[0]
    vs = tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES, scope='main/value')
    vs.extend(
        tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                          scope='main/feat_embedding'))

    self.grads = tf.clip_by_global_norm(tf.gradients(self.loss, vs),
                                        pd['grad_clip'])[0]
    with tf.variable_scope('train_value'):
      optimizer = tf.train.AdamOptimizer(pd['lr'])
      self.opt = optimizer.apply_gradients(zip(self.grads, vs))

    self.m_params = tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                                      scope="main/value")
    self.m_params.extend(
        tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                          scope='main/feat_embedding'))
    self.t_params = tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                                      scope="target/value")
    self.t_params.extend(
        tf.get_collection(tf.GraphKeys.GLOBAL_VARIABLES,
                          scope='target/feat_embedding'))

    alpha = pd['double_networks_sync_step']
    self.sync_op = [
        tf.assign(t, (1.0 - alpha) * t + alpha * m)
        for t, m in zip(self.t_params, self.m_params)
    ]
    self.total_loss, self.batch_counter = 0.0, 0

  def encode(self, seq_embed, query_embed):
    q = tf.layers.dropout(query_embed,
                          rate=pd['dropout'],
                          training=self.train_mode)
    kv = tf.layers.dropout(seq_embed,
                           rate=pd['dropout'],
                           training=self.train_mode)
    for i in range(pd['encoder_layer']):
      with tf.variable_scope('encoder_%d' % (i + 1)):
        # self-attention
        enc = lu.multihead_attention(queries=q,
                                     keys=kv,
                                     values=kv,
                                     num_heads=pd['head_num'],
                                     dropout_rate=pd['dropout'],
                                     training=self.train_mode,
                                     causality=False,
                                     scope='mha')
        ff_dim = enc.get_shape().as_list()[-1]
        # feed forward
        enc = lu.feed_forward(enc,
                              num_units=[ff_dim, ff_dim],
                              activation=tf.nn.tanh,
                              scope='ff')
    return enc

  def build_net(self, var_scope, unique_feature_num):
    with tf.variable_scope(var_scope, reuse=tf.AUTO_REUSE):
      feat_dict = lu.get_embeddings(unique_feature_num,
                                    pd['feat_dim'],
                                    scope='feat_embedding',
                                    zero_pad=False)
      with tf.variable_scope('value'):
        usr_embed = tf.reshape(tf.nn.embedding_lookup_sparse(feat_dict,
                                                             self.sph_user,
                                                             sp_weights=None,
                                                             combiner='mean'),
                               shape=[
                                   pd['batch_size'], pd['rnn_max_len'],
                                   pd['user_field_num'] * pd['feat_dim']
                               ])
        doc_embed = tf.reshape(tf.nn.embedding_lookup_sparse(feat_dict,
                                                             self.sph_doc,
                                                             sp_weights=None,
                                                             combiner='mean'),
                               shape=[
                                   pd['batch_size'], pd['rnn_max_len'],
                                   pd['doc_field_num'] * pd['feat_dim']
                               ])
        con_embed = tf.reshape(tf.nn.embedding_lookup_sparse(feat_dict,
                                                             self.sph_con,
                                                             sp_weights=None,
                                                             combiner='mean'),
                               shape=[
                                   pd['batch_size'], pd['rnn_max_len'],
                                   pd['con_field_num'] * pd['feat_dim']
                               ])
        fi_embed = tf.concat([usr_embed, doc_embed, con_embed], axis=2)
        with tf.variable_scope('feature_interaction'):
          fi_embed = self.encode(fi_embed, fi_embed)
        fi_layer = tf.reshape(fi_embed,
                              [pd['batch_size'], pd['rnn_max_len'], -1])
        gru = tf.nn.rnn_cell.GRUCell(fi_layer.get_shape().as_list()[-1])
        drop = tf.nn.rnn_cell.DropoutWrapper(
            gru,
            output_keep_prob=1.0 - pd['dropout'] if self.train_mode else 1.)
        cell = tf.nn.rnn_cell.MultiRNNCell(
            [drop for _ in range(pd['rnn_layer'])])
        init_state = cell.zero_state(pd['batch_size'], tf.float32)
        outputs, _ = tf.nn.dynamic_rnn(cell,
                                       fi_layer,
                                       initial_state=init_state,
                                       time_major=False)
        rnn_out = tf.reshape(outputs,
                             [pd['batch_size'] * pd['rnn_max_len'], -1])
        hdd_out = tf.layers.dropout(rnn_out,
                                    rate=pd['dropout'],
                                    training=self.train_mode)
        q = tf.layers.dense(hdd_out, 1, activation=tf.nn.relu)
        q = tf.reshape(q, [pd['batch_size'], pd['rnn_max_len']])
    return doc_embed, q

  def critic(self, sess, ph_dict):
    return sess.run(self.mq,
                    feed_dict={
                        self.dst_embed: ph_dict['action'],
                        self.sph_user: ph_dict['user'],
                        self.sph_con: ph_dict['con']
                    })

  def predict_for_train(self, sess, ph_dict):
    return sess.run(self.tq,
                    feed_dict={
                        self.sph_user: ph_dict['user'],
                        self.sph_doc: ph_dict['doc'],
                        self.sph_con: ph_dict['con']
                    })

  def predict_for_eval(self, sess, ph_dict):
    return sess.run(self.mq,
                    feed_dict={
                        self.sph_user: ph_dict['user'],
                        self.sph_doc: ph_dict['doc'],
                        self.sph_con: ph_dict['con']
                    })

  def learn(self, sess, ph_dict):
    loss, _ = sess.run(
        [self.loss, self.opt],
        feed_dict={
            self.ph_nq: ph_dict['next_q'],
            self.ph_reward: ph_dict['reward'],
            self.sph_user: ph_dict['user'],
            self.sph_doc: ph_dict['doc'],
            self.sph_con: ph_dict['con']
        })
    self.batch_counter += 1
    self.total_loss += loss

  # policy gradient
  def pg(self, sess, ph_dict):
    return sess.run(self.a_grads,
                    feed_dict={
                        self.dst_embed: ph_dict['action'],
                        self.sph_user: ph_dict['user'],
                        self.sph_con: ph_dict['con']
                    })
