<script setup>
import {inject, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router'
import {useStore} from 'vuex';
import Header from "@/components/Header.vue";
import TheQuestion from "@/components/TheQuestion.vue";
import AllAnswers from "@/components/AllAnswers.vue";
import OneAnswer from "@/components/OneAnswer.vue";

const store = useStore();
const route = useRouter();
const axios = inject('axios');
const answerInfo = ref();
const questionerId = ref();

function setQuestionerId(qid) {
  questionerId.value = qid;
  // console.log(questionerId.value);
}

onMounted(() => {
  if (route.currentRoute.value.params.answerId !== '0') {
    // 只显示单个问题
    axios
        .get('/answer/one', {
          params: {
            answerId: route.currentRoute.value.params.answerId,
            visitorId: store.state.userId
          }
        })
        .then(resp => {
          if (resp && resp.status === 200) {
            answerInfo.value = resp.data;
            // console.log(answerInfo.value);
          }
        })
  }
})
</script>


<template>
  <Header/>
  <div id="the-question-wrapper">
    <TheQuestion :question-id="this.$route.params.questionId"
                 @get-questioner-id="setQuestionerId"></TheQuestion>
  </div>
  <el-container class="container">
    <el-aside class="aside">
      <div id="side-bar-outer-container" class="side-bar">
        <SideBar></SideBar>
      </div>
    </el-aside>
    <el-main id="all-answers">
      <AllAnswers v-if="$route.params.answerId==='0'" :question-id="$route.params.questionId"
                   :questioner-id="questionerId"></AllAnswers>
      <div v-else>
        <OneAnswer :answer-info="answerInfo" :questioner-id="questionerId"></OneAnswer>
        <el-card>
          <div id="show-all-ans" @click=
              "this.$router.push(`/question/${$route.params.questionId}/0/${store.state.userId}`)"
          >显示所有回答
          </div>
        </el-card>
      </div>
    </el-main>
  </el-container>
</template>

<style scoped>
#all-answers {
  margin-right: 5%;
}

#side-bar-outer-container {
  margin-left: 15%;
  padding-top: 20px;
}

#show-all-ans {
  text-align: center;
  color: gray;
}

#show-all-ans:hover {
  cursor: pointer;
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
