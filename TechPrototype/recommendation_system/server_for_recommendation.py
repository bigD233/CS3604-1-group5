from CF_user import predict_by_user, train
from data_extract import user_case_mat, user_ques_mat, user_ans_mat, user_tag_mat
from data_reader import IdAllocator
from settings import PORT, NUM_CASE_REC, NUM_QUES_REC, NUM_ANS_REC, NUM_USER_REC, NUM_TAG_REC

from flask import Flask, request

app = Flask(__name__)

CASE_REC_LIST = dict()
QUES_REC_LIST = dict()
ANS_REC_LIST = dict()
USER_REC_LIST = dict()


def select_from(rates, num):
  id_rate_pairs = list(enumerate(rates))[1:]
  id_rate_pairs_sorted = sorted(id_rate_pairs, key=lambda x: x[1], reverse=True)
  id_rate_pairs_sorted = list(
      map(lambda pair: (pair[0] + 1, pair[1]), id_rate_pairs_sorted))

  return list(map(lambda pair: pair[0], id_rate_pairs_sorted[:num]))


def parse_items(items, alloc_dict):
  reversed_dict = dict(
      list(map(lambda pair: (pair[1], pair[0]), alloc_dict.items())))

  return list(map(lambda x: int(reversed_dict[x]), items))


@app.route("/user_to_case", methods=["GET"])
def recommend_case_by_user():
  user_id = request.args.get("user_id")

  if CASE_REC_LIST.get(user_id, None) is None:
    CASE_REC_LIST[user_id] = []

  if len(CASE_REC_LIST[user_id]) < NUM_CASE_REC:
    user_alloc = IdAllocator()
    case_alloc = IdAllocator()

    train_data, user_alloc, case_alloc = user_case_mat(user_alloc, case_alloc)
    rate_list = predict_by_user(train_data, user_id, user_alloc, case_alloc)

    rec_list = select_from(rate_list, len(rate_list))
    rec_list = parse_items(rec_list, case_alloc._tbl)

    CASE_REC_LIST[user_id].extend(rec_list)

  rec_list = CASE_REC_LIST[user_id][:NUM_CASE_REC]
  CASE_REC_LIST[user_id] = CASE_REC_LIST[user_id][NUM_CASE_REC:]

  return rec_list


@app.route("/user_to_ques", methods=["GET"])
def recommend_ques_by_user():
  user_id = request.args.get("user_id")

  if QUES_REC_LIST.get(user_id, None) is None:
    QUES_REC_LIST[user_id] = []

  if len(QUES_REC_LIST[user_id]) < NUM_QUES_REC:
    user_alloc = IdAllocator()
    ques_alloc = IdAllocator()

    train_data, user_alloc, ques_alloc = user_ques_mat(user_alloc, ques_alloc)
    rate_list = predict_by_user(train_data, user_id, user_alloc, ques_alloc)

    rec_list = select_from(rate_list, len(rate_list))
    rec_list = parse_items(rec_list, ques_alloc._tbl)

    QUES_REC_LIST[user_id].extend(rec_list)

  rec_list = QUES_REC_LIST[user_id][:NUM_QUES_REC]
  QUES_REC_LIST[user_id] = QUES_REC_LIST[user_id][NUM_QUES_REC:]

  return rec_list


@app.route("/user_to_answer", methods=["GET"])
def recommend_answer_by_user():
  user_id = request.args.get("user_id")

  if ANS_REC_LIST.get(user_id, None) is None:
    ANS_REC_LIST[user_id] = []

  if len(ANS_REC_LIST[user_id]) < NUM_ANS_REC:
    user_alloc = IdAllocator()
    ans_alloc = IdAllocator()

    train_data, user_alloc, ans_alloc = user_ans_mat(user_alloc, ans_alloc)
    rate_list = predict_by_user(train_data, user_id, user_alloc, ans_alloc)

    rec_list = select_from(rate_list, len(rate_list))
    rec_list = parse_items(rec_list, ans_alloc._tbl)

    ANS_REC_LIST[user_id].extend(rec_list)

  rec_list = ANS_REC_LIST[user_id][:NUM_ANS_REC]
  ANS_REC_LIST[user_id] = ANS_REC_LIST[user_id][NUM_ANS_REC:]

  return rec_list


@app.route("/user_to_user", methods=["GET"])
def recommend_user_by_user():
  user_id = request.args.get("user_id")

  if USER_REC_LIST.get(user_id, None) is None:
    USER_REC_LIST[user_id] = []

  if len(USER_REC_LIST[user_id]) < NUM_USER_REC:
    user_alloc = IdAllocator()
    ques_alloc = IdAllocator()

    train_data, user_alloc, ques_alloc = user_ques_mat(user_alloc, ques_alloc)
    _, similarity = train(train_data, user_alloc.unique_id_num(),
                          ques_alloc.unique_id_num())

    i_user = user_alloc.get_id(user_id)
    similarity = similarity[i_user - 1]

    rec_list = select_from(similarity, len(similarity))
    rec_list = parse_items(rec_list, user_alloc._tbl)

    user_id_int = int(user_id)
    if user_id_int in rec_list:
      del rec_list[rec_list.index(user_id_int)]

    USER_REC_LIST[user_id].extend(rec_list)

  rec_list = USER_REC_LIST[user_id][:NUM_USER_REC]
  USER_REC_LIST[user_id] = USER_REC_LIST[user_id][NUM_USER_REC:]

  return rec_list


@app.route("/user_to_tag", methods=["GET"])
def recommend_tag_by_user():
  user_id = request.args.get("user_id")
  user_alloc = IdAllocator()
  tag_alloc = IdAllocator()

  train_data, user_alloc, tag_alloc = user_tag_mat(user_alloc, tag_alloc)
  rate_list = predict_by_user(train_data, user_id, user_alloc, tag_alloc)

  rec_list = select_from(rate_list, NUM_TAG_REC)
  rec_list = parse_items(rec_list, tag_alloc._tbl)

  return rec_list


@app.route("/")
def print_out_query():
  return "欢迎使用白云社区推荐系统服务！"


if __name__ == "__main__":
  app.run(host="0.0.0.0", port=PORT)
