<script setup>
import {useStore} from 'vuex';
import {inject, onMounted, ref} from 'vue';
import {
  CaretTop,
  CaretBottom,
  WarnTriangleFilled,
  Hide,
  MoreFilled,
  ChatDotSquare,
  Delete
} from '@element-plus/icons-vue';
import {ElMessage, ElMessageBox} from "element-plus";
import {formatTime} from "@/utils/timeUtils.js"

import AuthorInfo from "@/components/AuthorInfo.vue";

const store = useStore();
const axios = inject('axios');

// const props = defineProps(["comment"]);
const props = defineProps({
  type: String,
  comment: {
    commentId: Number,
    commentText: String,
    parentId: Number,
    commenterId: Number,
    releaseTime: String,
    likes: Number,
    dislikes: Number,
    descendants: Number,
    deleted: Boolean,
    liked: Number  // 为 0 则未点赞或点踩，为 1 则点赞，为 2 则点踩
  },
});
const children = ref([]);
const descendantsNum = ref(props.comment.descendants);
const liked = ref(props.comment.liked);
const likes = ref(props.comment.likes);
const dislikes = ref(props.comment.dislikes);
const deleted = ref(props.comment.deleted);

// 添加回复时调用 descInc, 通知所有祖先节点将后代数加 1;
const emit = defineEmits(["descInc"]);

const showComments = ref(false);
const inputComment = ref(false);

const reply = ref('');


function likeOrDislikeComment(like, event) {
  let evalType;
  if (like) {  // 点赞或取消点赞
    evalType = (liked.value === 1) ? 1 : 0;  // 0 则点赞, 1 则取消点赞
  } else {  // 点踩或取消点踩
    evalType = (liked.value === 2) ? 3 : 2;  // 2 则点踩, 3 则取消点踩
  }
  axios
      .get("/" + props.type + "/comments/evaluate", {
        params: {
          userId: store.state.userId,
          commentId: props.comment.commentId,
          evalType: evalType,
        }
      })
      .then(resp => {
        switch (evalType) {
          case 0:
            liked.value = 1;
            likes.value += 1;
            break;
          case 1:
            liked.value = 0;
            likes.value -= 1;
            break;
          case 2:
            liked.value = 2;
            dislikes.value += 1;
            break;
          case 3:
            liked.value = 0;
            dislikes.value -= 1;
            break;
        }
      })
  handleClick(event);
}

function handleClick(event) {
  let target = event.target;
  while (target.nodeName !== "BUTTON") {
    target = event.target.parentNode;
  }
  target.blur();
}

function loadChildren() {
  axios
      .get("/" + props.type + "/comments/get", {
        params: {
          parentId: props.comment.commentId,
          levelOne: 0,
          visitorId: store.state.userId
        }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          children.value = resp.data;
        }
      })
}

function toggleComment() {
  showComments.value = !showComments.value;
  if (children.value.length === 0) {
    loadChildren();
  }
}

function addComment() {
  if (reply.value === "") {
    ElMessage({
      message: '内容不能为空',
      type: 'error',
    });
    return;
  }

  axios
      .post("/" + props.type + "/comments/add", {
        commentText: reply.value,
        parentId: props.comment.commentId,
        levelOne: false,
        commenterId: store.state.userId,
        releaseTime: formatTime(),
      })
      .then(resp => {
        ElMessage({
          message: '回复成功！',
          type: 'success',
        })

        if (children.value.length === 0)
          loadChildren();
        else
          children.value.push(resp.data);

        descendantsNum.value += 1;
        emit('descInc');

        inputComment.value = false;
        showComments.value = true;
        reply.value = "";
      })
      .catch(() => {
        ElMessage.error('回复失败，请稍后重试或联系维护人员。')
      })
}

function incDesc() {
  descendantsNum.value += 1;
  emit('descInc');
}

function deleteComment() {
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
            .get("/" + props.type + "/comments/delete", {
              params: {
                commentId: props.comment.commentId,
              }
            })
            .then(resp => {
              ElMessage({
                message: '删除成功',
                type: 'success',
              })

              deleted.value = true;
            })
      })
}

// onMounted(() => {
// console.log(props.type);
// })
</script>


<template>
  <div class="comment-node">
    <div class="commenter-delete">
      <AuthorInfo :user-id="comment.commenterId" :release-time="comment.releaseTime"
                  :thumbnail-type=2></AuthorInfo>

      <el-button v-if="Number(store.state.userId) === comment.commenterId && !deleted"
                 type="danger" @click="deleteComment" :icon="Delete" text>删除
      </el-button>
    </div>

    <div v-if="!deleted" class="comment-text">{{ props.comment.commentText }}</div>
    <div v-else class="comment-text" style="color:#909399">
      内容已被删除
    </div>

    <div class="operate-comment">
      <el-button type="primary" :icon="CaretTop" text
                 :bg="liked===1" :disabled="liked===2 || deleted"
                 @click="likeOrDislikeComment(true, $event)">
        点赞 {{ likes }}
      </el-button>

      <div class="dislike-wrapper">
        <el-button-group>
          <el-button type="danger" :icon="CaretBottom" text
                     :bg="liked===2" :disabled="liked===1 || deleted"
                     @click="likeOrDislikeComment(false, $event)">
            不喜欢 {{ dislikes }}
          </el-button>

          <!--          <el-tooltip-->
          <!--              class="box-item"-->
          <!--              effect="light"-->
          <!--              placement="top"-->
          <!--              :disabled="deleted">-->
          <!--            <template #content>-->
          <!--              <span style="color:#909399">更多操作</span>-->
          <!--            </template>-->
          <!--            <div class="more-wrapper">-->
          <!--              <el-popover placement="top" :width="80" trigger="click">-->
          <!--                <template #reference>-->
          <!--                  <el-button type="danger" :icon="MoreFilled" text :disabled="deleted"-->
          <!--                  ></el-button>-->
          <!--                </template>-->
          <!--                <div class="report-hide">-->
          <!--                  <el-button type="danger" :icon="WarnTriangleFilled" text>-->
          <!--                    举报-->
          <!--                  </el-button>-->
          <!--                  <el-button type="danger" :icon="Hide" text>-->
          <!--                    屏蔽-->
          <!--                  </el-button>-->
          <!--                </div>-->
          <!--              </el-popover>-->
          <!--            </div>-->
          <!--          </el-tooltip>-->
        </el-button-group>
      </div>


      <div class="reply-icon-wrapper">
        <el-button-group>
          <el-button type="primary" :icon="ChatDotSquare" text
                     @click="inputComment=!inputComment">
            <span v-show="!inputComment">回复</span>
            <span v-show="inputComment">取消回复</span>
          </el-button>

          <el-button type="primary" text
                     v-if="descendantsNum > 0"
                     @click=toggleComment>
            <span v-show="!showComments">展开 &thinsp;</span>
            <span v-show="showComments">收起 &thinsp;</span>
            {{ descendantsNum }}&thinsp;条回复&thinsp;
            <el-icon v-show="!showComments">
              <ArrowDown/>
            </el-icon>
            <el-icon v-show="showComments">
              <ArrowUp/>
            </el-icon>
          </el-button>

        </el-button-group>
      </div>

    </div>

    <div class="reply" v-show="inputComment">
      <el-input v-model="reply"
                :placeholder="'回复'"
                autosize
                type="textarea"/>
      <el-button type="primary" plain @click="addComment"
                 :disabled="!store.state.isNormalRole">发布
      </el-button>
    </div>

    <div class="children-comments" v-show="showComments">
      <CommentNode :type="props.type"
                   v-for="comment of children" :comment="comment"
                   @desc-inc="incDesc"></CommentNode>
    </div>
  </div>
</template>


<style scoped>
.comment-node {
  /*max-width: 95%;*/
}

.commenter-delete > * {
  display: inline-block;
}

.commenter-delete > button {
  float: right;
}

.comment-text {
  position: relative;
  left: 6vmin;
  margin-top: 2vmin;
  width: calc(100% - 6vmin);
}

.operate-comment {
  margin: 2vmin 0 2vmin 5vmin;
}

.operate-comment > * {
  display: inline-block;
}

.more-wrapper {
  display: inline-block;
}

.report-hide {
  display: flex;
  flex-direction: column;
}

.report-hide > * {
  margin: 0 !important;
}

.reply-icon-wrapper {
  float: right;
  /*margin-right: 10vmin;*/
}

.reply {
  position: relative;
  left: 5vmin;
  width: calc(100% - 5vmin);
  /*margin-bottom: 3vmin;*/
}

.reply > * {
  margin-bottom: 2vmin;
}

.children-comments {
  position: relative;
  left: 5vmin;
  width: calc(100% - 5vmin);
}
</style>

<!--<style>-->
<!--.el-popover {-->
<!--  min-width: 80px !important;-->
<!--  padding: 0 !important;-->
<!--}-->
<!--</style>-->