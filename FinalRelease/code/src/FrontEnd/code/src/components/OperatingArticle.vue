<script setup>
import {useStore} from 'vuex';
import {StarFilled, CaretTop, CaretBottom, WarnTriangleFilled, Hide, ChatDotSquare}
  from '@element-plus/icons-vue';
import {inject, onMounted, ref} from 'vue';
import CommentNode from "@/components/CommentNode.vue";
import {formatTime} from "@/utils/timeUtils";
import {ElMessage} from "element-plus";

const store = useStore();
const axios = inject('axios');

const props = defineProps([
  "type", "articleId", "saves", "saved", "likes", "dislikes", "liked", "reported"
]);

const children = ref([]);
const numOfTotalComments = ref(0);
const showComments = ref(false);
const comment = ref('');
const liked = ref(props.liked);
const saved = ref(props.saved);
const likes = ref(props.likes);
const dislikes = ref(props.dislikes);
const saves = ref(props.saves);
const reported = ref(props.reported);

// 举报对话框是否可见
const dialogVisible = ref(false);
const reportReason = ref("");


function likeOrDislikeArticle(like, event) {
  let evalType;
  if (like) {  // 点赞或取消点赞
    evalType = (liked.value === 1) ? 1 : 0;  // 0 则点赞, 1 则取消点赞
  } else {  // 点踩或取消点踩
    evalType = (liked.value === 2) ? 3 : 2;  // 2 则点踩, 3 则取消点踩
  }
  axios
      .get("/" + props.type + "/like", {
        params: {
          userId: store.state.userId,
          articleId: props.articleId,
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


function saveArticle(event) {
  let evalType = saved.value ? 1 : 0;
  axios
      .get("/" + props.type + "/save", {
        params: {
          userId: store.state.userId,
          articleId: props.articleId,
          evalType: evalType,
        }
      })
      .then(resp => {
        switch (evalType) {
          case 0:
            saved.value = true;
            saves.value += 1;
            break;
          case 1:
            saved.value = false;
            saves.value -= 1;
            break;
        }
      })
  handleClick(event);
}


function reportArticle() {
  dialogVisible.value = false;
  axios
      .get("/" + props.type + "/report", {
        params: {
          userId: store.state.userId,
          articleId: props.articleId,
          reason: reportReason.value
        }
      })
      .then(resp => {
        reported.value = 1;
        ElMessage({
          message: '已通知管理员进行处理',
          type: 'success',
        })
      })
  reportReason.value = "";
}

const closeDialog = (done) => {
  reportReason.value = "";
  done();
};


function handleClick(event) {
  let target = event.target;
  while (target.nodeName !== "BUTTON") {
    target = event.target.parentNode;
  }
  target.blur();
}


function addComment() {
  if (comment.value === "") {
    ElMessage({
      message: '内容不能为空',
      type: 'error',
    });
    return;
  }

  axios
      .post("/" + props.type + "/comments/add", {
        commentText: comment.value,
        parentId: props.articleId,
        levelOne: true,
        commenterId: store.state.userId,
        releaseTime: formatTime(),
      })
      .then(resp => {
        ElMessage({
          message: '评论成功',
          type: 'success',
        })

        if (children.value.length === 0)
          loadChildren();
        else
          children.value.push(resp.data);

        numOfTotalComments.value += 1;

        showComments.value = true;
        comment.value = "";
      })
      .catch(() => {
        ElMessage.error('回复失败，请稍后重试或联系维护人员。')
      })
}

function loadChildren() {
  axios
      .get("/" + props.type + "/comments/get", {
        params: {
          parentId: props.articleId,
          levelOne: 1,  // getting comments of the article
          visitorId: store.state.userId
        }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          children.value = resp.data;

          // 计算评论总数
          numOfTotalComments.value = children.value.length;
          for (let child of children.value) {
            numOfTotalComments.value += child.descendants;
          }
        }
      })
}

onMounted(() => {
  loadChildren();
})
</script>


<template>
  <div class="operate-article">
    <div class="op-buttons-wrapper">
      <div class="op-button-group-wrapper">
        <el-button-group>
          <el-button type="primary" :icon="StarFilled"
                     :plain="!saved"
                     @click="saveArticle($event)">收藏 {{ saves }}
          </el-button>
          <el-button type="primary" :icon="CaretTop"
                     :plain="props.type==='answer' && liked!==1 || props.type==='case' && !liked"
                     :disabled="props.type==='answer' && liked===2"
                     @click="likeOrDislikeArticle(true, $event)">有帮助 {{ likes }}
          </el-button>
        </el-button-group>

        <el-button-group v-if="props.type==='answer'">
          <el-button type="danger" :icon="CaretBottom"
                     :plain="liked!==2" :disabled="liked===1"
                     @click="likeOrDislikeArticle(false, $event)">不喜欢 {{ dislikes }}
          </el-button>
          <el-button type="danger" :icon="WarnTriangleFilled"
                     :plain="!reported" :disabled="reported"
                     @click="dialogVisible=true"
          >举报
          </el-button>

          <!--          <el-button type="danger" :icon="Hide" plain>屏蔽</el-button>-->
        </el-button-group>
        <el-dialog v-model="dialogVisible" title="举报原因" :before-close="closeDialog">
          <el-input v-model="reportReason" type="textarea" autosize/>
          <template #footer>
              <span class="dialog-footer">
                <el-button @click="reportReason=''; dialogVisible=false">取消</el-button>
                <el-button :disabled="reportReason===''" type="primary" @click="reportArticle">
                  举报
                </el-button>
              </span>
          </template>
        </el-dialog>
      </div>

      <el-button type="primary" :icon="ChatDotSquare" plain
                 @click="showComments = !showComments">
        {{ numOfTotalComments }} 条评论 &thinsp;
        <el-icon v-if="!showComments">
          <ArrowDown/>
        </el-icon>
        <el-icon v-if="showComments">
          <ArrowUp/>
        </el-icon>
      </el-button>
    </div>


    <div id="all-comments" v-show="showComments">
      <div id="comment-article" v-show="showComments">
        <el-input v-model="comment"
                  :placeholder="'评论'"
                  autosize
                  type="textarea"/>
        <el-button type="primary" plain @click="addComment"
                   :disabled="!store.state.isNormalRole">发布</el-button>
      </div>

      <CommentNode :type="props.type"
                   v-for="comment in children" :comment="comment"
                   @desc-inc="numOfTotalComments+=1"></CommentNode>
    </div>
  </div>
</template>


<style scoped>
.op-buttons-wrapper {
  margin: 1em 1em 1em 40px;
  display: flex;
  justify-content: space-between;
}

.op-buttons-wrapper > * {
  display: inline-block;
  margin: 0.3em;
}

.op-button-group-wrapper > * {
  margin: 2px;
}

#all-comments {
  width: 88%;
  border-top: solid darkgray 1px;
  padding-top: 1em;
  position: relative;
  left: 40px;
}

#comment-article > * {
  margin-bottom: 2vmin;
}
</style>
