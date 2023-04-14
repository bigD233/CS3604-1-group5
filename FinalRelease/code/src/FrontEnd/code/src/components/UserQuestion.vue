<script setup>
import { ref, inject, onMounted, watch } from 'vue';
import QAThumbnail from './QAThumbnail.vue';

const props = defineProps(['userId']);

const axios = inject('axios');

const questions = ref(null);

function loadQuestions() {
    axios
        .get('/question/user', {
            params: {
                questionerId: props.userId,
            }
        })
        .then(resp => {
            questions.value = resp.data;
            console.log(questions.value);
        });
}

onMounted(loadQuestions);

watch(
    () => props.userId,
    id => {
        questions.value = null;
        loadQuestions();
    });

</script>

<template>
    <el-space fill wrap>
        <div v-for="question of questions">
            <QAThumbnail :questionId="question.questionId"></QAThumbnail>
        </div>
    </el-space>
</template>
