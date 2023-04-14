<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  User,
  ChatLineRound,
  Document,
} from '@element-plus/icons-vue';
import SearchBox from '@/components/SearchBox.vue'

const props = defineProps({
  noSearch: Boolean,
})

// const notHome = !window.location.href.includes('/home')

const activeIndex = ref("/" + window.location.href.split('/')[4])
const isEllipsis = ref(false)

const handleSelect = (key, keyPath) => {
  console.log(key, keyPath)
}

const router = useRouter()

function jump_to_home() {
  router.push('/home')
}

</script>

<template>
  <el-menu :default-active="activeIndex" class="menu" mode="horizontal" @select="handleSelect" text-color="white"
    active-text-color="#c6e2ff" :ellipsis="isEllipsis" router>
    <img @click="jump_to_home" class="logo" src="../assets/logo.png" />
    <el-row v-if="!noSearch" class="search-box">
      <SearchBox style="width: 600px;" />
    </el-row>
    <div class="flex-grow" />
    <el-menu-item index="/home" class="item-box"><el-icon><House /></el-icon>广场</el-menu-item>
    <el-menu-item index="/casehome" class="item-box" route="/casehome"><el-icon><Document /></el-icon>案例</el-menu-item>
    <el-menu-item index="/qahome" class="item-box"><el-icon><ChatLineRound /></el-icon>问答</el-menu-item>
    <el-menu-item index="/user" class="item-box"><el-icon><User /></el-icon>我的</el-menu-item>
  </el-menu>
</template>

<style scoped>
.menu {
  background-color: #409EFF;
  height: 51px;
}

.logo {
  width: 70px;
  height: 70px;
  margin-left: 16px;
  margin-top: -10px;
}

.search-box {
  height: 30px;
  margin-top: 10px;
  margin-left: 20px;
}

.item-box {
  font-size: 16px;
  font-family: 'PingFang SC';
}

.flex-grow-large {
  flex-grow: 2;
}

.flex-grow {
  flex-grow: 1;
}
</style>