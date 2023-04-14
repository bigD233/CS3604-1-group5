<script setup>
import {useStore} from 'vuex';
import {inject, onMounted, ref, watch, computed} from 'vue';
import AuthorInfo from "@/components/AuthorInfo.vue";

const store = useStore();
const axios = inject('axios');

const emit = defineEmits(['getUserName']);

const props = defineProps(['commentId', 'replyId']);

const commentInfo = ref({
  commenterId: Number,
  releaseTime: "",
  commentText: String
})

const replyInfo = ref({
  commenterId: Number,
  releaseTime: "",
  commentText: String
})

function loadComment() {
  axios
      .get("/answer/comments/getOne", {
        params: {commentId: props.commentId}
      })
      .then(resp => {
        commentInfo.value.commenterId = resp.data.commenterId;
        commentInfo.value.releaseTime = resp.data.releaseTime;
        commentInfo.value.commentText = resp.data.commentText;
      })
}

function loadReply() {
  axios
      .get("/answer/comments/getOne", {
        params: {commentId: props.replyId}
      })
      .then(resp => {
        replyInfo.value.commenterId = resp.data.commenterId;
        replyInfo.value.releaseTime = resp.data.releaseTime;
        replyInfo.value.commentText = resp.data.commentText;
      })
}

function setCommenterName(nameOfUser) {
  emit('getUserName', nameOfUser);
}

onMounted(() => {
  loadComment();
  loadReply();
})
</script>

<template>
  <div class="comment-reply-thumbnail-wrapper">
    <el-card class="one-c-r-wrapper">
      <div class="c-wrapper">
        <div class="c-header">
          <AuthorInfo :user-id="commentInfo.commenterId" :thumbnail-type=0
                      :release-time="commentInfo.releaseTime"></AuthorInfo>
        </div>

        <div class="c-text">
          {{ commentInfo.commentText }}
        </div>
      </div>

      <div class="r-wrapper">
        <AuthorInfo :user-id="replyInfo.commenterId" :thumbnail-type=0
                    :release-time="replyInfo.releaseTime"
                    @get-author-name="setCommenterName"></AuthorInfo>
        <div class="r-text">
          {{ replyInfo.commentText }}
        </div>
      </div>
    </el-card>
  </div>
</template>


<style scoped>
.one-c-r-wrapper {
  margin-top: 0.7em;
  border-radius: 0.5em;
  border-width: 0.1em;
}

.c-text {
  margin: 0.5em;
}

.r-text {
  margin-top: 0.5em;
}

.r-wrapper {
  padding-top: 0.5em;
  border-top: solid 1px darkgray;
}
</style>