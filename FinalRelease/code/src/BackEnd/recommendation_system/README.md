# 白云社区推荐系统

## 环境配置

```terminal
conda create -n rs-env flask numpy pandas scikit-learn scipy
conda activate rs-env
```

## 使用说明

本推荐系统目前实现了如下**5**种推荐：
- 根据用户推荐案例：使用`localhost:2333/user_to_case?user_id=<id>`传递`user_id`，返回案例的推荐列表
- 根据用户推荐问题：使用`localhost:2333/user_to_ques?user_id=<id>`传递`user_id`，返回问题的推荐列表
- 根据用户推荐回答：使用`localhost:2333/user_to_answer?user_id=<id>`传递`user_id`，返回回答的推荐列表
- 根据用户推荐用户：使用`localhost:2333/user_to_user?user_id=<id>`传递`user_id`，返回用户的推荐列表
- 根据用户推荐标签：使用`localhost:2333/user_to_tag?user_id=<id>`传递`user_id`，返回标签的推荐列表

> 上述链接中的端口号`2333`可通过`settings.py`中的`PORT`来指定

运行`server_for_recommendation.py`后即可通过上述链接传递并获取相应的推荐列表。

> 注：模型每次生成的推荐列表都包含全部item，每次请求仅会获取`settings.py`中指定的数量并从推荐列表中去除本次获取的item。直到推荐列表已经全部被获取过或者长度不够此次获取，本系统才会重新生成一个新的推荐列表

## 参数设置

修改`settings.py`中的参数可以定制本推荐系统的服务。

- 端口号`PORT`：指定数据传递的端口号
- 推荐列表长度`NUM_*_REC`：指定返回的推荐列表的长度
