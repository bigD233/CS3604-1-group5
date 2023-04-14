from sentence_transformers import SentenceTransformer as st
from sentence_transformers import util
import numpy as np
import time 


model = st("./bert_based_chinese1")

name=np.load('name.npy')
id=np.load('id.npy')
vector=np.load('vector.npy')

# Queries and their embeddings
queries = ["皮肤黏膜暂时性血管通透性增加而发生的局限性水肿", "可有明显疼痛、畏光、流泪、视力减退","荨麻疹"]
start=time.time()
queries_embeddings = model.encode(queries)

# Find the top-2 corpus documents matching each query
hits = util.semantic_search(queries_embeddings, vector, top_k=2)

# Print results of first query
print(f"Query: {queries[0]}")
for hit in hits[0]:
    print(name[hit['corpus_id']], "(Score: {:.4f})".format(hit['score']))

print(f"Query: {queries[1]}")
for hit in hits[1]:
    print(name[hit['corpus_id']], "(Score: {:.4f})".format(hit['score']))

print(f"Query: {queries[2]}")
for hit in hits[2]:
    print(name[hit['corpus_id']], "(Score: {:.4f})".format(hit['score']))

end=time.time()

print(end-start)