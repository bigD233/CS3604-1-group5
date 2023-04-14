<script setup>

import {ref, computed, inject, watch, onMounted} from 'vue';
import {useRoute} from 'vue-router';
import {useStore} from 'vuex';
import Header from '@/components/Header.vue';
import UserInfo from '@/components/UserInfo.vue';
import UserFollowing from '@/components/UserFollowing.vue';
// import UserMessage from '@/components/UserMessage.vue';
import UserNotification from '@/components/UserNotification.vue'
import UserManagement from '@/components/UserManagement.vue';
import UserSetting from '@/components/UserSetting.vue';
import UserMessageDialog from '@/components/UserMessageDialog.vue';
import UserPunishDialog from '@/components/UserPunishDialog.vue';
import {displayError} from '@/utils/messageUtils.js';

const route = useRoute();
const store = useStore();
const axios = inject('axios');

const user = ref({
  id: null,
  username: null,
  sign: null,
  avatar: null,
  birthday: null,
  sex: null,
  height: 0,
  weight: 0,
});

const punishment = ref(null);

const pageUserId = computed(() => route.params.userId ?? store.state.userId);
const userSelf = computed(() => pageUserId.value === store.state.userId);
const userFollowing = ref(null);
const showFollowButton = computed(() => userFollowing.value !== null && !userSelf.value);
const showMessageDialog = ref(false);
const showPunishDialog = ref(false);
const chosenTab = ref(userSelf.value ? (store.state.userTab ?? 'info') : 'info');

function loadUser() {
  displayError(axios
      .get('/user/get', {
        params: {
          id: pageUserId.value,
        }
      }))
      .then(newUser => {
        user.value.id = newUser.id;
        user.value.username = newUser.username;
        user.value.sign = newUser.sign;
        user.value.avatar = newUser.avatar;
        user.value.birthday = newUser.birthday && new Date(newUser.birthday);
        user.value.sex = newUser.sex;
        user.value.height = newUser.height;
        user.value.weight = newUser.weight;
      })
      .catch(err => {
        user.value.id = null;
        user.value.username = null;
        user.value.sign = null;
        user.value.avatar = null;
        user.value.birthday = null;
        user.value.sex = null;
        user.value.height = null;
        user.value.weight = null;
      });
  displayError(axios
      .get('/user/punishment/get', {
        params: {
          userId: pageUserId.value,
        }
      }))
      .then(data => {
        punishment.value = data;
      })
}

function getUserFollowing() {
  if (userSelf.value) {
    return;
  }

  displayError(axios
      .get('/user/get/following', {
        params: {
          followee: pageUserId.value,
          follower: store.state.userId,
        }
      }))
      .then(respData => {
        userFollowing.value = respData;
      });
}

function addUserFollowing() {
  let data = new FormData();
  data.append('followee', pageUserId.value);
  data.append('follower', store.state.userId);

  displayError(axios
      .post('/user/add/following', data))
      .then(respData => {
        userFollowing.value = true;
      });
}

function removeUserFollowing() {
  let data = new FormData();
  data.append('followee', pageUserId.value);
  data.append('follower', store.state.userId);

  displayError(axios
      .post('/user/remove/following', data))
      .then(respData => {
        userFollowing.value = false;
      })
}

watch(
    () => route.params.userId,
    async id => {
      chosenTab.value = 'info';
      loadUser();
      getUserFollowing();
    });

watch(
  () => chosenTab.value,
  async tab => {
    if (userSelf.value) {
      store.commit('setUserTab', tab);
    }});

onMounted(loadUser);
onMounted(getUserFollowing);
</script>

<template>
  <Header/>
  <el-container class="container">
    <el-header height="100px">
      <div class="user-header">
        <div class="user-info">
          <el-avatar :size="80" :src="user.avatar"/>
          <div style="margin-left:20px">
            <div class="name"> {{ user.username }}</div>
            <div class="description"> {{ user.sign }}</div>
            <div class="punishment" v-if="punishment">
              已禁言：
              该用户在 {{ punishment.startTime }} 至 {{ punishment.endTime }} 期间无法发表内容
            </div>
          </div>
        </div>
        <div>
          <el-space style="width: 100px" fill>
            <el-button v-if="showFollowButton && !userFollowing" @click="addUserFollowing">
              <el-icon>
                <Star/>
              </el-icon>
              &nbsp;关注
            </el-button>
            <el-button v-if="showFollowButton && userFollowing" @click="removeUserFollowing" type="primary">
              <el-icon>
                <Check/>
              </el-icon>
              &nbsp;已关注
            </el-button>
            <el-button v-if="!userSelf" @click="(showMessageDialog = true)">
              <el-icon>
                <Message/>
              </el-icon>
              &nbsp;私信
            </el-button>

            <el-button type="danger" v-if="!userSelf && store.state.isAdminRole && !punishment"
                       @click="(showPunishDialog = true)">
              <el-icon>
                <Mute/>
              </el-icon>
              &nbsp;禁言
            </el-button>

            <el-button type="danger" v-if="!userSelf && store.state.isAdminRole && punishment"
                       @click="(showPunishDialog = true)">
              <el-icon>
                <Microphone/>
              </el-icon>
              撤销禁言
            </el-button>
          </el-space>
        </div>
      </div>
    </el-header>

    <el-main>
      <el-tabs v-model="chosenTab">
        <el-tab-pane label="个人信息" name="info">
          <UserInfo :user="user" :userSelf="userSelf" v-if="chosenTab === 'info'" @userChanged="loadUser">
          </UserInfo>
        </el-tab-pane>
        <el-tab-pane label="提问" name="question">
          <UserQuestion :userId="pageUserId" v-if="chosenTab === 'question'" style="width: 100%">
          </UserQuestion>
        </el-tab-pane>
        <el-tab-pane label="回答" name="answer">
          <UserAnswer :userId="pageUserId" v-if="chosenTab === 'answer'" style="width: 100%"></UserAnswer>
        </el-tab-pane>
        <el-tab-pane label="关注与收藏" name="following">
          <UserFollowing :userId="pageUserId" :userSelf="userSelf" v-if="chosenTab === 'following'"></UserFollowing>
        </el-tab-pane>
        <el-tab-pane v-if="userSelf" label="私信" name="message">
<!--          <UserMessage :userId="pageUserId" v-if="chosenTab === 'message'"></UserMessage>-->
          <UserMessageUser :userId="pageUserId" v-if="chosenTab === 'message'" style="width: 100%"></UserMessageUser>
        </el-tab-pane>
        <el-tab-pane v-if="userSelf" label="消息" name="notification">
          <UserNotification :user-id="pageUserId" v-if="chosenTab === 'notification'"></UserNotification>
        </el-tab-pane>
        <el-tab-pane v-if="userSelf && store.state.isAdminRole" label="社区管理" name="management">
          <UserManagement v-if="chosenTab === 'management'"></UserManagement>
        </el-tab-pane>
        <el-tab-pane v-if="userSelf" label="账号设置" name="setting">
          <UserSetting :user="user" v-if="chosenTab === 'setting'" @userChanged="loadUser"></UserSetting>
        </el-tab-pane>
      </el-tabs>
      <UserMessageDialog v-model="showMessageDialog" :otherId="pageUserId"></UserMessageDialog>
      <UserPunishDialog v-model="showPunishDialog" :userId="pageUserId" :unpunished="!punishment"
                        @changed="loadUser"></UserPunishDialog>
    </el-main>

  </el-container>

</template>

<style scoped>
.user-info {
  display: flex;
  align-items: center;
}

.container {
  padding: 20px 100px;
  width: 100%;
}

.name {
  font-size: 24px;
  font-weight: 700;
  margin: 5px;
}

.description {
  font-size: 16px;
  font-weight: 400;
  margin: 5px;
}

.punishment {
  font-size: 16px;
  font-weight: 600;
  color: red;
  margin: 5px;
}

.user-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
