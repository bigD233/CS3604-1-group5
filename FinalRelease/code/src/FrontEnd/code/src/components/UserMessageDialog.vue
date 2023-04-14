<script setup>

import { ref, inject, watch, onMounted } from 'vue';
import { displayError } from '@/utils/messageUtils.js';
import { useStore } from 'vuex';

const props = defineProps(['modelValue', 'otherId']);
const emit = defineEmits(['update:modelValue', 'close']);

const store = useStore();
const axios = inject('axios');
const user = ref(null);
const otherUser = ref(null);

const pageSize = ref(5);
const pageNum = ref(1);
const last = ref(true);
const messages = ref([]);

const messageInput = ref('');

function loadUsers() {
    displayError(axios
        .get('/user/get', {
            params: {
                id: store.state.userId,
            }
        }))
        .then(respData => {
            user.value = respData;
        });

    displayError(axios
        .get('/user/get', {
            params: {
                id: props.otherId,
            }
        }))
        .then(respData => {
            otherUser.value = respData;
        });
}

function loadMoreMessage() {
    displayError(axios
        .get('/message/get', {
            params: {
                id1: props.otherId,
                id2: store.state.userId,
                pageSize: pageSize.value,
                pageNum: pageNum.value,
            }
        }))
        .then(respData => {
            pageNum.value = pageNum.value + 1;
            last.value = respData.last;
            messages.value = respData.content.reverse().concat(messages.value);
        });
}

function messageRead() {
    let data = new FormData();
    data.append('from', props.otherId);
    data.append('to', store.state.userId);

    displayError(axios
        .post('/message/read', data));
}

function messageNew() {
    if (!messageInput.value) {
        return;
    }

    let data = new FormData();
    data.append('from', store.state.userId);
    data.append('to', props.otherId);
    data.append('to', props.otherId);
    data.append('message', messageInput.value);

    displayError(axios
        .post('/message/add', data))
        .then(respData => {
            refreshMessage();
        });

    messageInput.value = '';
}

function openDialog() {
    pageNum.value = 1;
    last.value = true;
    user.value = null;
    otherUser.value = null;
    messages.value = [];

    messageRead();
    loadUsers();
    loadMoreMessage();
}

function refreshMessage() {
    pageNum.value = 1;
    displayError(axios
        .get('/message/get', {
            params: {
                id1: props.otherId,
                id2: store.state.userId,
                pageSize: pageSize.value,
                pageNum: pageNum.value,
            }
        }))
        .then(respData => {
            pageNum.value = pageNum.value + 1;
            last.value = respData.last;
            messages.value = respData.content.reverse();
        });
}

</script>

<template>
    <el-dialog :modelValue="props.modelValue" @update:modelValue="emit('update:modelValue', $event)"
        @open="openDialog()" @close="emit('close')" center>
        <template #header>
            <span class="message-username">
                {{ otherUser?.username }}
            </span>
            <el-button style="margin-left: 10px" @click="refreshMessage()">
                <el-icon>
                    <Refresh />
                </el-icon>
            </el-button>
        </template>
        <template #footer>
            <el-input v-model="messageInput">
                <template #append>
                    <el-button @click="messageNew()">
                        发送
                    </el-button>
                </template>
            </el-input>
        </template>
        <el-scrollbar height="300px">
            <el-space fill wrap style="width: 100%">
                <el-link v-if="!last" :underline="false" @click="loadMoreMessage()"> 更多消息 </el-link>
                <div v-else style="display: flex; justify-content: center;"> 已显示所有消息 </div>
                <div v-for="message of messages">
                    <div class="message-username">
                        <span v-if="message.from == props.otherId" class="blue">
                            {{ otherUser?.username }}
                        </span>
                        <span v-else class="red">
                            {{ user?.username }}
                        </span>
                    </div>
                    <div class="message-time">
                        {{ message.createTime }}
                    </div>

                    <div class="message-content">
                        {{ message.message }}
                    </div>
                </div>
            </el-space>
        </el-scrollbar>
    </el-dialog>
</template>

<style scoped>
.message-username {
    font-size: 18px;
    font-weight: 800;
}

.blue {
    color: deepskyblue;
}

.red {
    color: deeppink;
}

.message-time {
    font-size: 12px;
    color: #909399;
}

.message-content {
    font-size: 16px;
}
</style>
