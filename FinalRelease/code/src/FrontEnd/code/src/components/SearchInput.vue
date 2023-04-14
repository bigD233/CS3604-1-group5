<script setup>

import {ref} from 'vue';
import {Search, Document, ChatLineRound} from '@element-plus/icons-vue';

const selected = ref('none');

</script>


<template>
  <el-row>
    <div class="mt-4">
      <select class="search-type" v-model="selected">
        <option value="none" disabled selected hidden>类型</option>
        <option value="case">
          案例
        </option>
        <option value="question">
          问答
        </option>
      </select>
      <input class='big-input' type="primary" placeholder="输入你感兴趣的内容"/>
      <el-button type="primary" plain style="font-size:2.4vmin;height:5vmin; margin-top: -0.4vmin; margin-left: 1vmin;"
                 :icon="Search">搜索
      </el-button>
    </div>
  </el-row>
</template>


<style scoped>
.mt-4 {
  text-align: center;
  margin-left: 28vmin;
}

.search-type {
  font-size: 2vmin;
  width: 10vmin;
  height: 5vmin;
  padding: 1vmin 1.5vmin;
  border-radius: 4px;
  background-color: #ecf5ff;
  transition: box-shadow 0.25s ease;
  color: #409EFF;
}

.select select {
  cursor: pointer;
  padding: 1vmin;
  width: 100%;
  border: none;
  background: transparent;
  background-image: none;
}

.select:hover {
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.15);
}


.big-input {

  outline-style: none;
  border: 1px solid #ccc;
  border-radius: 3px;
  padding: 1.3vmin 1.7vmin;
  width: 50vmin;
  font-size: 1.7vmin;
  font-weight: 700;
  font-family: "Microsoft soft";
  margin-left: 1vmin;

}

input:focus {
  border-color: #66afe9;
  outline: 0;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6)
}

input::-webkit-input-placeholder {
  /* WebKit browsers */
  color: rgb(189, 189, 189);
}
</style>
