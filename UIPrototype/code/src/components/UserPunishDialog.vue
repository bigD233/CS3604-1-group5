<script setup>

import { ref, inject, watch, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { displayError } from '@/utils/messageUtils.js';

const props = defineProps(['modelValue', 'userId', 'unpunished']);
const emit = defineEmits(['update:modelValue', 'changed']);

const axios = inject('axios');
const username = ref(null);
const days = ref(null);

function loadUserName() {
    if (props.userId == null) {
        return;
    }

    displayError(axios
        .get('/user/get', {
            params: {
                id: props.userId,
            }
        }))
        .then(user => {
            username.value = user.username;
        });
}

function setPunish() {
    if (!days.value) {
        ElMessage.error('请选择禁言时间');
        return;
    }

    let data = new FormData();
    data.append('userId', props.userId);
    data.append('days', days.value);
    displayError(axios
        .post('/user/punishment/set', data))
        .then(data => {
            emit('update:modelValue', false);
            emit('changed');
        });
}

function stopPunish() {
    let data = new FormData();
    data.append('userId', props.userId);
    displayError(axios
        .post('/user/punishment/stop', data))
        .then(data => {
            emit('update:modelValue', false);
            emit('changed');
        });
}

watch(
    () => props.userId,
    id => {
        loadUserName();
    });

onMounted(loadUserName);

</script>

<template>
    <el-dialog :modelValue="props.modelValue" @update:modelValue="emit('update:modelValue', $event)" center
        width="500px">
        <template #header>
            <span v-if="props.unpunished">
                禁言用户： {{ username }}
            </span>
            <span v-else>
                撤销用户禁言： {{ username }}
            </span>
        </template>
        <el-form>
            <el-form-item label="时间" v-if="props.unpunished">
                <el-select v-model="days">
                    <el-option label="1天" value="1"></el-option>
                    <el-option label="7天" value="7"></el-option>
                    <el-option label="30天" value="30"></el-option>
                    <el-option label="90天" value="90"></el-option>
                </el-select>
            </el-form-item>
            <div style="display: flex; justify-content: center; align-items: center;">
                <el-button type="primary" @click="props.unpunished ? setPunish() : stopPunish()"> 确认 </el-button>
                <el-button @click="emit('update:modelValue', false)"> 取消 </el-button>
            </div>
        </el-form>
    </el-dialog>
</template>
