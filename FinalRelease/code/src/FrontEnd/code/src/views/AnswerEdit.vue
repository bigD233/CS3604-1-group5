<script lang="ts" setup>
import {useStore} from 'vuex';
import {onMounted, ref, inject, reactive} from 'vue';
import Vue3Tinymce from '@jsdawn/vue3-tinymce';
import {ElMessage, ElInput} from 'element-plus'
import {useRouter} from 'vue-router'
import {formatTime} from "../utils/timeUtils.js"
import Header from '../components/Header.vue';

const store = useStore();
const axios = inject('axios');
const route = useRouter()

const editType = ref('富文本');

const answerId = ref(0);
const content = ref("");

const MDContent = ref("");

const RichTextState = reactive({
  content: "",
  // editor 配置项
  setting: {
    height: '71vmin', // editor 高度
    language: 'zh-Hans',
    language_url: '/tinymce/langs/zh-Hans.js',

    toolbar:
        'undo redo | fullscreen | blocks alignleft aligncenter alignright alignjustify | link unlink | numlist bullist | image table | fontsize forecolor backcolor | bold italic underline strikethrough | indent outdent | superscript subscript | removeformat |',
    toolbar_mode: 'sliding image',
    quickbars_selection_toolbar:
        'removeformat | bold italic underline strikethrough | fontsize forecolor backcolor',
    plugins: 'link image table lists fullscreen quickbars',
    font_size_formats: '12px 14px 16px 18px',
    link_default_target: '_blank',
    link_title: false,
    nonbreaking_force_tab: true,
    images_upload_handler: (blobInfo, progress) => new Promise((resolve, reject) => {
      let data = new FormData();
      data.append('file', blobInfo.blob());
      axios
          .post('/image', data)
          .then(resp => {
            if (resp.data) {
              resolve(resp.data);
            } else {
              reject(new Error('图片含有不当内容'));
            }
          })
          .catch(error => {
            reject(error);
          });
    })
  }
});

function handleUploadImage(event, insertImage, files) {
  for (let i in files) {
    const formData = new FormData();
    formData.append('file', files[i]);
    // console.log(formData);
    axios
        .post('/image', formData)
        .then(response => {
              if (response.data) {
                insertImage({
                  url: response.data,
                  desc: 'DESC',
                })
              } else {
                ElMessage.error('图片含有不当内容')
              }
            },
            error => {

            }
        )
  }
}

function show() {
  console.log(RichTextState.content);
}

const dialogVisible = ref(false)

function jumpToHome() {
  route.push('/home')
}

function submitAnswer() {
  content.value = (editType.value === "富文本" ? RichTextState.content : MDContent.value);

  if (content.value === "") {
    ElMessage({
      message: '内容不能为空',
      type: 'error',
    });
    return;
  }
  // console.log(content.value);

  if (route.currentRoute.value.params.answerId === "0") {
    // 发布回答
    axios
        .post("/answer/add", {
          answerText: content.value,
          questionId: route.currentRoute.value.params.questionId,
          answererId: store.state.userId,
          markdown: editType.value !== "富文本",
          releaseTime: formatTime(),
        })
        .then(resp => {
          if (resp && resp.status === 200) {
            answerId.value = resp.data.answerId;
            dialogVisible.value = true;
          }
        })
        .catch(() => {
          ElMessage.error('提交失败，请稍后重试或联系维护人员。')
        })
  } else {
    // 修改回答
    axios
        .post("/answer/edit", {
          answerId: route.currentRoute.value.params.answerId,
          answerText: content.value,
          markdown: editType.value !== "富文本",
          editTime: formatTime(),
        })
        .then(resp => {
          if (resp && resp.status === 200) {
            dialogVisible.value = true;
            answerId.value = parseInt(<string>route.currentRoute.value.params.answerId);
          }
        })
        .catch(() => {
          ElMessage.error('提交失败，请稍后重试或联系维护人员。')
        })
  }
}

function publishAnswer() {
  // 检查问题是否已关闭
  axios
      .get(`/question/getClosed`, {
        params: {questionId: route.currentRoute.value.params.questionId}
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          console.log(resp.data);
          if (resp.data == 1) {
            ElMessage({
              message: '问题已关闭，无法回答。',
              type: 'error',
            });
            route.push(`/question/${route.currentRoute.value.params.questionId}/0/${store.state.userId}`);
          } else if (resp.data == -1) {
            // 问题已被删除
            route.push('/nocontent');
          } else
            submitAnswer();
        }
      })
      .catch(() => {
        ElMessage({
          message: '请求数据失败，请稍后重试或联系维护人员。',
          type: 'error',
        });
      })
}

function loadAnswer() {
  axios
      // .get('/answer/one?answerId=' + route.currentRoute.value.params.answerId)
      .get('answer/one', {
        params: {
          answerId: route.currentRoute.value.params.answerId,
          visitorId: store.state.userId
        }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          if (route.currentRoute.value.params.markdown === "false")
            RichTextState.content = resp.data.answerText;
          else
            MDContent.value = resp.data.answerText;
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
  // 若为新建回答则 route.currentRoute.value.params.answerId 为 0,
  // 若为修改回答则 route.currentRoute.value.params.answerId 为该问题的 id
  if (route.currentRoute.value.params.answerId !== "0") {
    loadAnswer();
    // route.currentRoute.value.params.markdown 代表文本类型是否为 Markdown
    editType.value = (route.currentRoute.value.params.markdown === "true" ? "Markdown" : "富文本");
  }
})
</script>


<template>
  <header>
    <Header/>
  </header>

  <main>
    <el-card>

      <el-dialog v-model="dialogVisible" title="" width="30%" :show-close="false">
        <el-result icon="success" title="提交成功" sub-title=""></el-result>
        <template #footer>
            <span class="dialog-footer">
              <el-button @click="jumpToHome">返回首页</el-button>
                <el-button type="primary"
                           @click="this.$router.push(
                         `/question/${route.currentRoute.value.params.questionId}/${answerId}/${store.state.userId}`)">
                      查看回答
                </el-button>
            </span>
        </template>
      </el-dialog>

      <el-card class="answer-component">
        <template #header>
          <span class="card-header">编辑回答</span>
          <div class="selector">
            <el-radio-group v-model="editType" style="float:right">
              <el-radio-button label="富文本"/>
              <el-radio-button label="Markdown"/>
            </el-radio-group>
          </div>
        </template>

        <v-md-editor v-if="(editType === 'Markdown')" v-model="MDContent" height="71vmin"
                     left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | Publish  "
                     :toolbar="toolbar" disabled-menus="[]" @upload-image="handleUploadImage"></v-md-editor>
        <vue3-tinymce v-if="(editType === '富文本')" v-model="RichTextState.content"
                      :setting="RichTextState.setting"/>

        <el-button type="success" plain class="post-button" @click="publishAnswer">提交</el-button>
      </el-card>

    </el-card>
  </main>

</template>


<style scoped>
.answer-component {
  margin-top: 1.5vmin;
}

.card-header {
  display: inline;
  font-size: 4vmin;
  font-weight: bolder
}

.selector {
  float: right;
  /*display: inline-block;*/
}

.post-button {
  margin-top: 0.5%;
  float: right;
  margin-bottom: 0.5%;
  font-weight: bolder;
  font-size: 17px;
}
</style>