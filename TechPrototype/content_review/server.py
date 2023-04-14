from flask import Flask, request
import os
import shutil
import time

from filter import DFAFilter
from settings import PORT, PATH_TO_PRED_DIR, PATH_TO_SIGNAL, SLEEP_TIME, MAX_TRY, TEXT_KW_PATH, PATH_TO_PRED_RES

app = Flask(__name__)


@app.route("/text/<text>")
def filter_text(text):
  kw_filter = DFAFilter()
  kw_filter.parse(TEXT_KW_PATH)

  return kw_filter.filter(text)

@app.route("/text", methods=['POST'])
def filter_text_post():
  text = request.form['text']

  kw_filter = DFAFilter()
  kw_filter.parse(TEXT_KW_PATH)

  return kw_filter.filter(text)

@app.route("/image/<filename>")
def classify_image(filename):
  source_filename = os.path.join(
      os.path.split(__file__)[0], "..", "code", "image", filename)
  pred_filename = os.path.join(PATH_TO_PRED_DIR, filename)

  if not os.path.exists(source_filename):
    return "-1"

  shutil.copyfile(source_filename, pred_filename)

  with open(PATH_TO_SIGNAL, "a") as sig_file:
    sig_file.write(filename + "\n")

  for _ in range(MAX_TRY):
    time.sleep(SLEEP_TIME)
    with open(PATH_TO_PRED_RES, "r") as res_file:
      for line in res_file.readlines():
        res = line.split()
        if len(res) == 2 and res[0] == filename:
          os.remove(pred_filename)
          return res[1]

  os.remove(pred_filename)
  return "-1"


@app.route("/")
def hello():
  return "欢迎使用白云社区内容检测服务！"


if __name__ == "__main__":
  app.run(host="0.0.0.0", port=PORT)
