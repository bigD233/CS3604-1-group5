<script lang="ts" setup>
import {useStore} from 'vuex';
import {useRouter} from 'vue-router'
import {inject, nextTick, onMounted, ref} from 'vue';
import {Filter, Edit} from '@element-plus/icons-vue';
import Header from "../components/Header.vue";
import QAThumbnail from "../components/QAThumbnail.vue";
import {ElInput, ElMessage} from "element-plus";

const store = useStore();
const axios = inject('axios');
const router = useRouter()

// const showRec = ref(true);

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

function filterByTags() {
  // if (dynamicTags.value.length > 0) {
  if (dynamicTags.value.length > 0) {
    axios
        .get("/question/getByTags", {
          params: {tagNames: dynamicTags.value.join(";")}
        })
        .then(resp => {
          if (resp && resp.status === 200) {
            // recAns.value = [];
            // for (let tag of resp.data) {
            //   recAns.value.push(tag.questionId);
            // }
            // console.log(recAns);
            recIds.value = [];
            for (let tag of resp.data) {
              let ques = {questionId: tag.questionId, answerId: null};
              recIds.value.push(ques);
            }
          }
        })
        .catch(() => {
          ElMessage.error('请求数据失败，请稍后重试或联系维护人员。')
        })
  } else {
    recIds.value = [];
    getRecQA();
  }
}

const recIds = ref([]);

// 获取推荐问答的 Id
function getRecQA() {
  axios
      .get('/answer/recommend', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        console.log(resp.data);
        for (let answer_id of resp.data) {
          recIds.value.push({answerId: answer_id, questionId: null});
        }
        axios
            .get('/question/recommend', {
              params: {userId: store.state.userId}
            })
            .then(resp => {
              console.log(resp.data);
              // for (let question_id of resp.data) {
              //   recIds.value.push({answerId: null, questionId: question_id});
              // }
              // 只返回一个问题
              let recQuestionId = resp.data[0];
              // 随机插入
              let idx = Math.ceil(Math.random() * 10) - 1;
              console.log(idx);
              let recQuestion = {answerId: null, questionId: recQuestionId}
              recIds.value.splice(idx, 0, recQuestion);
            })
      })
}

onMounted(() => {
  getRecQA();
});

const LoadContent = () => {
  const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
  const clientHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
  const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
  // if (clientHeight + scrollTop - scrollHeight > 0) {
    // showInfo.value = true;
  // }
  if (clientHeight + scrollTop >= scrollHeight - 1) {
    getRecQA();
  }
}
window.addEventListener('scroll', LoadContent);

</script>


<template>
  <Header/>
  <el-container class="container">
    <el-aside class="aside">
      <div id="side-bar-outer-container" class="side-bar">
        <SideBar></SideBar>
      </div>
    </el-aside>

    <el-main>
      <div id="q-a-rec-wrapper">
        <el-card>
          <template #header>
            <el-row align="middle">
              <el-col :span="20">
                <div id="filter-input">
                  <el-tag
                      v-for="tag in dynamicTags"
                      class="question-tag"
                      :key="tag"
                      closable
                      :disable-transitions="false"
                      @close="handleClose(tag)"
                      round
                      size="large">
                    {{ tag }}
                  </el-tag>
                  <div v-if="inputVisible" style="display:inline-block">
                    <el-input
                        ref="InputRef"
                        v-model="inputValue"
                        placeholder="输入标签后按回车键以添加"
                        size="default"
                        @keyup.enter="handleInputConfirm"
                        @blur="handleInputConfirm"/>
                  </div>
                  <el-button v-else @click="showInput">
                    + 添加标签
                  </el-button>
                  <el-button @click="filterByTags" :icon="Filter">筛选</el-button>
                </div>
              </el-col>
              <el-col :span="4">
                <div id="pub-question">
                  <el-button @click="this.$router.push('question/edit/0/0')"
                             type="primary" :icon="Edit"
                             :disabled="!store.state.isNormalRole">发起提问
                  </el-button>
                </div>
              </el-col>
              <!--              <el-col :span="4">-->
              <!--                <div id="display-mode">-->
              <!--                  <el-button-group>-->
              <!--                    <el-button type="primary" :plain="!showRec" @click="showRec=true">推荐</el-button>-->
              <!--                    <el-button type="primary" :plain="showRec" @click="showRec=false">最近</el-button>-->
              <!--                  </el-button-group>-->
              <!--                </div>-->
              <!--              </el-col>-->
            </el-row>
          </template>

          <div id="all-qa-wrapper">
            <div v-for="recId in recIds">
              <QAThumbnail v-bind="recId"></QAThumbnail>
            </div>
          </div>
        </el-card>
      </div>
    </el-main>
  </el-container>
</template>


<style scoped>
#q-a-rec-wrapper {
  margin-right: 5%
}

#filter-input {
  /*width: 300px;*/
}

#filter-qa > button {
  display: inline-block;
  height: 6vmin;
  margin-left: 1vmin;
  border-radius: 0.5em;
}

#side-bar-outer-container {
  margin-left: 15%;
  padding-top: 20px;
}

.question-tag {
  margin: 0.5vmin;
}

#pub-question {
  float: right;
}

#display-mode {
  float: right;
}

.container {
  overflow: visible;
}

.aside {
  overflow: visible;
}

.side-bar {
  position: sticky;
  top: -5px;
}


</style>