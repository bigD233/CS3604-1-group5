import pymysql
from elasticsearch import  Elasticsearch
from elasticsearch import  helpers
from sentence_transformers import SentenceTransformer as st
import numpy as np


def loadEs(es):
    try:
        print('begin to create index:')
        setting = {
            "settings": {
                "number_of_replicas": 0,
                "number_of_shards": 2
            },
            "mappings": {
                "properties": {
                    "name": {
                        "type": "keyword"
                    },
                    "caseId": {
                        "type": "integer"
                    },
                    "feature": {
                        "type": "text"
                    },
                    "feature_vector": {
                        "type": "dense_vector",
                        "dims": 384
                    }
                }
            }
        }
        es.indices.create(index='cr', body=setting)
        print("end create index")
    except:
        print("index has existed")

# 将元数据和向量一起索引到es中
def bulk_index_data(es,model,data):
    index=[2,3,4,5,6]
    requests=[]
    print("begin index data to es")
    num=0
    cnt=0
    for item in data:
        num+=1
        print("finished {}".format(num))
        for i in index:
            feature_vector=model.encode(item[i],convert_to_numpy=True)
            request = {'_op_type': 'index',  # 操作 index update create delete  
                   '_index':'cr' ,  # index
                   '_id': cnt,
                   '_source':
                       {
                           'name': item[0]+str(i),
                           'caseId': item[1],
                           'feature': item[i],
                           'feature_vector': feature_vector,
                       }
                   }
            cnt+=1
            requests.append(request)
    
    
    helpers.bulk(es,requests)
    print("end index data to es")




def query(model,content):
    result=set()
    es=Elasticsearch()
    query = content
    input_vector = model.encode(query)
    resp = es.search(index='cr', body={
        "_source": ["name", "caseId"],
        "query": {
            "script_score": {
                "query": {
                    "match_all": {}
                },
                "script": {
                    "source": "cosineSimilarity(params.queryVector, doc['feature_vector'])+1",
                    "params": {
                        "queryVector": input_vector
                    }
                }
            }
        }
    })
    # print("可能获得的疾病是：", end=" ")
    for hit in resp["hits"]["hits"]:
        result.add(hit["_source"]["caseId"])

    print(list(result))
    return list(result)
    

def loadmodel():
    return st("./bert_based_chinese1")


def loaddata():
    conn = pymysql.connect(host= '127.0.0.1', port= 3306, user= 'root', password= 'admin', db= 'cloudcommunity',charset='utf8')

    # 生成游标对象 cursor
    cursor = conn.cursor()
    sql = "SELECT * FROM caserepository"
    cursor.execute(sql) # 返回值是查询到的数据数量
    data = cursor.fetchall() # 查询一条数据
    cursor.close()  # 关闭游标
    conn.close()    # 关闭连接
    return data

def initdata(model,data):
    
    es=Elasticsearch()

    loadEs(es)
    bulk_index_data(es,model,data)                                      

    
   
    
    
