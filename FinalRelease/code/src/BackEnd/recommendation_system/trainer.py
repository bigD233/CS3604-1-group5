import numpy as np
import os
import tensorflow.compat.v1 as tf

ZERO = 1e-6


class Trainer(object):
  """ Trainer for RL agents """
  def __init__(self, ra_type, actor, critic, data_reader, params) -> None:
    """
    ra_type --- str, ["lra", "hra"]
        Type of RL agent
    actor --- nets.PolicyNetwork
        Actor to be trained or evaluated
    critic --- nets.ValueNetwork
        Critic to be trained or evaluated
    data_reader --- data_reader.DataReader
        Data reader of training data
    params --- dict
        Hyperparameters
    """
    self.type = ra_type

    self.actor = actor
    self.critic = critic
    self.dr = data_reader

    self.train_mode = True

    self.n_epochs = params["num_epochs"]
    self.ds = params["dataset"]
    self.batch_size = params["batch_size"]
    self.rnn_max_len = params["rnn_max_len"]
    self.user_field_num = params["user_field_num"]
    self.doc_field_num = params["doc_field_num"]
    self.con_field_num = params["con_field_num"]
    self.net_sync_freq = params["double_networks_sync_freq"]

  def train(self) -> None:
    self.train_mode = True

  def eval(self) -> None:
    self.train_mode = False

  def work(self) -> None:
    sess = tf.Session()
    saver = tf.train.Saver(max_to_keep=1)
    g_init_op = tf.global_variables_initializer()

    if os.path.exists("ckpts") and len(os.listdir("ckpts")):
      model_file = tf.train.latest_checkpoint("ckpts")
      saver.restore(sess, model_file)
    else:
      sess.run(g_init_op)
      if not os.path.exists("ckpts"):
        os.mkdir("ckpts")

    for i_epoch in range(self.n_epochs if self.train_mode else 1):
      if i_epoch > 0:
        self.dr.load(self.ds)

      data = self.dr.next()
      batch_cnt = 0

      while data is not None:
        self._one_step(sess, data)
        data = self.dr.next()
        batch_cnt += 1

        if batch_cnt % 10 == 0:
          a_loss = self.actor.total_loss / (self.actor.batch_counter + ZERO)
          c_loss = self.critic.total_loss / (self.critic.batch_counter + ZERO)
          epoch_info = f"Epoch {i_epoch+1:2d}"
          batch_info = f"Batch {batch_cnt:3d}"
          a_info = f"{a_loss:.5f}"
          c_info = f"{c_loss:.5f}"
          print(f"| {epoch_info} | {batch_info} | {a_info} | {c_info} |")

    saver.save(sess, f"ckpts/{self.type}.ckpt")

  def predict(self):
    sess = tf.Session()
    saver = tf.train.Saver(max_to_keep=1)

    if os.path.exists("saved_model") and len(os.listdir("saved_model")):
      model_file = tf.train.latest_checkpoint("saved_model")
      saver.restore(sess, model_file)
    else:
      raise FileNotFoundError("No model found in saved_model/")

    data = self.dr.next()
    a_loss = 0
    a_loss_list = []
    while data is not None:
      self._one_step(sess, data)
      data = self.dr.next()

      a_loss = self.actor.total_loss - a_loss
      a_loss_list.append(a_loss)

    if sum(a_loss_list) == 0:
      a_loss_list = list(np.random.randn(len(a_loss_list)))

    return a_loss_list

  def _one_step(self, sess, sess_data):
    def gen_sparse_tensor(fs):
      kk, vv = [], []

      for i in range(len(fs)):
        ff = fs[i]

        assert isinstance(ff, set)

        ff = list(ff)
        for k in range(len(ff)):
          kk.append(np.array([i, k], dtype=np.int32))
          vv.append(ff[k])

      return tf.SparseTensorValue(
          kk, vv, [len(fs), self.dr.unique_feature_num()])

    if len(sess_data) != self.batch_size * self.rnn_max_len:
      return

    user = list(map(lambda x: x[2], sess_data))
    doc = list(map(lambda x: x[3], sess_data))
    con = list(map(lambda x: x[4], sess_data))
    rew = list(map(lambda x: x[5], sess_data))
    ret = list(map(lambda x: x[6], sess_data))

    phd = {}
    user = np.array(user).reshape(self.batch_size * self.rnn_max_len *
                                  self.user_field_num)
    doc = np.array(doc).reshape(self.batch_size * self.rnn_max_len *
                                self.doc_field_num)
    con = np.array(con).reshape(self.batch_size * self.rnn_max_len *
                                self.con_field_num)
    phd["user"] = gen_sparse_tensor(user)
    phd["doc"] = gen_sparse_tensor(doc)
    phd["con"] = gen_sparse_tensor(con)
    phd["reward"] = rew

    if self.train_mode:
      q_ = self.critic.predict_for_train(sess, phd)
      q_ = np.append(q_[:, 1:], np.zeros((self.batch_size, 1),
                                         dtype=np.float32), 1)
      phd["next_q"] = q_

      self.critic.learn(sess, phd)
      if self.critic.batch_counter % self.net_sync_freq == 0:
        print(">>> Run soft replacement for value networks <<<")
        sess.run(self.critic.sync_op)

      phd["action"] = self.actor.act(sess, phd)
      phd["a_grads"] = self.critic.pg(sess, phd)

      self.actor.learn(sess, phd)
      if self.actor.batch_counter % self.net_sync_freq == 0:
        print(">>> Run soft replacement for policy networks <<<")
        sess.run(self.actor.sync_op)
    else:
      cq = self.critic.predict_for_eval(sess, phd).reshape([-1])
      phd["action"] = self.actor.act(sess, phd)
      aq = self.critic.critic(sess, phd).reshape([-1])
