<script setup>
import {useStore} from 'vuex';
import {inject, onMounted, ref, watch, computed} from 'vue';
import AuthorInfo from "@/components/AuthorInfo.vue";

const store = useStore();
const axios = inject('axios');

const emit = defineEmits(['getUserName']);

const props = defineProps(['questionId', 'answerId', 'commentId']);

const commentInfo = ref({
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

function setCommenterName(nameOfUser) {
  emit('getUserName', nameOfUser);
}

onMounted(() => {
  loadComment();
})
</script>

<template>
  <div class="answer-comment-thumbnail-wrapper">
    <el-card class="one-a-c-wrapper">
      <AuthorInfo :user-id="commentInfo.commenterId" :thumbnail-type=0
                  :release-time="commentInfo.releaseTime"
                  @get-author-name="setCommenterName"></AuthorInfo>
      <div class="c-text"
           @click="this.$router.push(`/question/${props.questionId}/${props.answerId}/${store.state.userId}`)">
        {{ commentInfo.commentText }}
      </div>
    </el-card>
  </div>
</template>


<style scoped>
.one-a-c-wrapper {
  margin-top: 0.7em;
  border-radius: 0.5em;
  border-width: 0.1em;
}

.c-text {
  margin-top: 0.5em;
}

.c-text:hover {
  cursor: pointer;
}
</style>