<script setup>
import { useRouter } from 'vue-router'
import Menu from '@/components/Header.vue'
import Header from '../components/Header.vue'
import Footer from '@/components/StartFooter.vue'

const router = useRouter()

function jump_to_login() {
  router.push('/login');
};

function jump_to_home(){
  router.push('/home');
}

</script>

<template>
  <header>
    <Menu noSearch/>
  </header>
  <el-container style="width: 100%; min-height:100%; min-width: 1000px;">

    <el-main style="margin: 50px;">
      <el-row>
        <el-col :span="10"></el-col>
        <el-col :span="4">
          <img src="../assets/logo_with_name.png" style="width: 200px;" />
        </el-col>
        <el-col :span="10"></el-col>
      </el-row>

      <el-row justify="space-evenly">
<!--        <el-col :span="6"></el-col>-->
        <el-col :span="11">
          <el-space direction="vertical" size="large" alignment="center" >
            <h1 style="font-size: 5vmin; font-family: PingFang SC;">访问的内容不存在，可能已被删除</h1>
            <el-button size="large" type="primary" @click="jump_to_home" style="margin: 30px auto;">返回首页</el-button>
          </el-space>
        </el-col>
<!--        <el-col :span="6"></el-col>-->
      </el-row>
    </el-main>
  </el-container>
</template>

<style>
.quote-box {
  width: 100%;
  margin: 0px;
  padding: 10px;
  border-left: 4px solid #D4D7DE;
  background-color: #FAFAFA;
}

.quote-author::before {
  content: "——";
}

.quote-text {
  font-size: 20px;
  margin: 5px;
}

.quote-author {
  font-style: italic;
  font-size: 16px;
  color: #909399;
  text-align: right;
}

.footer {
  background-color: #F5F7FA;
}
</style>
