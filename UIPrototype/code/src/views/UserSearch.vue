<script setup>

import {onMounted, ref, inject, watch} from 'vue';
import { useRouter, useRoute } from 'vue-router'
import Header from '@/components/Header.vue';
import SearchBox from '@/components/SearchBox.vue';
import AuthorInfo from "@/components/AuthorInfo.vue";

const axios = inject('axios');

// const currentPage = ref(1);
const x =ref (0);
const y =ref (0);
const len=ref({num:0,});
const row=ref({num:0,});

const route = useRoute()
const router = useRouter()
//获取来自searchBox子组件跳转时的返回参数
const userInput=route.query.userInput;

const userInfo = ref({
  username: String,
  avatar: String,
  sign: String,
})

const userInfos =ref([userInfo]);

function userSearch() {
  axios
      .get(`/userSearchByName/${userInput}`)
      .then(resp => {
        // console.log(1);
        if (resp && resp.status === 200) {
          userInfos.value = resp.data;
          len.value.num=userInfos.value.length;

          if(len.value.num==0){
            userInfos.value=null;
          }
          if(len.value.num%2==0) {
            row.value.num=len.value.num/2;
          }
          else{
            row.value.num=(len.value.num+1)/2 ;
          }
          console.log(userInfos.value);
          console.log(len.value.num);
          console.log(row.value.num);
        }
      })
}


onMounted(() => {
  userSearch();
})

// 这里监听路由参数变化来更新页面
watch(
    () => router.currentRoute.value.query,

    (newVal, oldVal) => {

      if(oldVal && newVal)
      {
        // console.log(newVal);
        // console.log(oldVal);
        window.location.reload();

      }
    },
    {
      immediate: true,
    }
)


</script>

<template>
  <Header noSearch />

  <el-container style="margin-top:50px">
    <el-col :span="4"></el-col>
    <el-col :span="16">
      <SearchBox />

      <el-row>
        <el-col :span="1"></el-col>

        <el-col :span="22">
          <el-row v-for=" x in row.num" style="margin-top:15px">
            <el-col v-for="y in 2" :span="12">
              <el-card v-if="2*(x-1)+y-1 < len.num" class="user-box">
                  <AuthorInfo
                              :user-id="userInfos[2*(x-1)+y-1].id" :thumbnail-type=3
                              style="width:100%">
                  </AuthorInfo>
              </el-card>
            </el-col>
          </el-row>
        </el-col>

        <el-col :span="1"></el-col>

      </el-row>

    </el-col>

    <el-col :span="4"></el-col>
  </el-container>

</template>



<style scoped>

.user-box {
  margin-top: 0.7em;
  border-radius: 0.5em;
  border-width: 0.1em;
  margin-right: 0.7em;

}

</style>