from data_reader import DataReader
from nets import PolicyNetwork, ValueNetwork
from trainer import Trainer
from data_extract import get_full_user_df, get_case_tags, get_ques_tags, get_tags, get_user_df
from generate_trainset import generate_rec_list, dump_trainset, _hash
from hyper_param import param_dict

import numpy as np
import os
import random
import tensorflow.compat.v1 as tf
import time

os.environ["TF_CPP_MIN_LOG_LEVEL"] = "3"
tf.disable_eager_execution()

CASE_TAG_DF = get_case_tags()
QUES_TAG_DF = get_ques_tags()

_all_tags = get_tags().loc[:, "tag_id"].to_list()
_hashed_tag_ids = list(map(lambda i: _hash(i), _all_tags))
HASH_TBL = dict(zip(_hashed_tag_ids, _all_tags))
del _all_tags, _hashed_tag_ids

TAGS_ATTACHED_TO_CASE = CASE_TAG_DF.loc[:, "tag_id"].to_list()
TAGS_ATTACHED_TO_QUES = QUES_TAG_DF.loc[:, "tag_id"].to_list()

PATH_TO_SEQS_DATA = os.path.join(".tmp", f"p{os.getpid()}_seqs.temp")


def generate_user_seqs(user_id: str, n_seq: int, init_sid=0):
  def reset_rew_ret(item):
    item["reward"] = 0
    item["return"] = 0

    return item

  seqs = []
  sid = init_sid

  for _ in range(n_seq):
    seq_dict = dict()
    sid += 1
    rec_list = generate_rec_list(get_full_user_df(), user_id, 10)

    seq_dict["sid"] = sid
    seq_dict["offset"] = sid
    seq_dict["rec_list"] = list(map(lambda item: reset_rew_ret(item), rec_list))

    seqs.append(seq_dict)

  return seqs, sid


def dump_user_seqs(seqs):
  if not os.path.exists(".tmp"):
    os.mkdir(".tmp")

  dump_trainset(seqs, PATH_TO_SEQS_DATA)


def initialize_model():
  dr = DataReader(1)
  dr.load(PATH_TO_SEQS_DATA)

  params = param_dict
  params["num_epochs"] = 1
  params["dataset"] = PATH_TO_SEQS_DATA
  params["batch_size"] = 1

  actor = PolicyNetwork("eval", dr.unique_feature_num())
  critic = ValueNetwork("eval", dr.unique_feature_num())
  worker = Trainer("lra", actor, critic, dr, params)

  worker.eval()

  return worker


def recomment_item_by_user(user_id, n_seqs_per_user: int):
  if isinstance(user_id, str):
    users = [user_id]
  elif isinstance(user_id, list):
    users = user_id
  else:
    raise ValueError("Only accept str or list")

  users = list(map(lambda i: str(i), users))

  seqs = []
  sid = 0
  for i in users:
    new_seqs, sid = generate_user_seqs(i, n_seqs_per_user, sid)
    seqs.extend(new_seqs)

  dump_user_seqs(seqs)

  worker = initialize_model()
  losses = worker.predict()

  user_rec_seq_list = []
  for i, user in enumerate(users):
    user_df = get_user_df(user)
    user_case_tags = user_df.loc[:, "case_tags"].item().extend(
        user_df.loc[:, "love_case_tags"].item())
    user_ques_tags = user_df.loc[:, "ques_tags"].item().extend(
        user_df.loc[:, "love_ques_tags"].item())

    i_beg = i * n_seqs_per_user
    i_end = (i + 1) * n_seqs_per_user

    user_losses = np.array(losses[i_beg:i_end])
    best_seq_ind = user_losses.argmin()
    best_seq = seqs[i_beg + best_seq_ind]

    user_rec_seq = {"case": [], "question": []}
    for item in best_seq["rec_list"]:
      case_tag_id = HASH_TBL[random.choice(item["field5"])]
      ques_tag_id = HASH_TBL[random.choice(item["field8"])]

      case_ids = CASE_TAG_DF.loc[CASE_TAG_DF["tag_id"] ==
                                 case_tag_id].loc[:, "case_id"].to_list()
      cnt = 0
      while not case_ids and cnt < 7:
        case_tag_id = HASH_TBL[random.choice(item["field5"])]
        case_ids = CASE_TAG_DF.loc[CASE_TAG_DF["tag_id"] ==
                                   case_tag_id].loc[:, "case_id"].to_list()
        cnt += 1
      if cnt >= 7:
        if not user_case_tags:
          case_tag_id = random.choice(TAGS_ATTACHED_TO_CASE)
        else:
          case_tag_id = random.choice(user_case_tags)
        case_ids = CASE_TAG_DF.loc[CASE_TAG_DF["tag_id"] ==
                                   case_tag_id].loc[:, "case_id"].to_list()

      ques_ids = QUES_TAG_DF.loc[QUES_TAG_DF["tag_id"] ==
                                 ques_tag_id].loc[:, "question_id"].to_list()
      cnt = 0
      while not ques_ids and cnt < 7:
        ques_tag_id = HASH_TBL[random.choice(item["field8"])]
        ques_ids = QUES_TAG_DF.loc[QUES_TAG_DF["tag_id"] ==
                                   ques_tag_id].loc[:, "question_id"].to_list()
        cnt += 1
      if cnt >= 7:
        if not user_ques_tags:
          ques_tag_id = random.choice(TAGS_ATTACHED_TO_QUES)
        else:
          ques_tag_id = random.choice(user_ques_tags)
        ques_ids = QUES_TAG_DF.loc[QUES_TAG_DF["tag_id"] ==
                                   ques_tag_id].loc[:, "question_id"].to_list()

      user_rec_seq["case"].append(random.choice(case_ids))
      user_rec_seq["question"].append(random.choice(ques_ids))

    user_rec_seq_list.append(user_rec_seq)

  os.remove(PATH_TO_SEQS_DATA)
  return user_rec_seq_list


if __name__ == "__main__":
  all_users = get_full_user_df().loc[:, "id"].to_list()
  while 1:
    print(recomment_item_by_user(all_users, 10))
