<script setup>
import {inject, onMounted, ref, computed, watch} from 'vue';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router'
import {Edit, Delete, Lock, Unlock, CaretTop, View, EditPen, UserFilled} from '@element-plus/icons-vue';

import AuthorInfo from "./AuthorInfo.vue";
import {ElMessage, ElMessageBox} from "element-plus";

const store = useStore();
const axios = inject('axios');
const route = useRouter()

const emit = defineEmits(['getQuestionerId']);

const props = defineProps(["questionId"]);

const quesInfo = ref({
  caption: "",
  content: "",
  tags: [],
  questionerId: 0,
  releaseTime: "",
  editTime: "",
  solved: false,
  closed: false,
  likes: 0,
  watchers: 0,
  markdown: false,
  liked: false,
  watched: false
})


function likeQuestion() {
  let evalType = quesInfo.value.liked ? 1 : 0;  // 0 则点赞, 1 则取消点赞
  axios
      .get("/question/like", {
        params: {
          userId: store.state.userId,
          questionId: props.questionId,
          evalType: evalType,
        }
      })
      .then(resp => {
        switch (evalType) {
          case 0:
            quesInfo.value.liked = true;
            quesInfo.value.likes += 1;
            break;
          case 1:
            quesInfo.value.liked = false;
            quesInfo.value.likes -= 1;
            break;
        }
      })
}


function watchQuestion() {
  let evalType = quesInfo.value.watched ? 1 : 0;
  axios
      .get("/question/watch", {
        params: {
          userId: store.state.userId,
          questionId: props.questionId,
          evalType: evalType,
        }
      })
      .then(resp => {
        switch (evalType) {
          case 0:
            quesInfo.value.watched = true;
            quesInfo.value.watchers += 1;
            break;
          case 1:
            quesInfo.value.watched = false;
            quesInfo.value.watchers -= 1;
            break;
        }
      })
}


function answerQuestion() {
  axios
      .get(`/question/getClosed`, {
        params: {questionId: props.questionId}
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          quesInfo.value.closed = resp.data;
          if (quesInfo.value.closed) {
            ElMessage({
              message: '问题已关闭，无法回答。',
              type: 'error',
            });
          } else {
            route.push(`/answer/edit/0/${props.questionId}/0`);
          }
        }
      })
      .catch(() => {
        ElMessage({
          message: '请求数据失败，请稍后重试或联系维护人员。',
          type: 'error',
        });
      })
}


function editQuestion() {
  if (allowEdit())
    route.push(`/question/edit/${quesInfo.value.markdown}/${props.questionId}`)
  else
    ElMessage({
      message: '问题提出时间大于 24 小时，无法修改。',
      type: 'error',
    });

}

function allowEdit() {
  // 返回是否允许提问者修改问题
  if (quesInfo.value.releaseTime !== "") {
    let targetTime = new Date(quesInfo.value.releaseTime);
    let nowTime = new Date();  // 当前时间
    let gap = nowTime - targetTime;  // 发布时间与当前时间间隔

    let hour = gap / 3600000;  // 距今小时
    return hour < 24;
  }
  return false
}


function closeQuestion() {
  axios
      .get("/question/closed", {
        params: {closed: true, question_id: props.questionId}
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          quesInfo.value.closed = true;
          ElMessage({
            message: '关闭成功',
            type: 'success',
          })
        }
      })
      .catch(() => {
        ElMessage({
          message: '关闭失败，请稍后重试或联系维护人员。',
          type: 'error',
        });
      })
}


function openQuestion() {
  axios
      .get("/question/closed", {
        params: {closed: false, question_id: props.questionId}
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          quesInfo.value.closed = false;
          ElMessage({
            message: '开放成功',
            type: 'success',
          })
        }
      })
      .catch(() => {
        ElMessage({
          message: '开放失败，请稍后重试或联系维护人员。',
          type: 'error',
        });
      })
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
            .get("/question/delete", {
              params: {questionId: props.questionId}
            })
            .then(resp => {
              if (resp && resp.status === 200) {
                ElMessage({
                  message: '删除成功',
                  type: 'success',
                })
                route.push("/qahome");
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


function loadQuestion() {
  axios
      .get(`/question/${props.questionId}/${store.state.userId}`)
      .then(resp => {
        if (resp && resp.status === 200) {
          quesInfo.value = resp.data;
          emit("getQuestionerId", quesInfo.value.questionerId);
        }
      })
      .catch(() => {
        ElMessage({
          message: '请求数据失败，请稍后重试或联系维护人员。',
          type: 'error',
        });
      })
}

onMounted(() => {
  loadQuestion();
})

</script>

<template>
  <div id="question-wrapper">
    <div id="question-body-wrapper">

      <div id="author-solved-cap-edit">

        <AuthorInfo :user-id="quesInfo.questionerId" :thumbnail-type=0
                    :release-time="quesInfo.releaseTime"
                    :edit-time="quesInfo.editTime"></AuthorInfo>

<!--        <div id="show-solved" v-show="quesInfo.solved">-->
<!--          <el-icon color="forestgreen"><Select/></el-icon>-->
<!--          <span>&thinsp;已解决</span>-->
<!--        </div>-->

        <h2 id="question-caption">
          {{ quesInfo.caption }}
        </h2>

        <div v-if="Number(store.state.userId) === quesInfo.questionerId || store.state.isAdminRole"
             id="edit-question">
          <el-tooltip v-if="Number(store.state.userId) === quesInfo.questionerId && allowEdit()"
                      placement="bottom" effect="light"
                      content="<span style='color:gray'>问题提出后 24 小时内可以编辑</span>"
                      raw-content>
            <el-button type="primary" :icon="Edit" plain
                       @click="editQuestion">
              编辑问题
            </el-button>
          </el-tooltip>

          <div id="open-question" v-if="Number(store.state.userId) === quesInfo.questionerId && quesInfo.closed">
            <el-tooltip placement="bottom" effect="light"
                        content="<span style='color:gray'>问题开放后<br/>将允许被回答</span>"
                        raw-content>
              <el-button type="info" :icon="Unlock" plain
                         @click="openQuestion">开放问题
              </el-button>
            </el-tooltip>
          </div>

          <div id="close-question" v-else-if="Number(store.state.userId) === quesInfo.questionerId && !quesInfo.closed">
            <el-tooltip placement="bottom" effect="light"
                        content="<span style='color:gray'>问题关闭后<br/>将无法被回答</span>"
                        raw-content>
              <el-button type="info" :icon="Lock" plain
                         @click="closeQuestion">关闭问题
              </el-button>
            </el-tooltip>
          </div>

          <el-button type="danger" :icon="Delete" plain
                     @click="deleteQuestion">删除问题
          </el-button>
        </div>
      </div>

      <v-md-editor v-if="quesInfo.markdown" :model-value="quesInfo.content" mode="preview"></v-md-editor>
      <p v-else v-html="quesInfo.content" id="question-content"></p>

      <div id="question-tags">
        <el-tag v-for="(tag,index) in quesInfo.tags" :key="index"
                size="large" effect="light" round>
          {{ tag.tagName }}
        </el-tag>
      </div>

      <div id="operate-question">
        <div class="button-group-wrapper">
          <el-button-group>
            <el-button type="primary" :icon="CaretTop"
                       :plain="!quesInfo.liked"
                       @click="likeQuestion">好问题 {{ quesInfo.likes }}
            </el-button>
            <el-button type="primary" :icon="View"
                       :plain="!quesInfo.watched"
                       @click="watchQuestion">关注问题 {{ quesInfo.watchers }}
            </el-button>
          </el-button-group>
        </div>

        <div class="button-group-wrapper">
          <el-tooltip placement="bottom" effect="light"
                      content="<span style='color:gray'>问题已关闭</span>"
                      raw-content
                      :disabled="!quesInfo.closed">
            <el-button-group>
              <el-button type="primary" :icon="EditPen" :disabled="quesInfo.closed || !store.state.isNormalRole"
                         @click="answerQuestion">我要回答
              </el-button>
<!--              <el-button type="primary" :icon="UserFilled" :disabled="quesInfo.closed"-->
<!--              >邀请回答-->
<!--              </el-button>-->
            </el-button-group>
          </el-tooltip>

        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
#question-wrapper {
  border-bottom: solid;
  border-color: slategray;
  margin-top: 1em;
}

#question-body-wrapper {
  margin-left: 25%;
  margin-right: 11%;
}

#author-solved-cap-edit {
  width: 100%;
  display: inline-grid;
  grid-template-columns: 66% 17% 17%;
  grid-template-rows: auto auto;
  grid-gap: 0;
  justify-items: start;
  align-items: center;
}

#author-solved-cap-edit:first-child {
  grid-column: 1;
  grid-row: 1;
}

#show-solved {
  padding-right: 3vmin;
  grid-column: 2;
  grid-row: 1;
  justify-self: right;
}

#show-solved > * {
  display: inline-block;
  vertical-align: middle;
  color: forestgreen;
}

#show-solved > span {
  font-size: 1.2em;
}

#edit-question {
  grid-column: 3;
  grid-row-start: 1;
  grid-row-end: 3;
  justify-self: right;
  align-self: start;
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
  grid-column-start: 1;
  grid-column-end: 3;
  grid-row-start: 2;
  word-break: break-all;
}

#question-content {
  margin: 0;
}

#question-tags {
  margin-top: 2em;
}

#question-tags > * {
  margin-right: 1em;
  padding: 0.3em;
  font-size: 90%;
  /*color: darkslateblue;*/
  /*background-color: lightblue;*/
  border-radius: 0.7em;
}

#operate-question {
  margin-right: 1em;
}

.button-group-wrapper {
  display: inline-block;
  margin-right: 1em;
  margin-top: 1em;
  margin-bottom: 1em;
}

/*.vuepress-markdown-body {*/
/*  padding: 0 !important;*/
/*}*/
</style>