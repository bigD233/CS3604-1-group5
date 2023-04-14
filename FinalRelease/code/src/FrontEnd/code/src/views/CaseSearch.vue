<script setup>

import {onActivated,onMounted, ref, inject, watch} from 'vue';
import { useRouter, useRoute } from 'vue-router'
import Header from '@/components/Header.vue';
import SearchBox from '@/components/SearchBox.vue';
import CaseThumbnail from '../components/CaseThumbnail.vue';


const axios = inject('axios');

const currentPage = ref(1);
const x =ref (0);
const y =ref (0);
const len=ref({num:0,});
const row=ref({num:0,});

const route = useRoute()
const router = useRouter()
//获取来自searchBox子组件跳转时的返回参数
const caseInput=route.query.caseInput;
const semanticInput=route.query.semanticInput;
const kind=route.query.kind;

const myInterests = ref(['荨麻疹', '上交二附院', '抗组胺']);

const item = ref({
  name: '无',
  diagnosis: "无",
  treatment: '无',
  doctor: '无',
});
const items = ref([]);

function jumpToCase() {
  router.push('/case')
}

function selectColor(index) {
  const colors = ['#f89898', '#eebe77', '#95d475'];
  return colors[index % colors.length];
}


function caseSearch() {
  if(kind < 4){
    axios
        .get(`/caseSearch/${caseInput}`)
        .then(resp => {
          if (resp && resp.status === 200) {
            items.value = resp.data;
            len.value.num=items.value.length;

            if(len.value.num==0){
              items.value=null;
            }
            if(len.value.num%2==0) {
              row.value.num=len.value.num/2;
            }
            else{
              row.value.num=(len.value.num-len.value.num%2)/2 + 1 ;
            }
            console.log(items.value);
            console.log(row.value.num);
          }
        })
  }
  else{
    console.log(semanticInput)
    axios
        .get(`/semanticSearch/${semanticInput}`)
        .then(resp => {
          if (resp && resp.status === 200) {
            items.value = resp.data;
            len.value.num=items.value.length;

            if(len.value.num==0){
              items.value=null;
            }
            if(len.value.num%2==0) {
              row.value.num=len.value.num/2;
            }
            else{
              row.value.num=(len.value.num-len.value.num%2)/2 + 1 ;
            }
            console.log(items.value);
            console.log(row.value.num);
          }
        })
  }
}

function splitInterest(num) {
  // myInterests.value=items.value[0].tags.split(" ");
  // console.log();
  return items.value[num].tags.split(" ");
}


onMounted(() => {
  caseSearch();
})

// 这里监听路由参数变化来更新页面
watch(
    () => router.currentRoute.value.query,

    (newVal, oldVal) => {

      if(oldVal && newVal)
      {
        window.location.reload();
      }
    },
    {
      immediate: true,
    }
)

</script>


<template>
  <Header noSearch />

  <el-row style="margin-top:50px">
    <el-col :span="4"></el-col>
    <el-col :span="16">
      <SearchBox  />
      <div  class="result-box" >
        <el-row v-for=" x in row.num" style="margin-top:15px">
          <el-col v-for="y in 2" :span="12">
            <el-card  v-if="2*(x-1)+(y-1)+8*(currentPage-1)<len.num" class="case-card2">
              <div class="case-box">
                <case-thumbnail :case-id=items[2*(x-1)+(y-1)+8*(currentPage-1)].caseId></case-thumbnail>
<!--                <span style="font-weight:bolder">疾病名称：</span>-->
<!--                <v-md-editor :model-value="items[4*(x-1)+(y-1)].name" mode="preview" class="a-text"></v-md-editor>-->
<!--                <p><span style="font-weight:bolder">诊断：</span><v-md-editor :model-value="items[4*(x-1)+(y-1)].diagnosis" mode="preview"></v-md-editor></p>-->
<!--                <p><span style="font-weight:bolder">治疗方法：</span><v-md-editor :model-value="items[4*(x-1)+(y-1)].treatment" mode="preview"></v-md-editor></p>-->
<!--                <p><span style="font-weight:bolder">主治医生：</span><v-md-editor :model-value="items[4*(x-1)+(y-1)].doctor" mode="preview"></v-md-editor></p>-->
<!--                <span class="interests-show2" v-for="(interest, index) in splitInterest(4*(x-1)+(y-1)+8*(currentPage-1))"-->
<!--                      :style="{ boxShadow: 'var(&#45;&#45;el-box-shadow-light)', backgroundColor: `${selectColor(index)}` }">-->
<!--                                    {{ interest }}</span>-->
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
<!--      4*(x-1)+(y-1)+8*(currentPage-1)-->
<!--      <el-pagination background :page-size="20" :pager-count="21" layout="prev, pager, next" :total="1000"-->
<!--                     v-model:currentPage="currentPage" />-->
    </el-col>
    <el-col :span="4"></el-col>
  </el-row>

</template>


<style scoped>
.case-card2 {
  margin-left: 25px;
  width: 85%;
  height: auto;
  margin-top: 40px;
  background-color: #ffffff;
}

.interests-show2 {
  font-size: 12px;
  margin-right: 5px;
  float: right;
  margin-top: 15px;
  margin-bottom: 10px;
}

.case-box {
  height: auto;
  width: 90%;
  overflow-y: hidden;
}

.a-text {
  max-height: 20vmin;
}

.paging {
  text-align: center;
  margin-top: 50px;
  font-size: larger;
}
</style>
