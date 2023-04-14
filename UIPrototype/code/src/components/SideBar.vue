<script setup>
import {ref, onMounted,inject} from "vue";
import {useRouter} from 'vue-router';
import {useStore} from "vuex";
import AuthorInfo from '../components/AuthorInfo.vue'
import {Plus, Star, ChatDotSquare, Message, RefreshRight, Bell} from '@element-plus/icons-vue'

const axios = inject('axios');
const router = useRouter();
const store = useStore();

function jumpToUser(tab) {
  store.commit('setUserTab', tab);
  router.push('/user');
}

const recUserIds = ref([]);
function getRecUser() {
  axios
      .get('/user/recommend', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        console.log(resp.data);
        recUserIds.value = resp.data;
      })
}

onMounted(() => {
  getRecUser();
});
</script>


<template>
  <div id="side-bar-inner-container">
    <el-card>
      <template #header>
        <div id="card-header">
          <span>推荐关注 </span>
          <el-icon>
            <Plus/>
          </el-icon>
          <el-button id="refresh" :icon="RefreshRight" text @click="getRecUser">换一批</el-button>
        </div>
      </template>
      <div id="rec-user">
        <AuthorInfo v-for="userId in recUserIds"
                    class="one-rec-user"
                    :user-id="userId" :thumbnail-type=1
                    style="width:100%">
        </AuthorInfo>
      </div>
    </el-card>

    <el-card style="margin-top:2vmin;">
      <div class="menu">
        <el-link class="link" :underline="false" @click="jumpToUser('question')">
          <el-icon>
            <QuestionFilled/>
          </el-icon>
          <span style="margin-left: 5px">
            我的提问
          </span>
        </el-link>
        <br/>
        <el-link class="link" :underline="false" @click="jumpToUser('answer')">
          <el-icon>
            <ChatDotSquare/>
          </el-icon>
          <span style="margin-left: 5px">
            我的回答
          </span>
        </el-link>
        <br/>
        <el-link class="link" :underline="false" @click="jumpToUser('following')">
          <el-icon>
            <Star/>
          </el-icon>
          <span style="margin-left: 5px">
            我的关注
          </span>
        </el-link>
        <br/>
        <el-link class="link" :underline="false" @click="jumpToUser('message')">
          <el-icon>
            <Message/>
          </el-icon>
          <span style="margin-left: 5px">
            我的私信
          </span>
        </el-link>
        <br/>
        <el-link class="link" :underline="false" @click="jumpToUser('notification')">
          <el-icon>
            <Bell/>
          </el-icon>
          <span style="margin-left: 5px">
            我的消息
          </span>
        </el-link>
      </div>
    </el-card>
  </div>
</template>


<style scoped>
#side-bar-inner-container {

}

#refresh {
  float: right;
  position: relative;
  top: -3px;
}

#card-header > * {
  vertical-align: middle;
}

#rec-user {
  /*height: 25vmin;*/
}

/*#rec-user > * {*/

/*}*/

.one-rec-user {
  /*margin: 100px;*/
}

.menu {
  color: #909399;
}

.link {
  font-size: 16px;
  margin: 5px;
  color: #909399;
}
</style>