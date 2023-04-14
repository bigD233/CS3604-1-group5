<script setup>
import { ref, inject, watch, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { displayError } from '@/utils/messageUtils.js';

const props = defineProps(['userId', 'userSelf']);

const store = useStore();
const router = useRouter();
const axios = inject('axios');

const followees = ref([]);
const total = ref(0);
const pageNum = ref(1);
const pageSize = ref(10);

function getFollowees() {
    if (!props.userId) {
        return;
    }

    displayError(axios
        .get('/user/get/followee', {
            params: {
                follower: props.userId,
                pageSize: pageSize.value,
                pageNum: pageNum.value,
            }
        }))
        .then(respData => {
            followees.value = respData.list;
            total.value = respData.total;
        });
}

function removeFollowee(followee) {
    let data = new FormData();
    data.append('followee', followee);
    data.append('follower', store.state.userId);

    displayError(axios
        .post('/user/remove/following', data))
        .then(respData => {
            getFollowees();
        });
}

watch(
    () => props.userId,
    async id => {
        pageNum.value = 1;
        getFollowees();
    });

watch(
    () => pageNum.value,
    async num => {
        getFollowees();
    });

onMounted(getFollowees);

</script>

<template>
    <el-space fill wrap>
        <el-card v-for="followee of followees">
            <div class="flex-space-between">
                <div class="user-info">
                    <el-avatar :size="60" :src="followee.avatar" />
                    <div>
                        <el-link :underline="false" :href="'#/user/' + followee.id">
                            <span class="name"> {{ followee.username }} </span>
                        </el-link>
                        <div class="description"> {{ followee.sign }} </div>
                    </div>
                </div>
                <el-button v-if="props.userSelf" type="danger" @click="() => removeFollowee(followee.id)">
                    取消关注
                </el-button>
            </div>
        </el-card>
        <el-pagination class="flex-center" layout="prev, pager, next" hide-on-single-page v-model:current-page="pageNum"
            :page-size="pageSize" :total="total"></el-pagination>
    </el-space>
</template>

<style scoped>
.user-info {
    display: flex;
    align-items: center;
}

.name {
    font-size: 16px;
    font-weight: 700;
    margin: 5px;
}

.description {
    font-size: 14px;
    font-weight: 400;
    margin: 5px;
}

.flex-space-between {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.flex-center {
    display: flex;
    justify-content: center;
}
</style>