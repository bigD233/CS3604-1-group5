import hashlib
import numpy as np
import os
import pandas as pd
import random

from data_extract import get_full_user_df, get_tags

FN_TRAIN_SET = "rs_train_data.txt"
PATH_TO_DATA = os.path.join(os.path.split(__file__)[0], "data")


def _hash(data: str) -> str:
  return hashlib.sha256(data.encode()).hexdigest()[:16]


def generate_training_seqs(df: pd.DataFrame, n_seq: int, n_items_per_seq: int):
  train_seqs = []
  sid = 0

  for _ in range(n_seq):
    seq_dict = dict()
    sid += 1
    user_id = random.choice(df.loc[:, "id"].to_list())

    seq_dict["sid"] = sid
    seq_dict["offset"] = sid
    seq_dict["rec_list"] = generate_rec_list(df, user_id, n_items_per_seq)

    train_seqs.append(seq_dict)

  return train_seqs


def generate_rec_list(df: pd.DataFrame, user_id: str, n_items: int):
  tags = get_tags().loc[:, "tag_id"].to_list()
  tags = list(map(lambda tag: _hash(tag), tags))

  rec_list = []
  idx = 0
  user_df = df.loc[df["id"] == user_id]

  username = _hash(user_df.loc[:, "username"].item())
  birthday = _hash(user_df.loc[:, "birthday"].item())
  sex = _hash(user_df.loc[:, "sex"].item())
  height = _hash(user_df.loc[:, "height"].item())
  weight = _hash(user_df.loc[:, "weight"].item())
  cases_created = user_df.loc[:, "case_tags"].item()
  cases_loved = user_df.loc[:, "love_case_tags"].item()
  cases = cases_created + cases_loved
  ques_created = user_df.loc[:, "ques_tags"].item()
  ques_loved = user_df.loc[:, "love_ques_tags"].item()
  questions = ques_created + ques_loved
  cases = list(map(lambda case: _hash(case), cases))
  questions = list(map(lambda ques: _hash(ques), questions))

  field0 = [username, birthday, sex, height, weight]
  field1 = random.choices(cases, k=3) if cases else []
  field2 = random.choices(questions, k=3) if questions else []

  for _ in range(n_items):
    item = dict()

    field3 = random.choices(tags, k=3)
    field4 = random.choices(tags, k=3)
    field5 = field3 + field4
    field6 = random.choices(tags, k=3)
    field7 = random.choices(tags, k=3)
    field8 = field6 + field7

    cnt = 0
    for tag in field3:
      if tag in cases_created:
        cnt += 1
    for tag in field4:
      if tag in cases_loved:
        cnt += 1
    for tag in field6:
      if tag in ques_created:
        cnt += 1
    for tag in field7:
      if tag in ques_loved:
        cnt += 1

    item["idx"] = idx
    item["field0"] = field0
    item["field1"] = field1
    item["field2"] = field2
    item["field3"] = field3
    item["field4"] = field4
    item["field5"] = field5
    item["field6"] = field6
    item["field7"] = field7
    item["field8"] = field8
    item["reward"] = cnt / 6
    item["return"] = cnt / 3 + (np.random.randn() + 1) * random.random()

    rec_list.append(item)
    idx += 1

  return rec_list


def dump_trainset(train_seqs, path=None):
  if not path:
    path = os.path.join(PATH_TO_DATA, FN_TRAIN_SET)

  with open(path, "w") as fp:
    for seq in train_seqs:
      rec_list = seq["rec_list"]
      rec_list_str = "rec_list:"

      for item in rec_list:
        idx_str = f"idx:{item['idx']}"
        rr_str = f"reward:{item['reward']:.1f} return:{item['return']:.1f}"

        field_str = ""
        for i in range(9):
          field_i = item[f"field{i}"]
          field_str = f"{field_str} field{i}:"

          for field_item in field_i:
            field_str = f"{field_str}{field_item},"

          if field_str[-1] == ",":
            field_str = field_str[:-1]

        rec_list_str = f"{rec_list_str}{idx_str}{field_str} {rr_str}|"

      if rec_list_str[-1] == "|":
        rec_list_str = rec_list_str[:-1]

      fp.write(f"sid:{seq['sid']}\toffset:{seq['offset']}\t{rec_list_str}\n")


if __name__ == "__main__":
  dump_trainset(generate_training_seqs(get_full_user_df(), 10000, 10))
