<script setup>
import {useRoute, useRouter} from 'vue-router';
import {onMounted, ref, inject, watch} from 'vue';
import Header from '@/components/Header.vue';
import SearchBox from '@/components/SearchBox.vue';
import QAThumbnail from "../components/QAThumbnail.vue";
import {useStore} from "vuex";


const store = useStore();
const axios = inject('axios');

const answerInfo =ref([]);
const answersId =ref([]);
const answerCount =ref({num:0});
const queCount=ref({num:0,});
const quesId =ref([]);
const currentPage = ref(1);

const route = useRoute()
const router = useRouter();
//获取来自searchBox子组件跳转时的返回参数
const queInput=route.query.queInput;

const authorInfo = {
  userName: "Alice",
  userSignature: "Flesh is weak",
  releaseTime: "2022.10.02",
};

const info = {
  question: "<font color='red'>荨麻疹</font>真的没办法根治吗？",
  digest: "大部分的慢性荨麻疹完全可以治愈的，这里就儿童荨麻疹做下分享。其实荨麻疹的发生，是由于肥大细胞活化，释放组胺引起的。那肥大细胞一活化之后，它就会释放组胺，导致了荨麻疹的发生。但是，组胺在皮肤和在血液里存在的时间是非常短的...",
  author: {
    userName: "Alice",
    userSignature: "Flesh is weak",
    releaseTime: "2022.10.02",
  },
};

const queInfos = ref([]);

function jumpToQuestion() {
  router.push('/question');
}

function queSearch() {
  axios
      .get(`/queSearch/${queInput}`)
      .then(resp => {
        if (resp && resp.status === 200) {
          queInfos.value = resp.data;
          for(queCount.value.num=0;queCount.value.num<queInfos.value.length;queCount.value.num++){
            quesId.value[queCount.value.num]=queInfos.value[queCount.value.num].questionId;
          }
          console.log(quesId.value);

        }
      })
}
function allAnswerByQueID(questionID) {
  axios
      .get('/answer/all', {
        params: {
          questionId: questionID,
          visitorId: store.state.userId
        }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          answerInfo.value=resp.data;
          answerCount.value.num=0;
          // for( answerCount.value.num=0;answerCount.value.num<answerInfo.value.length; answerCount.value.num++){
          //   answersId.value[answerCount.value.num]=answerInfo.value[answerCount.value.num].answerId;
          // }
          // console.log(typeof questionID);
          console.log( answerInfo.value);
        }
        else{
          answerInfo.value=[];
        }
        if(answerInfo.value.length>0){
          return 1;
        }
      })
}

onMounted(() => {
  queSearch();
})

// 这里监听路由参数变化来更新页面
watch(
    () => router.currentRoute.value.query,
    (newVal, oldVal) => {
      if(oldVal && newVal)
      {
        // console.log(newVal);
        // console.log(oldVal);
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

  <el-container style="margin-top:50px">
    <el-col :span="4"></el-col>
    <el-col :span="16">
      <SearchBox />
      <el-row>
        <el-col :span="3"></el-col>

        <el-col :span="18">
          <div  v-for="(i,index1) in quesId" >

            <QAThumbnail :question-id="i"></QAThumbnail>

          </div>
        </el-col>
        <el-col :span="3"></el-col>

      </el-row>

    </el-col>

    <el-col :span="4"></el-col>
  </el-container>

</template>


<style scoped>
.paging {
  text-align: center;
  margin-top: 50px;
  font-size: larger;
}
</style>