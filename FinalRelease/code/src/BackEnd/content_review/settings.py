import os

PORT = 8001

SLEEP_TIME = 0.2
MAX_TRY = 10

MODEL_PATH = os.path.join(os.path.split(__file__)[0], "saved_model", "img")
PATH_TO_PRED_DIR = os.path.join(
    os.path.split(__file__)[0], "data", "img", "pred")
PATH_TO_SIGNAL = os.path.join(os.path.split(__file__)[0], "img_classify.sig")
PATH_TO_PRED_RES = os.path.join(PATH_TO_PRED_DIR, "res.txt")

IMG_RAW_PATH = os.path.join(os.path.split(__file__)[0], "data", "img", "raw")
IMG_CP_DIR = os.path.join(os.path.split(__file__)[0], "ckpts", "img")
IMG_TEST_DIR = os.path.join(os.path.split(__file__)[0], "data", "img", "test")

TEXT_KW_PATH = os.path.join(
    os.path.split(__file__)[0], "saved_model", "keywords")
