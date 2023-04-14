from img_review import init_model, predict_batch, set_gpu
from settings import SLEEP_TIME, MODEL_PATH, PATH_TO_PRED_DIR, PATH_TO_SIGNAL, PATH_TO_PRED_RES

import os
import tensorflow as tf
import time


def ensure_dirs():
  if not os.path.exists(MODEL_PATH):
    raise FileNotFoundError("No model found!")
  if not os.path.exists(PATH_TO_PRED_DIR):
    raise FileNotFoundError(
        f"No prediction directory! Expect {PATH_TO_PRED_DIR}.")
  if not os.path.exists(PATH_TO_SIGNAL):
    raise FileNotFoundError(f"No signal file found! Expect {PATH_TO_SIGNAL}.")


def load_model():
  saved_path = tf.train.latest_checkpoint(MODEL_PATH)
  model = init_model()
  model.load_weights(saved_path).expect_partial()

  return model


def dump_pred_res(pred_res):
  with open(PATH_TO_PRED_RES, "w") as fp:
    for img_name, res in pred_res:
      message = f"{img_name} {res}\n"
      fp.write(message)
      print(message, end="")


def predict_by_signal():
  model = load_model()

  while True:
    signal_time = os.path.getmtime(PATH_TO_SIGNAL)

    res = predict_batch(model, PATH_TO_PRED_DIR)
    dump_pred_res(res)

    while signal_time == os.path.getmtime(PATH_TO_SIGNAL):
      time.sleep(SLEEP_TIME)


if __name__ == "__main__":
  ensure_dirs()
  set_gpu()
  predict_by_signal()
