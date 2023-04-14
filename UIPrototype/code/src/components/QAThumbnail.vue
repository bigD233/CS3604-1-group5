<script setup>
import {useStore} from 'vuex';
import {inject, onMounted, ref, watch, computed} from 'vue';
import {ElMessage} from "element-plus";
import AuthorInfo from "@/components/AuthorInfo.vue";

const store = useStore();
const axios = inject('axios');

const emit = defineEmits(['getUserName']);

// 若 reportReason 非空, 则显示举报原因和回答, 否则,
// answerId 和 questionId 只有一个非空, answerId 非空则显示该答案及其问题, 否则只显示问题
const props = defineProps(["answerId", "questionId", "reportReason"]);

watch(  // 监听 props.questionId 变化
    () => props.questionId,
    (newProp) => {
      if (props.answerId != null)
        loadQAThumbnail();
      else
        loadQThumbnail();
    }
);

const answerInfo = ref({
  answerText: "",
  isSolution: false,
  answererId: 0,
  releaseTime: "",
  editTime: ""
})

const quesInfo = ref({
  questionId: 0,
  caption: "",
  questionerId: 0,
  releaseTime: "",
  editTime: "",
  solved: false,
  markdown: false,
})

const answerContainer = ref(null);  // 回答内容高度

const folded = computed(() => {   // 回答内容是否溢出而需要被折叠
  if (answerContainer.value) {
    // console.log(answerContainer.value.clientHeight);
    return answerContainer.value.clientHeight >= 100;
  }
  return false;
});


function loadQAThumbnail() {
  axios
      .get('/answer/one', {  // get info of answer and get id of question
        params: {
          answerId: props.answerId,
          visitorId: store.state.userId
        }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          answerInfo.value = resp.data;
          quesInfo.value.questionId = resp.data.questionId;
          axios
              .get(`/question/${quesInfo.value.questionId}/${store.state.userId}`)  // get info of questions
              .then(resp => {
                if (resp && resp.status === 200) {
                  quesInfo.value = resp.data;
                }
              })
              .catch(() => {
                ElMessage({
                  message: '请求数据失败，请稍后重试或联系维护人员。',
                  type: 'error',
                });
              })
        }
      })
      .catch(() => {
        ElMessage({
          message: '请求数据失败，请稍后重试或联系维护人员。',
          type: 'error',
        });
      })
}

function loadQThumbnail() {
  axios
      .get(`/question/${props.questionId}/${store.state.userId}`)  // get info of questions
      .then(resp => {
        if (resp && resp.status === 200) {
          quesInfo.value = resp.data;
        }
      })
      .catch(() => {
        ElMessage({
          message: '请求数据失败，请稍后重试或联系维护人员。',
          type: 'error',
        });
      })
}

function loadAThumbnail() {
  axios
      .get('/answer/one', {
        params: {
          answerId: props.answerId,
          visitorId: store.state.userId
        }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          answerInfo.value = resp.data;
          quesInfo.value.questionId = resp.data.questionId;
        }
      })
}

function setAnswererName(nameOfUser) {
  emit('getUserName', nameOfUser);
}

function setQuestionerName(nameOfUser) {
  if (props.answerId == null)
    emit('getUserName', nameOfUser);
}

onMounted(() => {
  if (props.reportReason != null)
    loadAThumbnail();
  else if (props.answerId != null)
    loadQAThumbnail();
  else
    loadQThumbnail();
})
</script>

<template>
  <div class="thumbnail-wrapper">
    <el-card class="one-q-a-wrapper">
      <div class="q-wrapper" v-if="props.reportReason==null">
        <div class="q-header">
          <AuthorInfo :user-id="quesInfo.questionerId" :thumbnail-type=0
                      :release-time="quesInfo.releaseTime"
                      :edit-time="quesInfo.editTime"
                      @get-author-name="setQuestionerName"></AuthorInfo>
          <!--          <div class="solved" v-if="quesInfo.solved">-->
          <!--            <el-icon color="forestgreen"><Select/></el-icon>-->
          <!--            <span>已解决</span>-->
          <!--          </div>-->
        </div>

        <h3 @click="this.$router.push(`/question/${quesInfo.questionId}/0/${store.state.userId}`)"
            class="q-caption qa-link">
          {{ quesInfo.caption }}
        </h3>
      </div>
      <div v-else class="report-reason">
        该回答因 <span class="report-reason-text">{{ props.reportReason }}</span> 被举报
      </div>

      <div v-if="props.answerId" class="a-wrapper">
        <AuthorInfo :user-id="answerInfo.answererId" :thumbnail-type=0
                    :release-time="answerInfo.releaseTime"
                    :edit-time="answerInfo.editTime"
                    @get-author-name="setAnswererName"></AuthorInfo>
        <div @click="this.$router.push(`/question/${quesInfo.questionId}/${props.answerId}/${store.state.userId}`)"
             class="a-text qa-link"
             ref="answerContainer"
             v-if="answerInfo.answerText">
          <v-md-editor v-if="answerInfo.markdown"
                       :model-value="answerInfo.answerText" mode="preview"></v-md-editor>
          <p v-else v-html="answerInfo.answerText" class="answer-text"></p>
        </div>
        <!--        <div class="mask-anchor">-->
        <div class="mask" v-if="folded"></div>
        <!--        </div>-->
      </div>
    </el-card>
  </div>
</template>


<style scoped>
.qa-link {
  cursor: pointer;
}

.one-q-a-wrapper {
  margin-top: 0.7em;
  border-radius: 0.5em;
  border-width: 0.1em;
}

.q-wrapper {
  /*padding-bottom: 0.5em;*/
}

.q-header {
  display: flex;
  justify-content: space-between;
}

.solved {
  height: 25px;
  text-align: center;
  border: solid 1px green;
  border-radius: 0.5em;
  background: honeydew;

  padding-left: 0.2em;
  padding-right: 0.2em;
}

.solved > * {
  vertical-align: middle;
}

.solved > span {
  line-height: 25px;
  color: forestgreen;
  margin-left: 3px;
}

.q-caption {
  margin: 0.3em;
}

.a-wrapper {
  padding-top: 0.7em;
  border-top: solid 1px darkgray;
  overflow-y: hidden;
}

.a-text {
  max-height: 100px;
}

/*以渐变的白色遮盖溢出内容*/
.mask {
  width: 100%;
  height: 10vmin;
  z-index: 2022;
  position: relative;
  bottom: -30px;
  background-image: linear-gradient(-180deg, rgba(255, 255, 255, 0) 0%, #ffffff 70%);
}

.report-reason {
  margin-bottom: 1vmin;
}

.report-reason-text {
  color: gray;
}
</style>