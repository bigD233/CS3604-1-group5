<script setup>
import {ref, inject, onMounted, computed} from 'vue';
import {useStore} from 'vuex';
import {ElMessage} from "element-plus";
import OneNotification from "@/components/OneNotification.vue";

const props = defineProps(['userId']);

const store = useStore();
const axios = inject('axios');

const completeFlag = ref(false);
const notifications = computed(() => {
  if (ansToQuestions.value === null || ansToWatchedQuestions.value === null ||
      commentToAns.value === null || replyToComments.value === null ||
      questionsOfFollowee.value === null || ansOfFollowee.value === null)
    return [];
  // 已经请求完毕
  completeFlag.value = true;
  let res = [];
  res.push.apply(res, ansToQuestions.value);
  res.push.apply(res, ansToWatchedQuestions.value);
  res.push.apply(res, commentToAns.value);
  res.push.apply(res, replyToComments.value);
  res.push.apply(res, questionsOfFollowee.value);
  res.push.apply(res, ansOfFollowee.value);
  console.log(res);
  res.sort(function (na, nb) {
    if (nb[1] > na[1])
      return 1;
    if (nb[1] < na[1])
      return -1;
    return 0;
  })
  return res;
});

const ansToQuestions = ref(null);

function loadAnsToQuestions() {
  // 获得自己所有提问下的回答
  axios
      .get('/answer/answerUser', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        ansToQuestions.value = resp.data;
      })
      .catch(() => {
        ElMessage.error('请求数据失败，请稍后重试或联系维护人员。')
      })
}

const ansToWatchedQuestions = ref(null);

function loadAnsToWatchedQuestions() {
  // 获得所有关注的提问下的回答
  axios
      .get('/answer/answerToWatched', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        ansToWatchedQuestions.value = resp.data;
      })
      .catch(() => {
        ElMessage.error('请求数据失败，请稍后重试或联系维护人员。')
      })
}

const commentToAns = ref(null);

function loadCommentToAns() {
  // 获得所有回答下的直接评论
  axios
      .get('/answer/comments/commentUser', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        commentToAns.value = resp.data;
      })
      .catch(() => {
        ElMessage.error('请求数据失败，请稍后重试或联系维护人员。')
      })
}

const replyToComments = ref(null);

function loadReplyToComments() {
  // 获得所有对回答的评论下的回复
  axios
      .get('/answer/comments/reply', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        replyToComments.value = resp.data;
      })
      .catch(() => {
        ElMessage.error('请求数据失败，请稍后重试或联系维护人员。')
      })
}

const questionsOfFollowee = ref(null);

function loadQuestionOfFollowee() {
  // 获得所有关注的用户发布的问题
  axios
      .get('/question/followeeQuestions', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        questionsOfFollowee.value = resp.data;
      })
      .catch(() => {
        ElMessage.error('请求数据失败，请稍后重试或联系维护人员。')
      })
}

const ansOfFollowee = ref(null);

function loadAnsOfFollowee() {
  // 获得所有关注的用户发布的答案
  axios
      .get('/answer/followeeAnswers', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        ansOfFollowee.value = resp.data;
      })
      .catch(() => {
        ElMessage.error('请求数据失败，请稍后重试或联系维护人员。')
      })
}

onMounted(() => {
  loadAnsToQuestions();
  loadAnsToWatchedQuestions();
  loadCommentToAns();
  loadReplyToComments();
  loadQuestionOfFollowee();
  loadAnsOfFollowee();
});
</script>

<template>
  <div v-show="completeFlag">
    <el-timeline>
      <OneNotification v-for="ntf in notifications" :params="ntf"></OneNotification>
    </el-timeline>
  </div>

</template>

<style scoped>

</style>
