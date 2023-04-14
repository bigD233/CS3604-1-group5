<script setup>
import {ref, inject, onMounted, watch} from 'vue';
import {useStore} from 'vuex';
import {ElMessage} from "element-plus";
import QAThumbnail from "@/components/QAThumbnail.vue";
import ReplyThumbnail from "@/components/ReplyThumbnail.vue";
import AnswerCommentThumbnail from "@/components/AnswerCommentThumbnail.vue";

const props = defineProps(['params']);

const store = useStore();
const axios = inject('axios');

const type = ref(props.params[0]);
const releaseTime = ref(props.params[1]);

const userName = ref("");  // 涉及到的用户名
function setUserName(nameOfUser) {
  userName.value = nameOfUser;
}

// onMounted(() => {
// })
</script>

<template>
  <div v-if="type==='1'">
    <el-timeline-item :timestamp="releaseTime" placement="top">
      <div class="desc">
        <span style="color:gray">{{ userName }}</span> 回答了我的问题
      </div>
      <QAThumbnail :answer-id="props.params[3]" style="font-size: 2.6vmin"
                   @get-user-name="setUserName"></QAThumbnail>
    </el-timeline-item>
  </div>

  <div v-else-if="type==='2'">
    <el-timeline-item :timestamp="releaseTime" placement="top">
      <div class="desc">
        <span style="color:gray">{{ userName }}</span> 回答了我关注的问题
      </div>
      <QAThumbnail :answer-id="props.params[3]" style="font-size: 2.6vmin"
                   @get-user-name="setUserName"></QAThumbnail>
    </el-timeline-item>
  </div>

  <div v-else-if="type==='3'">
    <el-timeline-item :timestamp="releaseTime" placement="top">
      <div class="desc">
        <span style="color:gray">{{ userName }}</span> 评论了我的回答
      </div>
      <AnswerCommentThumbnail :question-id="props.params[2]"
                              :answer-id="props.params[3]"
                              :comment-id="props.params[4]"
                              style="font-size: 2.6vmin"
                              @get-user-name="setUserName"></AnswerCommentThumbnail>
    </el-timeline-item>
  </div>

  <div v-else-if="type==='4'">
    <el-timeline-item :timestamp="releaseTime" placement="top">
      <div class="desc">
        <span style="color:gray"> {{ userName }}</span> 回复了我的评论
      </div>
      <ReplyThumbnail :comment-id="props.params[2]" :reply-id="props.params[3]"
                      style="font-size: 2.6vmin"
                      @get-user-name="setUserName"></ReplyThumbnail>
    </el-timeline-item>
  </div>

  <div v-else-if="type==='5'">
    <el-timeline-item :timestamp="releaseTime" placement="top">
      <div class="desc">
        我关注的用户 <span style="color:gray">{{ userName }}</span> 发起了提问
      </div>
      <QAThumbnail :question-id="props.params[3]" style="font-size: 2.6vmin"
                   @get-user-name="setUserName"></QAThumbnail>
    </el-timeline-item>
  </div>

  <div v-else-if="type==='6'">
    <el-timeline-item :timestamp="releaseTime" placement="top">
      <div class="desc">
        我关注的用户 <span style="color:gray">{{ userName }}</span> 发布了回答
      </div>
      <QAThumbnail :answer-id="props.params[4]" style="font-size: 2.6vmin"
                   @get-user-name="setUserName"></QAThumbnail>
    </el-timeline-item>
  </div>
</template>

<style scoped>
.desc {
  font-size: 3vmin;
}
</style>
