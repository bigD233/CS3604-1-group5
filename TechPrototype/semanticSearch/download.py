from sentence_transformers import SentenceTransformer
 
# 保存预训练模型的文件夹
save_path = "./bert_based_chinese1"
 
# 加载模型
model = SentenceTransformer('paraphrase-multilingual-MiniLM-L12-v2')
# 保存模型到指定文件夹
model.save(save_path)