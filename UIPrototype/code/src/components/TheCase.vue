<script setup>
import {
  Edit,
  Delete,
  Lock,
  Unlock,
  CaretTop,
  Star,
  EditPen,
  UserFilled
} from '@element-plus/icons-vue';
import AuthorInfo from "./AuthorInfo.vue";
import {useStore} from 'vuex';
import {inject, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router'
import {ElMessage, ElMessageBox} from "element-plus";

const axios = inject('axios');
const solved = true;
const store = useStore();
const props = defineProps(['caseInfo']);
const showOpen = ref(false);

const route = useRouter()


function CloseOrOpenCase() {

  console.log(props.caseInfo.caseId);
  axios
      .get(`/case/editopen/${props.caseInfo.caseId}`)
      .then(resp => {
        props.caseInfo.open = resp.data.open;
        console.log(resp.data);
      });
}

function editQuestion(){
  if(!store.state.isNormalRole){
    ElMessage.error('您已被禁言，无法编辑案例。');
  }
  else{
    route.push({name:'Casepublish',params:{publishId:props.caseInfo.caseId}});
  }
}


function deleteQuestion() {
  ElMessageBox
      .confirm(
          '确认删除吗？',
          '警告',
          {
            confirmButtonText: '删除',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
      .then(() => {
        axios
            .get("/case/delete", {
              params: {caseId: props.caseInfo.caseId}
            })
            .then(resp => {
              if (resp && resp.status === 200) {
                ElMessage({
                  message: '删除成功',
                  type: 'success',
                })
                route.push("/casehome");
              }
            })
            .catch(() => {
              ElMessage({
                message: '删除失败，请稍后重试或联系维护人员。',
                type: 'error',
              });
            })
      })
}

onMounted(() => {
console.log(store.state.isNormalRole);
})
</script>

<template>
  <div id="question-wrapper">
    <div id="question-body-wrapper">
      <div id="author-cap-edit">
        <div id="author-cap">
          <AuthorInfo :user-id="caseInfo.userId" :release-time="caseInfo.releaseTime" :thumbnail-type=0>
          </AuthorInfo>

          <h2 id="question-caption">
            {{ caseInfo.questionTitle }}
          </h2>
        </div>

<!--        <div id="show-solved" v-show="solved">-->
<!--          <el-icon color="forestgreen"><Select/></el-icon>-->
<!--          <span> 已审核</span>-->
<!--        </div>-->

        <div id="edit-question" v-if="Number(store.state.userId) === caseInfo.userId || store.state.isAdminRole">
          <el-button type="primary" :icon="Edit" plain v-if="Number(store.state.userId) === caseInfo.userId"
                     @click="editQuestion">编辑案例
          </el-button>
          <el-button v-if="props.caseInfo.open===1 && Number(store.state.userId) === caseInfo.userId" type="info" :icon="Lock" plain @click="CloseOrOpenCase">关闭案例
          </el-button>
          <el-button v-if="props.caseInfo.open===0 && Number(store.state.userId) === caseInfo.userId" type="info" :icon="Unlock" plain @click="CloseOrOpenCase">开放案例
          </el-button>
          <el-button type="danger" :icon="Delete" plain @click="deleteQuestion">删除案例</el-button>
        </div>
      </div>

      <p id="question-text">
        {{ caseInfo.questionText }}
      </p>

      <div id="question-tags">
        <el-tag v-for="(tag,index) in caseInfo.tagsOfQuestion" :key="index"
                size="large" effect="light" round>
          {{ tag }}
        </el-tag>
      </div>


      <!--      <div id="operate-question">-->
      <!--        <div class="button-group-wrapper">-->
      <!--          <el-button-group>-->
      <!--            <el-button type="primary" :icon="CaretTop" plain>点赞 18</el-button>-->
      <!--            <el-button type="primary" :icon="Star" plain>收藏案例 360</el-button>-->
      <!--          </el-button-group>-->
      <!--        </div>-->
      <!--      </div>-->
    </div>
  </div>
</template>


<style scoped>
#question-wrapper {
  /*background-color: #ecf5ff;*/
  border-bottom: solid;
  border-color: slategray;
  margin-top: 1em;
}

#question-body-wrapper {
  width: 60%;
  margin: auto;
}

#author-cap-edit {
  display: flex;
  justify-content: space-between;
}

#edit-question {
  display: flex;
  flex-direction: column;
}

#edit-question > * {
  width: 100%;
  position: relative;
  margin-left: 0;
  margin-right: 0;
  margin-bottom: 0.3em;
}

#question-caption {
  word-break: break-all;
}

#question-text {
  margin: 0;
}

#question-tags {
  margin-top: 2em;
  margin-bottom: 1em;
}

#question-tags > * {
  margin-right: 1em;
  padding: 0.3em;
  font-size: 90%;
  /*color: darkslateblue;*/
  /*background-color: lightblue;*/
  border-radius: 0.7em;
}

/*#operate-question {*/
/*  margin-right: 1em;*/
/*}*/

/*.button-group-wrapper {*/
/*  display: inline-block;*/
/*  margin-right: 1em;*/
/*  margin-top: 1em;*/
/*  margin-bottom: 1em;*/
/*}*/

#show-solved > * {
  vertical-align: middle;
  color: forestgreen;
}


#show-solved > span {
  font-size: 1.2em;
}
</style>