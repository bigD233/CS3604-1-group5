import os
import pandas as pd

_dirs = __file__.split(os.path.sep)
PATH_TO_DATABASE = os.path.join(os.path.sep.join(_dirs[:-2]), "database")
del _dirs

FN_TAGS = "tags.sql"
FN_USERS = "users.sql"
FN_QUESTIONS = "questions.sql"
FN_QUES_TAG = "questions_tags.sql"
FN_CASES = "caserepository.sql"
FN_CASES_TAG = "cases_tags.sql"
FN_ANSWER = "answers.sql"
FN_USER_EVAL_CASE = "user_eval_case.sql"
FN_USER_EVAL_QUES = "user_eval_question.sql"
FN_USER_EVAL_ANS = "user_eval_answer.sql"

CREATE_BONUS = 3
LOVE_BONUS = 2
UNLOVE_BONUS = -2
SAVE_BONUS = 3
REPORT_BONUS = -5
ANSWER_BONUS = 3


def generate_df_from_sql(path):
  with open(path, "r", encoding='utf8') as db:
    lines = db.readlines()
    read_cols_beg = 0

    for line in lines:
      read_cols_beg += 1
      if line[:6] == "CREATE":
        break

    read_cols_end = read_cols_beg
    for line in lines[read_cols_beg:]:
      line = line.strip()
      if line[0] == "`":
        read_cols_end += 1
      else:
        break

    col_lines = lines[read_cols_beg:read_cols_end]
    cols = []
    for line in col_lines:
      cols.append(line.strip().split(" ")[0][1:-1])

    tbl = dict()
    for col in cols:
      tbl[col] = []

    for line in lines[read_cols_end:]:
      line = line.strip()
      if line[:6] == "INSERT":
        data_pos = line.index("(")
        items = line[data_pos + 1:-2].split(", ")

        i = 0
        while i < len(items):
          if items[i][0] == "'" and items[i][-1] != "'":
            j = i + 1
            while items[j][-1] != "'":
              items[i] = items[i] + items[j]
              del items[j]

            assert items[j][-1] == "'"

            items[i] = items[i] + items[j]
            del items[j]

          i += 1

        assert len(items) == len(cols), "Mismatched column number"

        for i in range(len(items)):
          item = items[i]
          if item[0] == "'" and item[-1] == "'":
            item = item[1:-1]
          tbl[cols[i]].append(item)

    df = pd.DataFrame(tbl)

  return df


def get_tags():
  path = os.path.join(PATH_TO_DATABASE, FN_TAGS)
  return generate_df_from_sql(path)


def get_users():
  path = os.path.join(PATH_TO_DATABASE, FN_USERS)
  df = generate_df_from_sql(path).drop(
      ["password", "avatar", "sign", "create_time", "update_time"],
      axis="columns")

  return df


def get_user_df(user_id: str):
  df = get_full_user_df()

  return df.loc[df["id"] == str(user_id)]


def get_questions():
  path = os.path.join(PATH_TO_DATABASE, FN_QUESTIONS)
  df = generate_df_from_sql(path).drop([
      "question_caption", "question_content", "question_release_time",
      "question_edit_time", "solved", "closed", "likes", "watchers", "markdown"
  ],
                                       axis="columns")

  return df


def get_ques_tags():
  path = os.path.join(PATH_TO_DATABASE, FN_QUES_TAG)
  df = generate_df_from_sql(path).drop(["id"], axis="columns")

  return df


def get_cases():
  path = os.path.join(PATH_TO_DATABASE, FN_CASES)
  df = generate_df_from_sql(path).drop([
      "name", "intro", "history", "diagnosis", "treatment", "prevention",
      "notes", "open", "release_time", "text_type"
  ],
                                       axis="columns")

  return df


def get_case_tags():
  path = os.path.join(PATH_TO_DATABASE, FN_CASES_TAG)
  df = generate_df_from_sql(path).drop(["id"], axis="columns")

  return df


def get_answers():
  return generate_df_from_sql(os.path.join(PATH_TO_DATABASE, FN_ANSWER))


def get_user_love_case():
  path = os.path.join(PATH_TO_DATABASE, FN_USER_EVAL_CASE)
  df = generate_df_from_sql(path).drop(["id", "watch"], axis="columns")

  df.loc[df[df["love"] == "b'1'"].index, "love"] = 1

  return df


def get_user_love_ques():
  path = os.path.join(PATH_TO_DATABASE, FN_USER_EVAL_QUES)
  df = generate_df_from_sql(path).drop(["id", "watch"], axis="columns")

  df.loc[df[df["love"] == "b'1'"].index, "love"] = 1

  return df


def add_tags_to_user(users_df, related_df, related_tags_df, eval_df, kind):
  user_ids = users_df.loc[:, "id"]

  if kind == "question":
    ques_tags_lists = []
    love_ques_tags_lists = []

    for user_id in user_ids:
      ques_ids = related_df.loc[related_df["questioner_id"] ==
                                user_id].loc[:, "question_id"]
      love_ques_ids = eval_df.loc[eval_df["love"] == 1].loc[
          eval_df["user_id"] == user_id].loc[:, "question_id"]

      ques_tags = set()
      for ques_id in ques_ids:
        tag_ids = related_tags_df.loc[related_tags_df["question_id"] ==
                                      ques_id].loc[:, "tag_id"].to_list()
        ques_tags.update(tag_ids)
      ques_tags_lists.append(list(ques_tags))

      love_ques_tags = set()
      for ques_id in love_ques_ids:
        tag_ids = related_tags_df.loc[related_tags_df["question_id"] ==
                                      ques_id].loc[:, "tag_id"].to_list()
        love_ques_tags.update(tag_ids)
      love_ques_tags_lists.append(list(love_ques_tags))

    return users_df.assign(ques_tags=ques_tags_lists).assign(
        love_ques_tags=love_ques_tags_lists)

  elif kind == "case":
    case_tags_lists = []
    love_case_tags_lists = []

    for user_id in user_ids:
      case_ids = related_df.loc[related_df["user_id"] == user_id].loc[:,
                                                                      "case_id"]
      love_case_ids = eval_df.loc[eval_df["love"] == 1].loc[
          eval_df["user_id"] == user_id].loc[:, "case_id"]

      case_tags = set()
      for case_id in case_ids:
        tag_ids = related_tags_df.loc[related_tags_df["case_id"] ==
                                      case_id].loc[:, "tag_id"].to_list()
        case_tags.update(tag_ids)
      case_tags_lists.append(list(case_tags))

      love_case_tags = set()
      for case_id in love_case_ids:
        tag_ids = related_tags_df.loc[related_tags_df["case_id"] ==
                                      case_id].loc[:, "tag_id"].to_list()
        love_case_tags.update(tag_ids)
      love_case_tags_lists.append(list(love_case_tags))

    return users_df.assign(case_tags=case_tags_lists).assign(
        love_case_tags=love_case_tags_lists)

  else:
    raise ValueError("Invalid kind")


def get_full_user_df():
  users_df = get_users()
  ques_df = get_questions()
  case_df = get_cases()
  ques_tags_df = get_ques_tags()
  case_tags_df = get_case_tags()
  eval_ques_df = get_user_love_ques()
  eval_case_df = get_user_love_case()

  users_df = add_tags_to_user(users_df, ques_df, ques_tags_df, eval_ques_df,
                              "question")
  users_df = add_tags_to_user(users_df, case_df, case_tags_df, eval_case_df,
                              "case")

  return users_df


def user_case_mat(user_alloc, case_alloc):
  case_df = get_cases()
  case_eval_df = generate_df_from_sql(
      os.path.join(PATH_TO_DATABASE, FN_USER_EVAL_CASE))

  user_ids = get_users().loc[:, "id"].to_list()
  rate_mat = []
  rate_dict = dict()

  for user in user_ids:
    user_alloc.allocate(user)

    created_cases = case_df.loc[case_df["user_id"] ==
                                user].loc[:, "case_id"].to_list()
    loved_cases = case_eval_df.loc[case_eval_df["user_id"] == user].loc[
        case_eval_df["love"] == "b'1'"].loc[:, "case_id"].to_list()
    watched_cases = case_eval_df.loc[case_eval_df["user_id"] == user].loc[
        case_eval_df["watch"] == "b'1'"].loc[:, "case_id"].to_list()
    related_cases = set(created_cases).union(set(loved_cases)).union(
        set(watched_cases))

    for case in related_cases:
      case_alloc.allocate(case)

      rate_dict[(user, case)] = 0
      if case in created_cases:
        rate_dict[(user, case)] += CREATE_BONUS
      if case in loved_cases:
        rate_dict[(user, case)] += LOVE_BONUS
      if case in watched_cases:
        rate_dict[(user, case)] += SAVE_BONUS

  for (user, case), rate in rate_dict.items():
    user = user_alloc.get_id(user)
    case = case_alloc.get_id(case)

    rate_mat.append([user, case, rate])

  return rate_mat, user_alloc, case_alloc


def user_ques_mat(user_alloc, ques_alloc):
  ques_df = get_questions()
  ans_df = get_answers()
  ques_eval_df = generate_df_from_sql(
      os.path.join(PATH_TO_DATABASE, FN_USER_EVAL_QUES))

  user_ids = get_users().loc[:, "id"].to_list()
  rate_mat = []
  rate_dict = dict()

  for user in user_ids:
    user_alloc.allocate(user)

    created_ques = ques_df.loc[ques_df["questioner_id"] ==
                               user].loc[:, "question_id"].to_list()
    loved_ques = ques_eval_df.loc[ques_eval_df["user_id"] == user].loc[
        ques_eval_df["love"] == "b'1'"].loc[:, "question_id"].to_list()
    watched_ques = ques_eval_df.loc[ques_eval_df["user_id"] == user].loc[
        ques_eval_df["watch"] == "b'1'"].loc[:, "question_id"].to_list()
    answered_ques = ans_df.loc[ans_df["answerer_id"] ==
                               user].loc[:, "question_id"].to_list()
    related_ques = set(created_ques).union(set(loved_ques)).union(
        set(watched_ques)).union(set(answered_ques))

    for ques in related_ques:
      ques_alloc.allocate(ques)

      rate_dict[(user, ques)] = 0
      if ques in created_ques:
        rate_dict[(user, ques)] += CREATE_BONUS
      if ques in loved_ques:
        rate_dict[(user, ques)] += LOVE_BONUS
      if ques in watched_ques:
        rate_dict[(user, ques)] += SAVE_BONUS
      if ques in answered_ques:
        rate_dict[(user, ques)] += ANSWER_BONUS

  for (user, ques), rate in rate_dict.items():
    user = user_alloc.get_id(user)
    ques = ques_alloc.get_id(ques)
    rate_mat.append([user, ques, rate])

  return rate_mat, user_alloc, ques_alloc


def user_ans_mat(user_alloc, ans_alloc):
  ans_df = get_answers()
  ans_eval_df = generate_df_from_sql(
      os.path.join(PATH_TO_DATABASE, FN_USER_EVAL_ANS))

  user_ids = get_users().loc[:, "id"].to_list()
  rate_mat = []
  rate_dict = dict()

  for user in user_ids:
    user_alloc.allocate(user)

    created_ans = ans_df.loc[ans_df["answerer_id"] ==
                             user].loc[:, "answer_id"].to_list()
    loved_ans = ans_eval_df.loc[ans_eval_df["user_id"] == user].loc[
        ans_eval_df["love"] == "1"].loc[:, "answer_id"].to_list()
    unloved_ans = ans_eval_df.loc[ans_eval_df["user_id"] == user].loc[
        ans_eval_df["love"] == "2"].loc[:, "answer_id"].to_list()
    saved_ans = ans_eval_df.loc[ans_eval_df["user_id"] == user].loc[
        ans_eval_df["save"] == "b'1'"].loc[:, "answer_id"].to_list()
    reported_ans = ans_eval_df.loc[ans_eval_df["user_id"] == user].loc[
        ans_eval_df["report"] == "b'1'"].loc[:, "answer_id"].to_list()
    related_ans = set(created_ans).union(set(loved_ans)).union(
        set(saved_ans)).union(set(unloved_ans)).union(set(reported_ans))

    for ans in related_ans:
      ans_alloc.allocate(ans)

      rate_dict[(user, ans)] = 0
      if ans in created_ans:
        rate_dict[(user, ans)] += CREATE_BONUS
      if ans in loved_ans:
        rate_dict[(user, ans)] += LOVE_BONUS
      if ans in unloved_ans:
        rate_dict[(user, ans)] += UNLOVE_BONUS
      if ans in saved_ans:
        rate_dict[(user, ans)] += SAVE_BONUS
      if ans in reported_ans:
        rate_dict[(user, ans)] += REPORT_BONUS

  for (user, ans), rate in rate_dict.items():
    user = user_alloc.get_id(user)
    ans = ans_alloc.get_id(ans)
    rate_mat.append([user, ans, rate])

  return rate_mat, user_alloc, ans_alloc


def user_tag_mat(user_alloc, tag_alloc):
  users_df = get_full_user_df()
  user_ids = users_df.loc[:, "id"].to_list()
  rate_mat = []
  rate_dict = dict()

  for user in user_ids:
    user_alloc.allocate(user)
    user_df = users_df.loc[users_df["id"] == user]
    created_case_tags = user_df.loc[:, "case_tags"].item()
    loved_case_tags = user_df.loc[:, "love_case_tags"].item()
    created_ques_tags = user_df.loc[:, "ques_tags"].item()
    loved_ques_tags = user_df.loc[:, "love_ques_tags"].item()

    related_tags = set(created_case_tags).union(set(loved_case_tags)).union(
        set(created_ques_tags)).union(set(loved_ques_tags))

    for tag in related_tags:
      tag_alloc.allocate(tag)

      rate_dict[(user, tag)] = 0
      if tag in created_case_tags:
        rate_dict[(user, tag)] += CREATE_BONUS
      if tag in loved_case_tags:
        rate_dict[(user, tag)] += LOVE_BONUS
      if tag in created_ques_tags:
        rate_dict[(user, tag)] += CREATE_BONUS
      if tag in loved_ques_tags:
        rate_dict[(user, tag)] += LOVE_BONUS

  for (user, tag), rate in rate_dict.items():
    user = user_alloc.get_id(user)
    tag = tag_alloc.get_id(tag)
    rate_mat.append([user, tag, rate])

  return rate_mat, user_alloc, tag_alloc


if __name__ == "__main__":
  # users_df = get_users()
  # tags_df = get_tags()
  # ques_df = get_questions()
  # case_df = get_cases()
  # ques_tags_df = get_ques_tags()
  # case_tags_df = get_case_tags()
  # eval_ques_df = get_user_love_ques()
  # eval_case_df = get_user_love_case()

  # users_df = add_tags_to_user(users_df, ques_df, ques_tags_df, eval_ques_df,
  #                             "question")
  # users_df = add_tags_to_user(users_df, case_df, case_tags_df, eval_case_df,
  #                             "case")
  # print(users_df)
  print(user_case_mat())
  print(user_ques_mat())
  print(user_ans_mat())
