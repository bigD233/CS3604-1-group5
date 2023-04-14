<script lang="ts" setup>
import { useStore } from 'vuex';
import { onMounted, ref, inject, nextTick, reactive } from 'vue';
import Vue3Tinymce from '@jsdawn/vue3-tinymce';
import {ElMessage, ElInput} from 'element-plus'
import {useRouter} from 'vue-router'
import {formatTime} from "../utils/timeUtils.js"
import Header from '../components/Header.vue';

// handle tags begin
const inputValue = ref('')
const dynamicTags = ref([])
const inputVisible = ref(false)
const InputRef = ref<InstanceType<typeof ElInput>>()

const handleClose = (tag: string) => {
  dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1)
}

const showInput = () => {
  inputVisible.value = true
  nextTick(() => {
    InputRef.value!.input!.focus()
  })
}

const handleInputConfirm = () => {
  if (inputValue.value) {
    dynamicTags.value.push(inputValue.value)
  }
  inputVisible.value = false
  inputValue.value = ''
}
// handle tags end

const store = useStore();
const axios = inject('axios');
const editType = ref('富文本');

const questionId = ref(0);
const caption = ref("");
const content = ref("");

const resSubtitle = ref("");

const MDContent = ref("");


const RichTextState = reactive({
  content: "",
  // editor 配置项
  setting: {
    height: '71vmin', // editor 高度
    language: 'zh-Hans',
    language_url: '/tinymce/langs/zh-Hans.js',

    toolbar:
      'undo redo | fullscreen | blocks alignleft aligncenter alignright alignjustify | link unlink | numlist bullist | image media table | fontsize forecolor backcolor | bold italic underline strikethrough | indent outdent | superscript subscript | removeformat |',
    toolbar_mode: 'sliding image',
    quickbars_selection_toolbar:
      'removeformat | bold italic underline strikethrough | fontsize forecolor backcolor',
    plugins: 'link image media table lists fullscreen quickbars',
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
            }
            else {
              reject(new Error('图片含有不当内容'));
            }
        })
        .catch(error => {
          reject(error);
        });
    }),


    file_picker_types: "media",
    file_picker_callback: (callback, value, meta) => {
      let formData=new FormData();
      if (meta.filetype == 'media') {
        var input = document.createElement("input");
        input.setAttribute("type", "file");
        input.setAttribute("accept", "video/mp4,video/AVI,video/mov,video/FLV,video/rmvb,video/mtv,video/mpeg");
        input.onchange = function (e) {
          let file = this.files[0];
          console.log(this.files[0]);
          console.log(file);
          let ext = file.name.split(".")[1];
          // let url = CONSTANT.URL.BASESYS.ADMINUPLOADFILE;
          let param = {
            suffix: ext,   // 文件后缀  png
            contentType: file.type       // 文件类型  image/png
          }
          // console.log('shipin',param)
          //上传接口
          formData.append('file', file);//添加form表单中其他数据

          axios.post('/media', formData).then(res => {
            console.log(res);
            callback(res.data, { title: '名字' });
          }).catch(err => {
            ElMessage.error('提交失败，请稍后重试或联系维护人员。');
            console.log(err);
          })
        };
        input.click();
      }
    },

  }});

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
        }
        else {
          ElMessage.error('图片含有不当内容')
        }
      },
        error => {

        }
      )
  }
}

const route = useRouter()

function show() {
  console.log(RichTextState.content);
}

const dialogVisible = ref(false)

function jumpToHome() {
  route.push('/home')
}

function publishQuestion() {
  if (caption.value === "") {
    ElMessage({
      message: '标题不能为空',
      type: 'error',
    });
    return;
  }

  content.value = (editType.value === "富文本" ? RichTextState.content : MDContent.value);

  if (route.currentRoute.value.params.questionId === "0") {
    // 发布问题
    axios
      .post("/question/add", {
        caption: caption.value,
        content: content.value,
        questionerId: store.state.userId,
        releaseTime: formatTime(),
        markdown: editType.value !== "富文本"
      }, {
        params: { tags: dynamicTags.value.join(";") }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          questionId.value = resp.data.questionId;
          dialogVisible.value = true;
        }
      })
      .catch(() => {
        ElMessage.error('提交失败，请稍后重试或联系维护人员。')
      })
  } else {
    // 修改问题
    axios
      .post("/question/edit", {
        questionId: route.currentRoute.value.params.questionId,
        caption: caption.value,
        content: content.value,
        editTime: formatTime(),
        markdown: editType.value === "Markdown"
      }, {
        params: { tags: dynamicTags.value.join(";") }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          questionId.value = Number(route.currentRoute.value.params.questionId);
          dialogVisible.value = true;
        }
      })
      .catch(() => {
        ElMessage.error('提交失败，请稍后重试或联系维护人员。')
      })
  }
}

function loadQuestion() {
  axios
    .get(`/question/${route.currentRoute.value.params.questionId}/${store.state.userId}`)
    .then(resp => {
      if (resp && resp.status === 200) {
        caption.value = resp.data.caption;
        for (let oldTag of resp.data.tags) {
          dynamicTags.value.push(oldTag.tagName);
        }
        if (route.currentRoute.value.params.markdown === "false")
          RichTextState.content = resp.data.content;
        else
          MDContent.value = resp.data.content;
      }
    })
    .catch(() => {
      ElMessage({
        message: '请求数据失败，请稍后重试或联系维护人员。',
        type: 'error',
      });
    })
}

//czz 添加的 联想标签

const relatedTags = ref([])


const querySearch = (queryString, cb) => {
  if (queryString) {
    relatedTags.value = [];
  }
  axios
    .get(`/tagSearch/${queryString}`)
    .then(
      resp => {
        if (resp && resp.status === 200) {


          for (let item of resp.data) {
            relatedTags.value.push({ value: item.tagName });
            console.log(item);
          }
          console.log(relatedTags.value);

        }
      }
    )
  cb(relatedTags.value);
}
const handleSelect = (item) => {
  console.log(item)
}






onMounted(() => {
  // 若为新建问题则 route.currentRoute.value.params.questionId 为 0,
  // 若为修改问题则 route.currentRoute.value.params.questionId 为该问题的 id
  if (route.currentRoute.value.params.questionId !== "0") {
    loadQuestion();
    // route.currentRoute.value.params.markdown 代表文本类型是否为 Markdown
    editType.value = (route.currentRoute.value.params.markdown === "true" ? "Markdown" : "富文本");
  }
  else
    resSubtitle.value = "您可以在 24 小时内修改问题";
})
</script>


<template>
  <header>
    <Header />
  </header>

  <main>
    <el-card>

      <el-dialog v-model="dialogVisible" title="" width="30%" :show-close="false">
        <el-result icon="success" title="提交成功" :sub-title=resSubtitle></el-result>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="jumpToHome">返回首页</el-button>
            <el-button type="primary"
                       @click="this.$router.push(`/question/${questionId}/0/${store.state.userId}`)">
              查看问题
            </el-button>
          </span>
        </template>
      </el-dialog>

      <el-card class="question-component">
        <template #header>
          <span class="card-header">问题标题</span>
        </template>

        <el-input v-model="caption" :placeholder="'请输入问题标题'" autosize type="textarea" />
      </el-card>

      <el-card class="question-component">
        <template #header>
          <span class="card-header">问题标签</span>
        </template>

        <el-tag v-for="tag in dynamicTags" class="question-tag" :key="tag" closable :disable-transitions="false"
          @close="handleClose(tag)" round size="large">
          {{ tag }}
        </el-tag>
        <el-autocomplete v-if="inputVisible" ref="InputRef" v-model="inputValue" placeholder="输入标签后按回车键以添加"
          size="default" @keyup.enter="handleInputConfirm" @blur="handleInputConfirm" :trigger-on-focus="false"
          :fetch-suggestions="querySearch" @select="handleSelect" />
        <el-button v-else @click="showInput">
          + 添加标签
        </el-button>

      </el-card>

      <el-card class="question-component">
        <template #header>
          <span class="card-header">问题内容</span>
          <div class="selector">
            <el-radio-group v-model="editType" style="float:right">
              <el-radio-button label="富文本" />
              <el-radio-button label="Markdown" />
            </el-radio-group>
          </div>
        </template>

        <v-md-editor v-if="(editType === 'Markdown')" v-model="MDContent" height="71vmin"
          left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | Publish  "
          :toolbar="toolbar" disabled-menus="[]" @upload-image="handleUploadImage"></v-md-editor>
        <vue3-tinymce v-if="(editType === '富文本')" v-model="RichTextState.content" :setting="RichTextState.setting" />

        <el-button type="success" plain class="post-button" @click="publishQuestion">提交</el-button>
      </el-card>

    </el-card>
  </main>

</template>


<style scoped>
.question-component {
  margin-top: 1.5vmin;
}

.question-tag {
  margin: 1vmin;
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