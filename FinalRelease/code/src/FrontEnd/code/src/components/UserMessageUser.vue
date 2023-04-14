<script setup>
import { ref, inject, watch, onMounted } from 'vue';
import UserMessageDialog from '@/components/UserMessageDialog.vue';
import { displayError } from '@/utils/messageUtils.js';

const props = defineProps(['userId']);

const axios = inject('axios');

const total = ref(0);
const pageNum = ref(1);
const pageSize = ref(10);

const summaries = ref(null);
const users = ref(null);

const showDialog = ref(false);
const dialogUserId = ref(null);

function loadSummaries() {
    if (!props.userId) {
        return;
    }

    summaries.value = null;
    users.value = null;

    displayError(axios
        .get('/message/summary', {
            params: {
                id: props.userId,
                pageSize: pageSize.value,
                pageNum: pageNum.value,
            }
        }))
        .then(respData => {
            summaries.value = respData.content;
            total.value = respData.totalElements;

            users.value = new Array(summaries.value.length);
            loadUsers();
        });
}

function loadUsers() {
    for (let i = 0; i < summaries.value.length; i++) {
        displayError(axios
            .get('/user/get', {
                params: {
                    id: summaries.value[i].from,
                }
            }))
            .then(respData => {
                users.value[i] = respData;
            });
    }
}

function sendMessage(toId) {
    dialogUserId.value = toId;
    showDialog.value = true;
}

onMounted(loadSummaries);

watch(
    () => props.userId,
    id => {
        loadSummaries();
    });

</script>

<template>
    <el-space fill wrap>
        <div v-for="i in summaries?.length">
            <el-card v-if="users[i - 1]" @click="sendMessage(users[i - 1].id)">
                <div class="user-info">
                    <el-avatar :size="60" :src="users[i - 1].avatar" />
                    <div>
                        <el-link :underline="false" :href="'#/user/' + users[i - 1].id">
                            <div class="name"> {{ users[i - 1].username }} </div>
                        </el-link>
                        <div class="time">
                            <span>
                                {{ summaries[i - 1].updateTime }}
                            </span>
                            <span v-if="summaries[i - 1].unread" style="margin-left: 10px; color: red;">
                                {{ summaries[i - 1].unread }} 条未读消息
                            </span>
                        </div>
                    </div>
                </div>
                <div class="message">
                    {{ summaries[i - 1].message }} ...
                </div>
            </el-card>
        </div>
        <el-pagination class="flex-center" layout="prev, pager, next" hide-on-single-page v-model:current-page="pageNum"
            :page-size="pageSize" :total="total"></el-pagination>
        <UserMessageDialog v-model="showDialog" :otherId="dialogUserId" @close="loadSummaries()"></UserMessageDialog>
    </el-space>

</template>

<style scpoed>
.user-info {
    display: flex;
    align-items: center;
}

.name {
    font-size: 20px;
    font-weight: 800;
    margin: 5px;
}

.time {
    font-size: 12px;
    margin: 5px;
    color: #909399;
}

.message {
    font-size: 16px;
    margin-left: 65px;
    margin-top: 10px;
}

.flex-center {
    display: flex;
    justify-content: center;
}
</style>
