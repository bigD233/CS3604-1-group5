<script setup>
import { useRouter } from 'vue-router'
import Header from '@/components/StartHeader.vue'
import Footer from '@/components/StartFooter.vue'

const router = useRouter()

function jump_to_login() {
  router.push('/login');
};
</script>

<template>
  <el-container style="width: 100%; min-height:100%; min-width: 1000px;">
    <el-header style="margin: 0; padding: 0">
      <Header />
    </el-header>
    <el-main style="margin: 50px;">
      <el-row justify="space-evenly">
        <el-col :span="8">
          <el-space direction="vertical" size="large" alignment="center" style="width: 450px;">
            <h1 style="font-size: 40px; font-family: PingFang SC;">欢迎来到白云社区</h1>
            <div style="font-size: 24px;">
              <p>本社区旨在为患者们搭建一个互相交流、信息共享的平台。</p>
              <blockquote class="quote-box">
                <p class="quote-text">不为怜同病，何人到白云。</p>
                <footer class="quote-author">刘长卿《碧涧别墅喜皇甫侍御相访》</footer>
              </blockquote>
            </div>
            <el-button size="large" type="primary" @click="jump_to_login" style="margin: 30px auto;">开始</el-button>
          </el-space>
        </el-col>
        <el-col :span="8">
          <img src="../assets/logo_with_name.png" style="width: 400px;" />
        </el-col>
      </el-row>
    </el-main>
<!--    <el-footer height="220px" class="footer">-->
<!--      <Footer />-->
<!--    </el-footer>-->
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
