<script setup>
import {ref, computed, inject, watch, onMounted} from 'vue';
import {useStore} from 'vuex';
import UserFollowingUser from '@/components/UserFollowingUser.vue';
import CaseThumbnail from "@/components/CaseThumbnail.vue";
import QAThumbnail from "@/components/QAThumbnail.vue";

const store = useStore();
const axios = inject('axios');

const props = defineProps(['userId', 'userSelf']);
const savedCaseIds = ref([]);
const watchedQuestionIds = ref([]);
const savedAnswerIds = ref([]);

onMounted(() => {
  // 获得所有收藏的案例
  axios
      .get('/case/saved', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        savedCaseIds.value = resp.data;
      })
  // 获得所有关注的问题
  axios
      .get('/question/watched', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        watchedQuestionIds.value = resp.data;
      })
  // 获得所有收藏的回答
  axios
      .get('/answer/saved', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        savedAnswerIds.value = resp.data;
      })
})

</script>

<template>
  <el-tabs tab-position="left">
    <el-tab-pane label="用户">
      <UserFollowingUser :userId="props.userId" :userSelf="props.userSelf" style="width: 100%"/>
    </el-tab-pane>
    <el-tab-pane label="案例">
      <CaseThumbnail v-for="caseId in savedCaseIds" :case-id="caseId"></CaseThumbnail>
    </el-tab-pane>
    <el-tab-pane label="提问">
      <QAThumbnail v-for="questionId in watchedQuestionIds" :question-id="questionId">
      </QAThumbnail>
    </el-tab-pane>
    <el-tab-pane label="回答">
      <QAThumbnail v-for="answerId in savedAnswerIds" :answer-id="answerId">
      </QAThumbnail>
    </el-tab-pane>
  </el-tabs>
</template>
