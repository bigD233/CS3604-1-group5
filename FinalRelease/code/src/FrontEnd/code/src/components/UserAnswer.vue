<script setup>
import { ref, inject, onMounted, watch } from 'vue';
import QAThumbnail from './QAThumbnail.vue';

const props = defineProps(['userId']);

const axios = inject('axios');

const answers = ref(null);

function loadAnswers() {
    axios
        .get('/answer/user', {
            params: {
                answererId: props.userId,
            }
        })
        .then(resp => {
            answers.value = resp.data;
        });
}

onMounted(loadAnswers);

watch(
    () => props.userId,
    id => {
        answers.value = null;
        loadAnswers();
    });
</script>

<template>
    <el-space fill wrap>
        <div v-for="answer of answers">
            <QAThumbnail :answerId="answer.answerId"></QAThumbnail>
        </div>
    </el-space>
</template>
