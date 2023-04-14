<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { User, Lock } from '@element-plus/icons-vue';
import Header from '@/components/StartHeader.vue';
import { SHA256Password } from '@/utils/encrypto.js';
import { saveToken } from '@/utils/tokenUtils.js';
import { displayError } from '@/utils/messageUtils.js';

const router = useRouter();
const store = useStore();
const axios = inject('axios');

const username = ref('');
const password = ref('');

function login() {
  let data = new FormData();
  data.append('username', username.value);
  data.append('password', SHA256Password(password.value));
  displayError(axios
    .post('/user/login', data))
    .then(respData => {
      saveToken(store, axios, respData.userId, respData.token);

      displayError(axios
        .get('/user/role', { params: { userId: respData.userId } }))
        .then(roles => {
          store.commit('setRoles', { isNormalRole: roles.includes('normal'), isAdminRole: roles.includes('admin') });
          store.commit('setUserTab', 'info');
          router.push('/home');
        })
    });
}
</script>

<template>
  <div id="whole-page">
    <header class="i-header">
      <Header disableLogin />
    </header>
    <main class="i-main">
      <div>
        <el-card class="login-card">
          <el-row justify="center">
            <h1>用户登录</h1>
          </el-row>
          <div class="input-prompt">用户名</div>
          <div class="input-box">
            <el-input v-model="username" placeholder="请输入用户名" clearable :prefix-icon="User" />
          </div>
          <div class="input-prompt">密码</div>
          <div class="input-box">
            <el-input v-model="password" placeholder="请输入密码" type="password" show-password :prefix-icon="Lock" />
          </div>
          <el-row class="login-button" justify="center">
            <el-button type="primary" size="large" @click="login" auto-insert-space>登录</el-button>
          </el-row>
        </el-card>
      </div>
    </main>
  </div>
</template>

<style scoped>
.input-prompt {
  margin: 0 0 0 5px;
}

.input-box {
  margin: 5px 0 30px 0;
}

.login-button {
  margin-top: 100px;
  font-size: 'Large';
}

.login-card {
  margin: 50px auto;
  height: 640px;
  width: 395px;
  border-radius: 30px;
  font-family: 'Microsoft YaHei';
}
</style>