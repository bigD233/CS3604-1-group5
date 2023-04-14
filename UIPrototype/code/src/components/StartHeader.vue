<script setup>
import { useRouter } from 'vue-router'

const props = defineProps(["disableLogin", "disableSignup"])

const router = useRouter()

function jump_to_start() {
  router.push('/start')
}
function jump_to_login() {
  router.push('/login')
}
function jump_to_signup() {
  router.push('/signup')
}

</script>

<template>
  <div class="nav_bar">
    <el-row align="middle" style="height: 50px;">
      <div class="flex-grow-small" />
      <el-icon @click="jump_to_start"><img class="logo" src="../assets/logo.png" /></el-icon>
      <div class="flex-grow-large" />
      <el-button type="primary" size="large" @click="jump_to_login" :disabled="disableLogin">登录</el-button>
      <div class="flex-grow-small" />
      <el-button type="default" size="large" @click="jump_to_signup" :disabled="disableSignup">注册</el-button>
      <div class="flex-grow-small" />
    </el-row>
  </div>
</template>

<style scoped>
.logo {
  width: 70px;
  height: 70px;
  margin-left: 15px;
}
.nav_bar {
  background-color: #409EFF;
  height: 50px;
}

.el-row {
  height: 100px;
}

.flex-grow-large {
  flex-grow: 100;
}

.flex-grow-small {
  flex-grow: 3;
}
</style>
