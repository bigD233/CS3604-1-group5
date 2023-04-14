<script setup>
import {Plus} from '@element-plus/icons-vue';
import {inject, onMounted, ref, watch} from 'vue';

const axios = inject('axios');

const props = defineProps({
  userId: Number,

  // 折中模式，无发布时间，简化关注按钮，用于推荐用户列表中；

});

const userInfo = ref({
  username: String,
  avatar: String,
  sign: String,
})

watch(  // 监听 props.userId 变化
    () => props.userId,
    (newId) => {
      loadUser();
    }
);

function loadUser() {
  axios
      .get('/user/get', {
        params: {id: props.userId}
      })
      .then(resp => {
        if (resp) {
          userInfo.value = resp.data.data;
          // console.log(userInfo.value.avatar);
        }
      })
}

onMounted(() => {
  if (props.userId !== 0)
    loadUser();
});
</script>


<template>
  <div>

    <!--  折中模式-->
    <div  class="author-info-wrapper-ecl">
      <div @click="this.$router.push(`/user/${props.userId}`)"
           class="user-avatar-wrapper user-link">
        <img :src=userInfo.avatar>
      </div>
      <div @click="this.$router.push(`/user/${props.userId}`)"
           class="user-name-time-wrapper user-link">
        <span class="user-name">{{ userInfo.username }}</span>
      </div>
      <span @click="this.$router.push(`/user/${props.userId}`)"
            class="user-signature user-link">{{ userInfo.sign }}</span>
      <el-button class="follow-user" type="primary" :icon="Plus">关注</el-button>
    </div>

  </div>
</template>


<style scoped>
.user-link:hover {
  cursor: pointer;
}

/*.author-info-wrapper-comp {*/
/*  display: inline-grid;*/
/*  grid-template-columns: 38px auto 100px;*/
/*  grid-template-rows: 19px 19px;*/
/*  grid-gap: 7px;*/
/*  justify-items: start;*/
/*  align-items: center;*/
/*}*/

img {
  width: 98%;
  height: 98%;
}

/*.author-info-wrapper-comp > .user-avatar-wrapper {*/
/*  grid-column: 1;*/
/*  grid-row-start: 1;*/
/*  grid-row-end: 3;*/
/*  justify-self: center;*/
/*  align-self: center;*/
/*}*/

/*.author-info-wrapper-comp > .user-name-time-wrapper {*/
/*  grid-column: 2;*/
/*  grid-row: 1;*/
/*}*/

.user-name {
  font-size: 150%;
  font-weight: bold;
}

.user-signature {
  grid-column: 2;
  grid-row: 2;
  color: dimgray
}

.follow-user {
  grid-column: 3;
  grid-row-start: 1;
  grid-row-end: 3;
  place-self: center;
  width: 70%;
  height: 70%;
  border-radius: 0.5em;
}



.author-info-wrapper-ecl {
  display: inline-grid;
  grid-template-columns: 38px auto 90px;
  grid-template-rows: 19px 35px;
  grid-gap: 3px;
  justify-items: start;
  align-items: center;
  margin: 3px;
}

.author-info-wrapper-ecl > .user-avatar-wrapper {
  grid-column: 1;
  grid-row-start: 1;
  grid-row-end: 3;
  justify-self: center;
  align-self: center;
}

.author-info-wrapper-ecl > .user-name-time-wrapper {
  grid-column: 2;
  grid-row: 1;
}

.author-info-wrapper-ecl > .follow-user {
  width: 100%;
  justify-self: end;
}
</style>
