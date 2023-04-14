<script setup>
import {useStore} from 'vuex';
import {Plus, Minus} from '@element-plus/icons-vue';
import {computed, inject, onMounted, ref, watch} from 'vue';
import {displayError} from "@/utils/messageUtils";
import {timeTillNow} from "@/utils/timeUtils";

const axios = inject('axios');
const store = useStore();
const userSelf = computed(() => props.userId === Number(store.state.userId));

const emit = defineEmits(['getAuthorName']);

const props = defineProps({
  userId: Number,
  releaseTime: String,
  editTime: String,

  // 0-完整模式，显示头像、用户名、发布时间、签名和关注按钮，用于案例和问答发布者；
  // 1-推荐模式，无发布时间，固定宽度，简化关注按钮，用于推荐用户列表中；
  // 2-简化模式，不显示个性签名和关注按钮，用于评论
  // 3-搜索模式，无发布时间，固定宽度，其余布局同完整模式，尺寸更大
  thumbnailType: Number,
});

const userInfo = ref({
  username: "",
  avatar: null,
  sign: "",
})

// 当前登录用户是否关注了该用户
const userFollowing = ref(false);

watch(  // 监听 props.userId 变化
    () => props.userId,
    (newId) => {
      loadUser();
      getUserFollowing();
    }
);

function getUserFollowing() {
  if (userSelf.value) {
    return;
  }

  displayError(axios
      .get('/user/get/following', {
        params: {
          followee: props.userId,
          follower: store.state.userId,
        }
      }))
      .then(respData => {
        userFollowing.value = respData;
      });
}

function followUser() {
  let data = new FormData();
  data.append('followee', props.userId);
  data.append('follower', store.state.userId);

  displayError(axios
      .post('/user/add/following', data))
      .then(respData => {
        userFollowing.value = true;
      });
}

function unfollowUser() {
  let data = new FormData();
  data.append('followee', props.userId);
  data.append('follower', store.state.userId);

  displayError(axios
      .post('/user/remove/following', data))
      .then(respData => {
        userFollowing.value = false;
      })
}

function loadUser() {
  if (Number.isInteger(props.userId)) {
    axios
        .get('/user/get', {
          params: {id: props.userId}
        })
        .then(resp => {
          if (resp) {
            userInfo.value = resp.data.data;
            emit('getAuthorName', userInfo.value.username);
          }
        })
  }
}


onMounted(() => {
  if (props.userId !== 0)
    loadUser();
});
</script>


<template>
  <div>
    <!--  完整模式 -->
    <div v-if="props.thumbnailType===0" class="author-info-wrapper-comp">
      <div @click="this.$router.push(`/user/${props.userId}`)"
           class="user-avatar-wrapper user-link">
        <img :src=userInfo.avatar>
      </div>
      <div @click="this.$router.push(`/user/${props.userId}`)"
           class="user-name-time-wrapper user-link">
        <span class="user-name">{{ userInfo.username }}</span>
        <span v-if="props.editTime"> 编辑于 {{ timeTillNow(props.editTime) }}</span>
        <span v-else> 发布于 {{ timeTillNow(props.releaseTime) }}</span>
      </div>
      <span @click="this.$router.push(`/user/${props.userId}`)"
            class="user-signature user-link">{{ userInfo.sign }}</span>
      <div v-if="!userSelf" class="follow-user">
        <el-button v-if="!userFollowing"
                   @click="followUser" type="primary" :icon="Plus">关注
        </el-button>
        <el-button v-else type="primary" :icon="Minus" plain
                   @click="unfollowUser">取消关注
        </el-button>
      </div>
    </div>

    <!--  推荐模式-->
    <div v-else-if="props.thumbnailType===1" class="author-info-wrapper-rec">
      <div @click="this.$router.push(`/user/${props.userId}`)"
           class="user-avatar-wrapper user-link">
        <img :src=userInfo.avatar>
      </div>
      <div @click="this.$router.push(`/user/${props.userId}`)"
           class="user-name-time-wrapper user-link">
        <span class="user-name">{{ userInfo.username }}</span>
      </div>
      <span @click="this.$router.push(`/user/${props.userId}`)"
            class="user-signature-rec user-link">{{ userInfo.sign }}</span>
      <el-button v-if="!userSelf && !userFollowing"
                 @click="followUser" class="follow-user" :icon="Plus" text></el-button>
    </div>

    <!--  简化模式-->
    <div v-else-if="props.thumbnailType===2" @click="this.$router.push(`/user/${props.userId}`)"
         class="author-info-wrapper-simp user-link">
      <div class="user-avatar-wrapper">
        <img :src=userInfo.avatar>
      </div>
      <div class="user-name-time-wrapper">
        <span class="user-name">{{ userInfo.username }}</span>
        <span> 发布于 {{ timeTillNow(releaseTime) }}</span>
      </div>
    </div>

    <!--  搜索模式 -->
    <div v-else class="author-info-wrapper-sear">
      <div @click="this.$router.push(`/user/${props.userId}`)"
           class="user-avatar-wrapper user-link">
        <img :src=userInfo.avatar>
      </div>
      <div @click="this.$router.push(`/user/${props.userId}`)"
           class="user-name-wrapper user-link">
        <span class="user-name">{{ userInfo.username }}</span>
      </div>
      <span @click="this.$router.push(`/user/${props.userId}`)"
            class="user-signature user-link">{{ userInfo.sign }}</span>
      <div v-if="!userSelf" class="follow-user">
        <el-button v-if="!userFollowing"
                   @click="followUser" type="primary" :icon="Plus">关注
        </el-button>
        <el-button v-else type="primary" :icon="Minus" plain
                   @click="unfollowUser">取消关注
        </el-button>
      </div>
    </div>
  </div>
</template>


<style scoped>
.user-link:hover {
  cursor: pointer;
}

.author-info-wrapper-comp {
  display: inline-grid;
  grid-template-columns: 38px auto 100px;
  grid-template-rows: 19px 19px;
  grid-gap: 7px;
  justify-items: start;
  align-items: center;
}

img {
  width: 98%;
  height: 98%;
}

.author-info-wrapper-comp > .user-avatar-wrapper {
  grid-column: 1;
  grid-row-start: 1;
  grid-row-end: 3;
  justify-self: center;
  align-self: center;
}

.author-info-wrapper-comp > .user-name-time-wrapper {
  grid-column: 2;
  grid-row: 1;
}

.user-name {
  font-weight: bold;
}

.user-signature {
  grid-column: 2;
  grid-row: 2;
  color: dimgray;
}

.user-signature-rec {
  grid-column: 2;
  grid-row: 2;
  color: dimgray;
  width: 100%;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
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

.author-info-wrapper-simp {
  display: inline-grid;
  grid-template-columns: 5vmin auto;
  grid-template-rows: 5vmin;
  grid-gap: 4px;
  justify-items: start;
  align-items: center;
}

.author-info-wrapper-simp > .user-avatar-wrapper {
  grid-column: 1;
  grid-row: 1;
  justify-self: center;
  align-self: center;
}

.author-info-wrapper-simp > .user-name-time-wrapper {
  grid-column: 2;
  grid-row: 1;
}

.author-info-wrapper-rec {
  display: inline-grid;
  grid-template-columns: 1fr 4fr 1fr;
  grid-template-rows: 19px 19px;
  grid-gap: 3px;
  justify-items: start;
  align-items: center;
  margin: 3px;
}

.author-info-wrapper-rec > .user-avatar-wrapper {
  grid-column: 1;
  grid-row-start: 1;
  grid-row-end: 3;
  justify-self: center;
  align-self: center;
}

.author-info-wrapper-rec > .user-name-time-wrapper {
  grid-column: 2;
  grid-row: 1;
}

.author-info-wrapper-rec > .follow-user {
  width: 100%;
  justify-self: end;
}

.author-info-wrapper-sear {
  display: inline-grid;
  grid-template-columns: 1fr 3fr 1fr;
  grid-gap: 7px;
  justify-items: start;
  align-items: center;
}

.author-info-wrapper-sear > .user-avatar-wrapper {
  grid-column: 1;
  grid-row-start: 1;
  grid-row-end: 3;
  justify-self: center;
  align-self: center;
}

.author-info-wrapper-sear > .user-name-wrapper {
  grid-column: 2;
  grid-row: 1;
}

.author-info-wrapper-sear > .user-name-wrapper > .user-name {
  font-size: 5vmin;
}

.author-info-wrapper-sear > .user-signature {
  font-size: 3vmin
}
</style>
