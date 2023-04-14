from data_reader import DataReader
from hyper_param import param_dict as pd
from nets import PolicyNetwork, ValueNetwork
from trainer import Trainer

import os
import tensorflow.compat.v1 as tf
from absl import app, flags

FLAGS = flags.FLAGS
flags.DEFINE_string("ra_type", "lra",
                    "Type of agent: lra (low-level) or hra (high-level)")
flags.DEFINE_bool("train", True, "Train agent")
flags.DEFINE_bool("eval", False, "Evaluate agent")

os.environ["TF_CPP_MIN_LOG_LEVEL"] = "3"
tf.disable_eager_execution()


def main(args):
  # No need for args
  del args

  # Read data
  dr = DataReader(pd["batch_size"])
  dr.load(pd["dataset"])

  mode = "train" if FLAGS.train else "eval"
  mode = "eval" if FLAGS.eval else "train"

  actor = PolicyNetwork(mode=mode, unique_feature_num=dr.unique_feature_num())
  critic = ValueNetwork(mode=mode, unique_feature_num=dr.unique_feature_num())
  trainer = Trainer(FLAGS.ra_type, actor, critic, dr, pd)

  if FLAGS.train:
    trainer.train()
  if FLAGS.eval:
    trainer.eval()
  trainer.work()


if __name__ == "__main__":
  app.run(main)
