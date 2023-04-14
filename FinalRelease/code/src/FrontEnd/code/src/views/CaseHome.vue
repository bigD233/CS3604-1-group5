<script setup lang="ts">

import {onMounted, nextTick, ref, inject} from 'vue';
import {ElInput} from 'element-plus'
import {DocumentAdd, Delete} from '@element-plus/icons-vue';
import Header from '../components/Header.vue';
import PublishCase from '../components/PublishCase.vue';
import {useStore} from 'vuex';
import { ElMessage } from 'element-plus'
import CaseThumbnail from '../components/CaseThumbnail.vue';

const showManageCases = ref(false);
const myInterests = ref([]);
const initMyInterests = ref(['荨麻疹', '上交二附院', '抗组胺']);
const store = useStore();
const inputValue = ref('')
const inputVisible = ref(false)
const InputRef = ref<InstanceType<typeof ElInput>>()

// const getCaseId = ref([20042]);
// const getData = ref([]);

const axios = inject('axios');

// function loadCaseGeneral(Id) {
//   for (let id of Id) {
//     const item = ref();
//     axios.get(`/case/${id}/${store.state.userId}`)
//         .then(
//             resp => {
//               if (resp && resp.status === 200) {
//                 item.value = resp.data;
//               }
//               getData.value.push(item);
//             }
//         )
//   }
//   console.log(getData);
// }


const handleClose = (interest) => {
  myInterests.value.splice(myInterests.value.indexOf(interest), 1)
}

const showInput = () => {
  inputVisible.value = true
  nextTick(() => {
    InputRef.value!.input!.focus()
  })
}

const handleInputConfirm = () => {
  if (inputValue.value) {
    myInterests.value.push(inputValue.value)
  }
  inputVisible.value = false
  inputValue.value = ''
}

function selectColor(index) {
  const colors = ['default', 'success', 'info', 'warning', 'danger'];
  return colors[index % colors.length];
}

function showPublishInfo(){
  if (!store.state.isNormalRole){
    ElMessage.error('您已被禁言，无法发布案例。');
    console.log('666');
  }
  else{
    publishInfo.value.dialogVisible=true;
  }
}


const publishInfo = ref({
  dialogVisible: false,
  open: 1,
  tags: '',
  notes: '',
  name: '',
  userId: Number(store.state.userId),
  publishId: 0,
})

const savedCaseIds = ref();
function loadSavedCases() {
  axios
      .get('/case/saved', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        savedCaseIds.value = resp.data;
        console.log(resp.data);
      })
}


const recommendCaseList=ref([]);

function loadRecommendCase(){
  axios
      .get('/case/recommend', {
        params: {userId: store.state.userId}
      })
      .then(resp => {
        recommendCaseList.value = resp.data;
        console.log(recommendCaseList.value);
      });

  // axios.get('/case/recommend/tags',{
  //   params: {userId: store.state.userId}
  // }).then(
  //   resp=>{
  //       myInterests.value=resp.data;
  //       console.log(myInterests.value);
  //   }
  // )
};

function loadRecommendTags(){
  axios
      .get('/case/recommendtags',{
        params:{userId:store.state.userId}
      })
      .then(
        res=>{
        myInterests.value=res.data;
        console.log(res.data);
    }
  );
};


onMounted(() => {
  // loadCaseGeneral(getCaseId.value);
  loadSavedCases();
  
  loadRecommendTags();

  loadRecommendCase();
})


</script>

<template>
  <Header/>
  <el-container style="margin-top: 5%;">
    <el-col :span="1"></el-col>
    <el-col :span="10">

      <publish-case :case-info="publishInfo"></publish-case>


      <!-- this.$router.push({name:'Casepublish',params:{publishId:0}}) -->

      <el-card class="box-card" style="background-color: white;">
        <template #header>
          <div id="editcase" class="card-header">
            <span style="font-size:20px;font-weight:bolder">我的收藏</span>


            <el-button class="button" round type="success" style="margin-left:3%; float:right" :icon="DocumentAdd"
                       @click="showPublishInfo">发布案例
            </el-button>

          </div>
        </template>

        <el-scrollbar class="scroll-body">
          <CaseThumbnail v-for="caseId in savedCaseIds" :case-id="caseId"></CaseThumbnail>
        </el-scrollbar>
      </el-card>
    </el-col>
    <el-col :span="1"></el-col>
    <el-col :span="11">
      <el-card class="box-card" style="background-color: white;">
        <template #header>
          <div id="editcase" class="card-header">
            <span style="font-size:20px;font-weight:bolder; width: 100px">我的关注</span>
            <el-tag v-for="(interest, index) in myInterests" :type="selectColor(index)" size="large"
                    style="margin-left:2%" effect="dark"
                    closable :disable-transition="false" @close="handleClose(interest)">
              {{ interest }}
            </el-tag>
            <div style="width: 20%;display:inline-block;margin-left: 3%;" v-if="inputVisible">
              <el-input size="large" ref="InputRef" v-model="inputValue" @keyup.enter="handleInputConfirm"
                        @blur="handleInputConfirm"/>
            </div>
            <el-button v-else @click="showInput" style="width: 100px;margin-left: 2%" size="large">+添加新标签
            </el-button>
          </div>
        </template>
        <el-scrollbar class="scroll-body">
          <div class="case-wrapper" v-for="caseId in recommendCaseList">
            <case-thumbnail  :case-id=caseId></case-thumbnail>
          </div>
          
        </el-scrollbar>
      </el-card>
    </el-col>
  </el-container>
</template>


<style scoped>
.text-item {
  height: auto;
  width: 100px;
}

.scroll-body {
  height: 70vmin;
}

.case-box {
  height: auto;
  width: 90%
}

.case-card {
  width: 95%;
  height: auto;
  margin: 20px auto;
  background-color: white;
}

.case-card2 {
  width: 95%;
  height: auto;
  margin-top: 15px;
  background-color: #ffffff;
}

.deletecase {
  position: relative;
  right: -30px;
  float: right;
  top: -10px;

}

.interests-show {
  margin-left: 5px;
  margin-right: 5px;
}

.interests-show2 {
  font-size: 12px;
  margin-right: 5px;
  float: right;
  margin-top: 15px;
  margin-bottom: 10px;
}

.dialog-footer {
  margin-top: -160px;
  float: right;
  margin-right: -10px;
}

input {
  outline-style: none;
  border: 1px solid #ccc;
  border-radius: 3px;
  padding: 13px 14px;
  width: 420px;
  font-size: 14px;
  font-weight: 700;
  font-family: "Microsoft soft";

}

input:focus {
  border-color: #66afe9;
  outline: 0;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6);
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6)
}

.case-wrapper {
  width: 50%;
  display: inline-block;

}


</style>

// this.$router.push({ name: 'Casepublish', params: { publishId: 0, name: props.caseInfo.name, notes: props.caseInfo.notes, open: props.caseInfo.open, tags: props.caseInfo.tags } })