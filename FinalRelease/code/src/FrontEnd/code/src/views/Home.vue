<script setup>
import {ref, inject,onMounted} from 'vue'
import Menu from '@/components/Header.vue'
import SearchBox from '@/components/SearchBox.vue'
import SideBar from '../components/SideBar.vue'
import QAThumbnail from "../components/QAThumbnail.vue";
import CaseThumbnail from '../components/CaseThumbnail.vue';

const showCase = ref(true)
const axios = inject('axios');


const authorInfo = ref({
  userName: "Alice",
  userSignature: "Flesh is weak",
  releaseTime: "2022.10.02",
});


const caseItem = {
  name: '慢性荨麻疹',
  diagnosis: "1.反复发作的局限性水肿。2.有自限性，一般1-3天可自行缓解消退。3.反复发作的喉水肿或不明原因的腹痛...",
  treatment: '抗组胺和激素药物，在治疗荨麻疹（尤其是重症荨麻疹）的过程中发挥着一定的作用，能够快速控制病情，减少重症风险。 但是...',
  doctor: '上海交通大学第二附属医院 卷怪科主任 朱楷文',
};
//
// const info = ref({
//   question: "荨麻疹真的没办法根治吗？",
//   digest: "大部分的慢性荨麻疹完全可以治愈的，这里就儿童荨麻疹做下分享。其实荨麻疹的发生，是由于肥大细胞活化，释放组胺引起的。那肥大细胞一活化之后，它就会释放组胺，导致了荨麻疹的发生。但是，组胺在皮肤和在血液里存在的时间是非常短的...",
//   author: authorInfo,
// });

const recAns = ref([1, 15, 16, 17]);


const caseIdList=ref([]);
const questionIdList=ref([]);
const pageSize1=ref(3);
const pageNum1=ref(0);
const pageSize2=ref(3);
const pageNum2=ref(0);


const showInfo=ref(false)

const loadInit=()=>{
  axios.get('/case/timeorder',{
        params: {
                pageSize: pageSize1.value,
                pageNum: pageNum1.value
            }
       }).then(
          res=>{
            console.log(res.data)
            caseIdList.value=caseIdList.value.concat(res.data);
            console.log(caseIdList.value);
            pageNum1.value=pageNum1.value+pageSize1.value;
          }
       );

  axios.get('/answer/timeorder',{
  params: {
          pageSize: pageSize2.value,
          pageNum: pageNum2.value
      }
  }).then(
    res=>{
      console.log(res.data)
      questionIdList.value=questionIdList.value.concat(res.data);
      
      pageNum2.value=pageNum2.value+pageSize2.value;
    }
  );
}



const LoadContent=()=>{
  const  scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
  const clientHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
  const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
  if(clientHeight + scrollTop - scrollHeight>0){showInfo.value=true;}
  if(clientHeight + scrollTop >= scrollHeight-5){
    if(showCase.value===true){
      axios.get('/case/timeorder',{
        params: {
                pageSize: pageSize1.value,
                pageNum: pageNum1.value
            }
       }).then(
          res=>{
            // console.log(res.data)
            caseIdList.value=caseIdList.value.concat(res.data);
            // console.log(caseIdList.value);
            pageNum1.value=pageNum1.value+pageSize1.value;
          }
       );     
    }
    else{
      axios.get('/answer/timeorder',{
        params: {
                pageSize: pageSize2.value,
                pageNum: pageNum2.value
            }
       }).then(
          res=>{
            console.log(res.data)
            questionIdList.value=questionIdList.value.concat(res.data);
            console.log(questionIdList.value);
            pageNum2.value=pageNum2.value+pageSize2.value;
          }
       );
    }
       
                    
  }
}

window.addEventListener('scroll', LoadContent);

onMounted(loadInit);


</script>

<template>
  <header>
    <Menu noSearch/>
  </header>

  <main>
    <el-container>

      <el-header>
        <el-row class="search-box">
          <SearchBox boxSize="large"/>
        </el-row>
      </el-header>

      <el-container  class="container">

        <el-aside class="aside">
          <div id="side-bar-outer-container" class="side-bar">
            <SideBar></SideBar>
          </div>
        </el-aside>

        <el-main>
          <div id="main-container">
            <el-card>
              <template #header>
                <el-row align="middle">
                  <h3 style="margin: 0 0 0 0; font-family: 'PingFang SC'">大家关注</h3>
                  <div style="flex-grow: 1"/>
                  <el-button-group>
                    <el-button type="primary" :plain="!showCase" @click="showCase=true">案例</el-button>
                    <el-button type="primary" :plain="showCase" @click="showCase=false">问答</el-button>
                  </el-button-group>
                </el-row>
              </template>
              <div class="scroll-body">
                <div v-show="showCase">
                 <div v-for="caseId in caseIdList " style="margin-top:1%">
                    <case-thumbnail :case-id="caseId"></case-thumbnail>
                </div>
                </div>

                <div v-show="!showCase">
                  <QAThumbnail v-for="answerId in questionIdList" :answer-id="answerId"></QAThumbnail>
                </div>
              </div>
            </el-card>
            <span v-if="showInfo" style="margin-left: 40%;margin-top: 1%;color:#909399">已加载全部~</span>
          </div>
        </el-main>

      </el-container>

    </el-container>
  </main>
</template>

<style scoped>
.el-header {
  height: 150px;
}

#side-bar-outer-container {
  margin-left: 15%;
  padding-top: 20px;
}

#main-container {
  margin-right: 5%;
}

.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}

.search-box {
  margin: 70px auto;
  width: 1000px;
}

.recommend-box {
  margin: 0px auto;
  width: 800px;
}

.scroll-body {
  /*height: 65vmin;*/
}
.container{
  overflow: visible;
}

.aside{
  overflow: visible;
}

.side-bar{
  position: sticky;
  top: -5px;
}
</style>
