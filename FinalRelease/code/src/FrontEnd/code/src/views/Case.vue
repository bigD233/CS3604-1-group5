<script setup>

import {onMounted, ref, inject} from 'vue';
import Header from '../components/Header.vue'
import OperatingArticle from '../components/OperatingArticle.vue'
import TheCase from "@/components/TheCase.vue";
import {useRouter} from 'vue-router'
import {useStore} from 'vuex';

const axios = inject('axios');


const store = useStore();
const activeName = ref(0);
const tabPosition = 'right';
const router = useRouter()
const myCaseList = ref({
  id: router.currentRoute.value.params.caseId,
  name: '',
  intro: '',
  history: '',
  diagnosis: '',
  treatment: '',
  prevention: '',
  userId: 0,
  notes: '',
  textType: 0,
  open: '',
  likes: 0,
  watchers: 0,
  liked: false,
  saved: false
})

const typeList = ref({
  type: ["患者病史", "诊断", "药物治疗", "预防"]
})


const caseInfo = ref({
  caseId: router.currentRoute.value.params.caseId,
  questionText: "",
  questionTitle: "",
  userId: 0,
  releaseTime: 'YYYY-MM-DD hh:mm:ss',
  tagsOfQuestion: [],
  open: 0,

})

function handleClick(tab) {
  const sections = document.getElementsByClassName('section');
  const top = sections[tab.index].offsetTop;
  console.log('' + top + tab.index);
  // Chrome
  document.body.scrollTop = top;
  // Firefox
  document.documentElement.scrollTop = top;
  // Safari
  window.pageYOffset = top;
}

function handleScroll() {
  const scroll = document.documentElement.scrollTop || document.body.scrollTop;
  const sections = document.getElementsByClassName('section');
  for (let i = sections.length - 1; i >= 0; i--) {
    if (scroll >= sections[i].offsetTop - 100) {
      activeName.value = i;
      break;
    }
  }
}

function loadCase() {
  axios
      .get(`/case/${myCaseList.value.id}/${store.state.userId}`)
      .then(resp => {
        if (resp && resp.status === 200) {
          myCaseList.value.id = resp.data.caseId;
          myCaseList.value.name = resp.data.name;
          myCaseList.value.intro = resp.data.intro;
          myCaseList.value.history = resp.data.history;
          myCaseList.value.diagnosis = resp.data.diagnosis;
          myCaseList.value.treatment = resp.data.treatment;
          myCaseList.value.prevention = resp.data.prevention;
          myCaseList.value.userId = resp.data.userId;
          myCaseList.value.open = resp.data.open;
          myCaseList.value.likes = resp.data.likes;
          myCaseList.value.watchers = resp.data.watchers;
          myCaseList.value.liked = resp.data.liked;
          myCaseList.value.saved = resp.data.watched;
          console.log(myCaseList.value.liked);

          caseInfo.value.questionTitle = resp.data.name;
          caseInfo.value.userId = resp.data.userId;
          caseInfo.value.questionText = resp.data.notes;

          if (resp.data.tags != null) {
            for (let oldTag of resp.data.tags) {
              caseInfo.value.tagsOfQuestion.push(oldTag.tagName);
            }
          }


          caseInfo.value.releaseTime = resp.data.releaseTime;
          caseInfo.value.open = resp.data.open;
        }
      })

}


onMounted(() => {
  loadCase();
})
window.addEventListener('scroll', handleScroll);

</script>


<template>
  <Header/>
  <the-case :caseInfo="caseInfo"></the-case>
  <div class="container">
    <div v-if="Number(store.state.userId) != myCaseList.userId && myCaseList.open != 1">
      <el-result icon="warning" title="不可查看" sub-title="案例已被发布者关闭，不可查看。">
        <template #extra>
          <el-button type="primary">Back</el-button>
        </template>
      </el-result>
    </div>
    <div class="wrapper" v-else>
      <div class="section" style="width:100%;margin-top: 20px;">
        <el-card class="son-case" style="background-color: #ffffff;">
          <template #header>
            <div id="editcase" class="card-header">
                            <span style="font-size:30px;font-weight:bolder">{{
                                myCaseList.name + '#' + myCaseList.id
                              }}</span>
            </div>
          </template>
          <v-md-editor :model-value="myCaseList.intro" mode="preview"></v-md-editor>
        </el-card>
      </div>
      <div class="section" style="width:100%;margin-top: 20px;">
        <el-card class="son-case" style="background-color: #ffffff;">
          <template #header>
            <div id="editcase" class="card-header">
              <span style="font-size:30px;font-weight:bolder">患者病史</span>
            </div>
          </template>
          <v-md-editor :model-value="myCaseList.history" mode="preview"></v-md-editor>
        </el-card>
      </div>
      <div class="section" style="width:100%;margin-top: 20px;">
        <el-card class="son-case" style="background-color: #ffffff;">
          <template #header>
            <div id="editcase" class="card-header">
              <span style="font-size:30px;font-weight:bolder">诊断</span>
            </div>
          </template>
          <v-md-editor :model-value="myCaseList.diagnosis" mode="preview"></v-md-editor>
        </el-card>
      </div>
      <div class="section" style="width:100%;margin-top: 20px;">
        <el-card class="son-case" style="background-color: #ffffff;">
          <template #header>
            <div id="editcase" class="card-header">
              <span style="font-size:30px;font-weight:bolder">药物治疗</span>
            </div>
          </template>
          <v-md-editor :model-value="myCaseList.treatment" mode="preview"></v-md-editor>
        </el-card>
      </div>
      <div class="section" style="width:100%;margin-top: 20px;">
        <el-card class="son-case" style="background-color: #ffffff;">
          <template #header>
            <div id="editcase" class="card-header">
              <span style="font-size:30px;font-weight:bolder">预防</span>
            </div>
          </template>
          <v-md-editor :model-value="myCaseList.prevention" mode="preview"></v-md-editor>
        </el-card>
      </div>
      <div id="op-wrapper">
        <OperatingArticle :type="'case'"
                          :article-id="myCaseList.id"
                          :saves="myCaseList.watchers"
                          :likes="myCaseList.likes"
                          :liked="myCaseList.liked"
                          :saved="myCaseList.saved"
        ></OperatingArticle>
      </div>
    </div>

    <nav style="position:fixed;right:10vmin;top:50%;">
      <div style="margin-left:18px;font-size:20px">目录</div>
      <el-tabs @tab-click="handleClick" v-model="activeName" :tab-position="tabPosition" style="height: 200px;">
        <el-tab-pane :name="index" :class="index === 0 ? 'current' : ''" :key="index"
                     :label="myCaseList.name + '#' + myCaseList.id"></el-tab-pane>
        <el-tab-pane :name="index" :class="index === 0 ? 'current' : ''" v-for="(item, index) in typeList.type"
                     :key="index" :label="item"></el-tab-pane>
      </el-tabs>
    </nav>
  </div>

</template>


<style scoped>
.container {
  width: 60%;
  margin: auto;
  margin-top: 70px;
  margin-right: 350px;
}

.card-header {
  height: 40px;
}

#op-wrapper {
  padding-bottom: 2em;
}
</style>
