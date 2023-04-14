from sentence_transformers import SentenceTransformer as st
from sentence_transformers import util
import time
 
# 加载模型
model = st("./bert_based_chinese1")
 
# 待编码的句子
sentences = [
      "荨麻疹（Urticaria）俗称风团、风疹块（与风疹名称相似，但却非同一疾病）。",
      "皮肤黏膜较为常见的过敏性疾病，主要是皮肤黏膜暂时性血管通透性增加而发生的局限性水肿，即风团。",
      "根据不同类型眼外伤其临床表现不一样。如钝挫伤、眼球穿通伤、眼异物伤、眼附属器外伤。临床表现可有明显疼痛、畏光、流泪、视力减退等。",
      "荨麻疹"
    ]
 
# 编码
embeddings = model.encode(sentences)

start=time.time()
for sentence,embedding in zip(sentences, embeddings):
    print("Sentence:", sentence)
    print("Embedding:", embedding)
    print("")
end=time.time()

# Compute similarities
sim = util.cos_sim(embeddings[0], embeddings[3])
print("{0:.4f}".format(sim.tolist()[0][0])) # 0.6445

sim = util.cos_sim(embeddings[1], embeddings[3])
print("{0:.4f}".format(sim.tolist()[0][0])) # 0.6445

sim = util.cos_sim(embeddings[2], embeddings[3])
print("{0:.4f}".format(sim.tolist()[0][0])) # 0.6445

print(len(embeddings[2]))

print(end-start)