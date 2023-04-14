# 内容检测

## 环境配置

```terminal
conda create -n cr-env tensorflow==2.4.1 pillow
conda activate cr-env
```

## 图片检测

### 准备工作

创建下述目录与文件：
- `./data/img/pred`: 用于存储需要检测的图片
- `./saved_model`: 用于存储模型
- `./img_classify.sig`: 用于触发检测

> 若上述目录或文件有缺少，之后脚本的运行会在一开始就报错。
> 以上目录或文件的路径可以在`basic_values.py`中修改，分别对应其中的`PATH_TO_PRED_DIR`, `MODEL_PATH`, `PATH_TO_SIGNAL`。

### 运行脚本

准备工作完成后，运行`classify_img.py`：

```terminal
python classify_img.py
```

该脚本在使用`CTRL-C`中断前会一直运行。运行后脚本会自动开始检测`PATH_TO_PRED_DIR`目录中的文件，若该目录为空，脚本会等待直到该目录不为空。检测结果存储在`PATH_TO_PRED_DIR/res.txt`中，格式为：

```
<file_name> <res>   # res = 1 means this picture has inappropriate content
```

之后，脚本会等待直到`PATH_TO_SIGNAL`文件被修改。当`PATH_TO_SIGNAL`文件被修改后，脚本会重新读取`PATH_TO_PRED_DIR`目录下的文件并进行检测，结果依旧以相同格式存储在`PATH_TO_PRED_DIR/res.txt`中。

### 退出运行

任意中断程序运行的方法都可。如终端可使用`CTRL-C`来退出运行。

## 文字内容检测

### 准备工作

将关键词文件`keywords`放入`saved_model/`目录中。

### 运行服务

```terminal
python server.py
```

然后即可通过`http://localhost:<PORT>/text/<text>`来传输需要进行过滤的文本，其中`<PORT>`由`settings.py`中的`PORT`变量指定，`<text>`为需要过滤的文本，返回结果为用`*`过滤后的文本。