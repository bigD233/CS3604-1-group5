<script setup>
import {useStore} from 'vuex';
import {inject, onMounted, ref} from 'vue';
import {Edit, Delete, Select, CloseBold} from '@element-plus/icons-vue';
import AuthorInfo from "@/components/AuthorInfo.vue";
import OperatingArticle from "@/components/OperatingArticle.vue";
import {ElMessage, ElMessageBox} from "element-plus";

const store = useStore();
const axios = inject('axios');

const props = defineProps(["answerInfo", "questionerId"]);
// 删除该回答时调用 ansNumDec, 通知组件 AllAnswers 将回答总数减 1;
const emit = defineEmits(["ansNumDec"]);
// 删除后置为 false
const alive = ref(true);

function deleteAnswer() {
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
            .get("/answer/delete", {
              params: {answerId: props.answerInfo.answerId}
            })
            .then(resp => {
              if (resp && resp.status === 200) {
                alive.value = false;
                emit('ansNumDec');
                ElMessage({
                  message: '删除成功',
                  type: 'success',
                });
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

// function loadOneAnswer() {
//   axios
//       .get('/answer', {
//         params: {answerId: props.answerId}
//       })
//       .then(resp => {
//         if (resp && resp.status === 200) {
//           // answerInfo.value = resp.data;
//           answerInfo.value.answerText = resp.data.answerText;
//           answerInfo.value.isSolution = resp.data.isSolution;
//           answerInfo.value.saves = resp.data.saves;
//           answerInfo.value.likes = resp.data.likes;
//           answerInfo.value.dislikes = resp.data.dislikes;
//
//           answerInfo.value.authorInfo.releaseTime =
//               resp.data.releaseTime.split(' ')[0];
//         }
//       })
// }
//
// onMounted(() => {
// loadOneAnswer();
// authorInfo.value.releaseTime = props.answerInfo.releaseTime;
//   console.log(props);
// })
// onMounted(() => {
  // console.log(props.answerInfo);
// })
</script>


<template>
  <div v-if="answerInfo && alive" class="one-answer">
    <el-card>
<!--      <template #header v-if="props.answerInfo.solution">-->
<!--        <div class="solution-ans-wrapper">-->
<!--          <div class="solution-ans">-->
<!--            <el-icon color="forestgreen"><Select/></el-icon>-->
<!--            <span>解决方案</span>-->
<!--          </div>-->
<!--        </div>-->
<!--      </template>-->

      <div class="answerer-edit">
        <AuthorInfo :user-id="answerInfo.answererId" :thumbnail-type=0
                    :release-time="answerInfo.releaseTime"
                    :edit-time="answerInfo.editTime"></AuthorInfo>

        <div class="edit-answer">
          <!--          uncompleted-->
<!--          <el-button v-if="Number(store.state.userId) === questionerId"-->
<!--                     type="success" v-show="!props.answerInfo.isSolution" :icon="Select" text>标记为解决方案-->
<!--          </el-button>-->
<!--          <el-button v-if="Number(store.state.userId) === questionerId"-->
<!--                     type="danger" v-show="props.answerInfo.isSolution" :icon="CloseBold" text>取消标记为解决方案-->
<!--          </el-button>-->
          <el-button v-if="Number(store.state.userId) === answerInfo.answererId"
                     type="primary" :icon="Edit" text
                     @click="this.$router.push(
                         `/answer/edit/${answerInfo.markdown}/${answerInfo.questionId}/${answerInfo.answerId}`)"
          >修改
          </el-button>
          <el-button v-if="Number(store.state.userId) === answerInfo.answererId || Number(store.state.userId) === questionerId || store.state.isAdminRole"
                     type="danger" :icon="Delete" text
                     @click="deleteAnswer">删除
          </el-button>
        </div>
      </div>

      <v-md-editor v-if="answerInfo.markdown"
                   :model-value="answerInfo.answerText" mode="preview"></v-md-editor>
      <p v-else v-html="answerInfo.answerText" class="answer-text"></p>

      <OperatingArticle :type="'answer'"
                        :article-id="props.answerInfo.answerId"
                        :saves="props.answerInfo.saves"
                        :likes="props.answerInfo.likes"
                        :dislikes="props.answerInfo.dislikes"
                        :liked="props.answerInfo.liked"
                        :saved="props.answerInfo.saved"
                        :reported="props.answerInfo.reported"
      ></OperatingArticle>
    </el-card>
  </div>
</template>


<style scoped>
.one-answer {
  margin-bottom: 0.7em;
}

.solution-ans-wrapper {
  width: 90px;
}

.solution-ans > * {
  vertical-align: middle;
}

.solution-ans {
  height: 25px;
  text-align: center;
  border: solid 1px green;
  border-radius: 0.5em;
  background: honeydew;
}

.solution-ans > span {
  line-height: 25px;
  color: forestgreen;
  margin-left: 3px;
}

.answerer-edit {
  /*display: flex;*/
  /*justify-content: space-between;*/
}

.answerer-edit > * {
  display: inline-block;
}

.edit-answer {
  float: right;
}

.edit-answer:nth-child(2) {
  margin-right: 1em;
}

.answer-text {
  margin: 1em 1em 1em 40px;
  /*white-space: pre-line;*/
  /*line-height: 150%;*/
}
</style>
