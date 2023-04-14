from flask import Flask, render_template, request
import loadESCase
import json


app = Flask(__name__)

model = loadESCase.loadmodel()
data = loadESCase.loaddata()
loadESCase.initdata(model,data)

# 语义搜索
@app.route('/', methods=['GET', 'POST'])
def semanticSearch():

    #post请求
    content = request.args.get('query')

    print(content)
    result = loadESCase.query(model,content)

    print(result)

    return result





if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
    


    
 
