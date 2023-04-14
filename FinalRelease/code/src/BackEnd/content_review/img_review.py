from settings import SLEEP_TIME, IMG_RAW_PATH, IMG_CP_DIR, IMG_TEST_DIR

import os
import numpy as np
import tensorflow as tf
import time
from absl import app, flags

NUM_EPOCHS = 32
VAL_SPLIT = 0.2
SEED = 123
IMG_SIZE = 256
BATCH_SIZE = 8
SAVE_FREQ = 2

FLAGS = flags.FLAGS
flags.DEFINE_bool("train", True, "Train a new model")
flags.DEFINE_bool("eval", False, "Evaluate a saved model")
flags.DEFINE_bool("predict", False, "Predict in test set")

tf.random.set_seed(SEED)


def set_gpu():
  gpus = tf.config.experimental.list_physical_devices("GPU")

  if gpus:
    try:
      for gpu in gpus:
        tf.config.experimental.set_memory_growth(gpu, True)

      logical_gpus = tf.config.experimental.list_logical_devices("GPU")
      print(f"{len(gpus)} physical GPUs, {len(logical_gpus)} logical GPUs")
    except RuntimeError as e:
      print(e)


def init_model():
  model = tf.keras.Sequential([
      tf.keras.layers.experimental.preprocessing.Rescaling(1. / 255),
      tf.keras.layers.Conv2D(16, 8, activation='relu'),
      tf.keras.layers.MaxPooling2D(),
      tf.keras.layers.Conv2D(64, 8, activation='relu'),
      tf.keras.layers.MaxPooling2D(),
      tf.keras.layers.Conv2D(256, 8, activation='relu'),
      tf.keras.layers.MaxPooling2D(),
      tf.keras.layers.Conv2D(128, 8, activation='relu'),
      tf.keras.layers.MaxPooling2D(),
      tf.keras.layers.Flatten(),
      tf.keras.layers.Dense(256, activation='relu'),
      tf.keras.layers.Dense(2)
  ])

  return model


def load_raw():
  train_set = tf.keras.preprocessing.image_dataset_from_directory(
      IMG_RAW_PATH,
      validation_split=VAL_SPLIT,
      subset="training",
      seed=SEED,
      image_size=(IMG_SIZE, IMG_SIZE),
      batch_size=BATCH_SIZE)

  val_set = tf.keras.preprocessing.image_dataset_from_directory(
      IMG_RAW_PATH,
      validation_split=VAL_SPLIT,
      subset="validation",
      seed=SEED,
      image_size=(IMG_SIZE, IMG_SIZE),
      batch_size=BATCH_SIZE)

  return train_set, val_set


def train(model, train_ds, val_ds):
  # Include the epoch in the file name (uses `str.format`)
  checkpoint_path = IMG_CP_DIR + "/cp-{epoch:02d}.ckpt"
  checkpoint_dir = os.path.dirname(checkpoint_path)
  if not os.path.exists(checkpoint_dir):
    os.makedirs(checkpoint_dir)

  # Create a callback that saves the model's weights every 5 epochs
  cp_callback = tf.keras.callbacks.ModelCheckpoint(filepath=checkpoint_path,
                                                   verbose=1,
                                                   save_weights_only=True,
                                                   save_freq=SAVE_FREQ * 40)

  # Train
  model.compile(
      optimizer='adam',
      loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
      metrics=['accuracy'])
  model.fit(
      train_ds,
      validation_data=val_ds,
      epochs=NUM_EPOCHS,
      callbacks=[cp_callback],
  )

  return model


def predict_batch(model, path_to_dir):
  imgs = []
  img_names = []

  while not os.listdir(path_to_dir):
    time.sleep(SLEEP_TIME)

  for path in os.listdir(path_to_dir):
    if path.endswith(".png") or path.endswith(".jpg") or path.endswith(
        ".PNG") or path.endswith(".JPG") or path.endswith(".jpeg"):
      img = tf.keras.preprocessing.image.load_img(
          os.path.join(path_to_dir, path)).resize((IMG_SIZE, IMG_SIZE))
      imgs.append(np.array(img))
      img_names.append(path)

  imgs = np.array(imgs)
  prob_model = tf.keras.Sequential([model, tf.keras.layers.Softmax()])
  res = prob_model.predict(imgs)
  pred = np.argmax(res, axis=1)

  return list(zip(img_names, pred))


def main(args):
  set_gpu()

  train_ds, val_ds = load_raw()

  if not FLAGS.predict:
    if FLAGS.train:
      model = init_model()
      model = train(model, train_ds, val_ds)

    if FLAGS.eval:
      model.evaluate(val_ds)
  else:
    if len(args) != 2:
      saved_path = tf.train.latest_checkpoint(IMG_CP_DIR)
    else:
      saved_path = tf.train.latest_checkpoint(args[-1])

    model = init_model()
    model.load_weights(saved_path).expect_partial()
    preds = predict_batch(model, IMG_TEST_DIR)

    for pred in preds:
      print(pred[0], pred[1])


if __name__ == "__main__":
  app.run(main)
