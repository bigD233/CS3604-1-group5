import math
import numpy as np
import scipy.stats
import scipy.spatial
import warnings
from math import sqrt
from sklearn.model_selection import KFold
from sklearn.metrics import mean_squared_error

warnings.simplefilter("error")


def similarity_user(data, num_users):
  user_similarity_cosine = np.zeros((num_users, num_users))
  user_similarity_jaccard = np.zeros((num_users, num_users))
  user_similarity_pearson = np.zeros((num_users, num_users))

  for user1 in range(num_users):
    for user2 in range(num_users):
      if np.count_nonzero(data[user1]) and np.count_nonzero(data[user2]):
        user_similarity_cosine[user1][
            user2] = 1 - scipy.spatial.distance.cosine(data[user1], data[user2])
        user_similarity_jaccard[
            user1][user2] = 1 - scipy.spatial.distance.jaccard(
                data[user1], data[user2])

        try:
          if not math.isnan(scipy.stats.pearsonr(data[user1], data[user2])[0]):
            user_similarity_pearson[user1][user2] = scipy.stats.pearsonr(
                data[user1], data[user2])[0]
          else:
            user_similarity_pearson[user1][user2] = 0
        except:
          user_similarity_pearson[user1][user2] = 0

  return user_similarity_cosine, user_similarity_jaccard, user_similarity_pearson


def train(data, num_users, num_items):
  k_fold = KFold(10)

  Mat = np.zeros((num_users, num_items))
  for e in data:
    Mat[e[0] - 1][e[1] - 1] = e[2]

  sim_user_cosine, sim_user_jaccard, sim_user_pearson = similarity_user(
      Mat, num_users)
  rmse_cosine = []
  rmse_jaccard = []
  rmse_pearson = []

  for train_indices, test_indices in k_fold.split(data):
    train = [data[i] for i in train_indices]
    test = [data[i] for i in test_indices]

    M = np.zeros((num_users, num_items))

    for e in train:
      M[e[0] - 1][e[1] - 1] = e[2]

    true_rate = []
    pred_rate_cosine = []
    pred_rate_jaccard = []
    pred_rate_pearson = []

    for e in test:
      user = e[0]
      item = e[1]
      true_rate.append(e[2])

      pred_cosine = 3.0
      pred_jaccard = 3.0
      pred_pearson = 3.0

      if np.count_nonzero(M[user - 1]):
        sim_cosine = sim_user_cosine[user - 1]
        sim_jaccard = sim_user_jaccard[user - 1]
        sim_pearson = sim_user_pearson[user - 1]
        ind = (M[:, item - 1] > 0)

        normal_cosine = np.sum(np.absolute(sim_cosine[ind]))
        normal_jaccard = np.sum(np.absolute(sim_jaccard[ind]))
        normal_pearson = np.sum(np.absolute(sim_pearson[ind]))
        if normal_cosine > 0:
          pred_cosine = np.dot(sim_cosine, M[:, item - 1]) / normal_cosine

        if normal_jaccard > 0:
          pred_jaccard = np.dot(sim_jaccard, M[:, item - 1]) / normal_jaccard

        if normal_pearson > 0:
          pred_pearson = np.dot(sim_pearson, M[:, item - 1]) / normal_pearson

      user_info = f"User {user:2d}"
      item_info = f"Item {item:2d}"
      true_rate_info = f"True rate {e[2]:.1f}"
      pred_info = f"{pred_cosine:.1f} {pred_jaccard:.1f} {pred_pearson:.1f}"
      print(f"| {user_info} | {item_info} | {true_rate_info} | {pred_info} |")

      pred_rate_cosine.append(pred_cosine)
      pred_rate_jaccard.append(pred_jaccard)
      pred_rate_pearson.append(pred_pearson)

    rmse_cosine.append(sqrt(mean_squared_error(true_rate, pred_rate_cosine)))
    rmse_jaccard.append(sqrt(mean_squared_error(true_rate, pred_rate_jaccard)))
    rmse_pearson.append(sqrt(mean_squared_error(true_rate, pred_rate_pearson)))

    cosine_loss = mean_squared_error(true_rate, pred_rate_cosine)
    jaccard_loss = mean_squared_error(true_rate, pred_rate_jaccard)
    pearson_loss = mean_squared_error(true_rate, pred_rate_pearson)
    cosine_info = f"Cosine loss: {cosine_loss:.3f}"
    jaccard_info = f"Jaccard loss: {jaccard_loss:.3f}"
    pearson_info = f"Pearson loss: {pearson_loss:.3f}"
    print(f"\n| {cosine_info} | {jaccard_info} | {pearson_info} |\n")

  rmse_cosine = sum(rmse_cosine) / float(len(rmse_cosine))
  rmse_pearson = sum(rmse_pearson) / float(len(rmse_pearson))
  rmse_jaccard = sum(rmse_jaccard) / float(len(rmse_jaccard))

  print(
      f"\nAverage loss: | {rmse_cosine:.3f} | {rmse_jaccard:.3f} | {rmse_pearson:.3f}\n"
  )

  rmse = [rmse_cosine, rmse_jaccard, rmse_pearson]
  req_sim = rmse.index(min(rmse))

  if req_sim == 0:
    sim_mat_user = sim_user_cosine
  if req_sim == 1:
    sim_mat_user = sim_user_jaccard
  if req_sim == 2:
    sim_mat_user = sim_user_pearson

  return Mat, sim_mat_user


def predict_by_user(recommend_data, user_id, user_alloc, item_alloc):
  M, sim_user = train(recommend_data, user_alloc.unique_id_num(),
                      item_alloc.unique_id_num())

  pred_rate = []

  for e in range(1, item_alloc.unique_id_num()):
    user = user_alloc.get_id(user_id)
    item = e + 1

    pred = 3.

    if np.count_nonzero(M[user - 1]):
      sim = sim_user[user - 1]
      ind = (M[:, item - 1] > 0)
      normal = np.sum(np.absolute(sim[ind]))
      if normal > 0:
        pred = np.dot(sim, M[:, item - 1]) / normal

    pred_rate.append(pred)

  return pred_rate


if __name__ == "__main__":
  print(predict_by_user((), "11"))
