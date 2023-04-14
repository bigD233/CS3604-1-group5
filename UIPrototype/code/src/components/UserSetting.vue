<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';
import { Lock } from '@element-plus/icons-vue';
import { saveToken, removeToken } from '@/utils/tokenUtils.js';
import { SHA256Password } from '@/utils/encrypto.js';
import { displayError } from '@/utils/messageUtils.js';

const props = defineProps(['user']);
const emit = defineEmits(['userChanged']);

const router = useRouter();
const store = useStore();
const axios = inject('axios');

const changeSign = ref(false);
const signInput = ref('');
const changeAvatar = ref(false);
const changePassword = ref(false);
const oldPasswordInput = ref('');
const newPasswordInput = ref('');

const uploadHeader = ref({ 'Authorization': store.state.token });

function updateSign() {
    let data = new FormData();
    data.append('id', props.user.id);
    data.append('sign', signInput.value);

    displayError(axios
        .post('/user/update/setting', data))
        .then(respData => {
            emit('userChanged');
            changeSign.value = false;
        });
}

function setChangeSign() {
    if (!changeSign.value) {
        signInput.value = props.user.sign;
    }

    changeSign.value = !changeSign.value;
}

function checkImage(rawFile) {
    if (rawFile.type !== 'image/jpeg' && rawFile.type != 'image/png') {
        ElMessage.error('图片格式错误');
        return false;
    }

    if (rawFile.size > 512 * 1024) {
        ElMessage.error('图片超过大小限制');
        return false;
    }

    return true;
}

function handleAvatarSuccess(resp, file) {
    if (!resp) {
        ElMessage.error("图片含有不当内容");
        return;
    }

    let data = new FormData();
    data.append('id', props.user.id);
    data.append('avatar', resp);

    displayError(axios
        .post('/user/update/setting', data))
        .then(respData => {
            emit('userChanged');
            changeAvatar.value = false;
        });
}

function updatePassword() {
    let data = new FormData();
    data.append('id', props.user.id)
    data.append('oldPassword', SHA256Password(oldPasswordInput.value));
    data.append('newPassword', SHA256Password(newPasswordInput.value));

    displayError(axios
        .post('/user/update/password', data))
        .then(respData => {
            oldPasswordInput.value = '';
            newPasswordInput.value = '';
            changePassword.value = false;
            saveToken(store, axios, respData.userId, respData.token);
            ElMessage.success('修改成功');
        });
}

function resetChangePassword() {
    changePassword.value = false;
    oldPasswordInput.value = '';
    newPasswordInput.value = '';
}

function logout() {
    removeToken(store, axios);
    router.push('/start');
    ElMessage.success('已退出');
}

</script>

<template>
    <el-space fill wrap style="width:100%">
        <el-card>
            <template #header>
                <div class="card-header">
                    <span>
                        用户名
                    </span>
                </div>
            </template>
            <span>
                {{ user.username }}
            </span>
        </el-card>
        <el-card>
            <template #header>
                <div class="card-header">
                    <span>
                        头像
                    </span>
                    <el-button @click="(changeAvatar = !changeAvatar)">
                        <el-icon>
                            <Edit />
                        </el-icon>
                    </el-button>
                </div>
            </template>
            <div>
                <el-avatar v-if="!changeAvatar" :size="60" :src="user.avatar"></el-avatar>
                <div v-else>
                    <el-upload drag action="http://localhost:8443/api/image" :headers="uploadHeader"
                        :before-upload="checkImage" :on-success="handleAvatarSuccess">
                        <el-icon :size="40">
                            <Plus />
                        </el-icon>
                    </el-upload>
                    <div style="font-size: 12px">
                        图片格式为 jpg 或 png，大小不超过 500kB
                    </div>
                </div>
            </div>
        </el-card>
        <el-card>
            <template #header>
                <div class="card-header">
                    <span>
                        简介
                    </span>
                    <el-button @click="setChangeSign">
                        <el-icon>
                            <Edit />
                        </el-icon>
                    </el-button>
                </div>
            </template>
            <div v-if="!changeSign">
                {{ user.sign }}
            </div>
            <div v-else>
                <el-input v-model="signInput"></el-input>
                <el-button @click="updateSign"> 确认 </el-button>
            </div>
        </el-card>
        <el-card>
            <template #header>
                <div class="card-header">
                    <span>
                        密码
                    </span>
                </div>
            </template>
            <el-button type="danger" @click="(changePassword = true)">修改密码</el-button>
        </el-card>
        <el-card>
            <el-button type="warning" @click="logout">退出登录</el-button>
        </el-card>
    </el-space>

    <el-dialog v-model="changePassword">
        <el-form>
            <el-form-item label="旧密码">
                <el-input v-model="oldPasswordInput" placeholder="请输入旧密码" type="password" show-password
                    :prefix-icon="Lock" />
            </el-form-item>
            <el-form-item label="新密码">
                <el-input v-model="newPasswordInput" placeholder="请输入新密码" type="password" show-password
                    :prefix-icon="Lock" />
            </el-form-item>
            <el-form-item>
                <el-button @click="updatePassword" type="primary">确认</el-button>
                <el-button @click="resetChangePassword">取消</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<style scoped>
.setting-list {
    list-style-type: none;
    padding: 0;
    margin: 0px;
}

.setting-item {
    padding: 5px;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>
