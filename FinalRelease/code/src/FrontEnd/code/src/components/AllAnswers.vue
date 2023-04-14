<script setup>
import {useStore} from 'vuex';
import {inject, onMounted, ref} from 'vue';
import OneAnswer from "@/components/OneAnswer.vue";

const store = useStore();
const axios = inject('axios');

const props = defineProps(["questionId", "questionerId"]);

const answers = ref([]);
const showMostHelpful = ref(true);
const ansNum = ref(0);


function loadAnswers() {
  axios
      .get('/answer/all', {
        params: {
          questionId: props.questionId,
          visitorId: store.state.userId
        }
      })
      .then(resp => {
        if (resp && resp.status === 200) {
          answers.value = resp.data;
          ansNum.value = answers.value.length;
          // console.log(answers.value);
        }
      })
}

onMounted(() => {
  loadAnswers();
})
</script>


<template>
  <div id="all-answers">
    <el-card>
      <template #header>
        <div id="manage-all-ans">
          <span id="num-of-ans">共 {{ ansNum }} 个回答</span>
          <el-button-group>
            <el-button type="primary" :plain="!showMostHelpful" @click="showMostHelpful=true">最有帮助</el-button>
            <el-button type="primary" :plain="showMostHelpful" @click="showMostHelpful=false">最近</el-button>
          </el-button-group>
        </div>
      </template>
      <div id="answers-wrapper">
        <OneAnswer @ans-num-dec="ansNum -= 1"
                    v-for="answer in answers" :answerInfo="answer"
                    :questioner-id="questionerId"></OneAnswer>
      </div>
    </el-card>
  </div>
</template>


<style scoped>
#manage-all-ans {
  display: flex;
  justify-content: space-between;
}

#num-of-ans {
  margin: 0.5em;
}

#answers-wrapper {

}
</style>