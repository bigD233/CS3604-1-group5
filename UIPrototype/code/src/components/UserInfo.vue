<script setup>
import { useStore } from "vuex";
import { ref, inject, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { displayError } from '@/utils/messageUtils.js';
import CaseThumbnail from '../components/CaseThumbnail.vue';

const props = defineProps(['user', 'userSelf']);
const emit = defineEmits(['userChanged']);

const router = useRouter();
const store = useStore();
const axios = inject('axios');

const changeInfo = ref(false);
const birthdayInput = ref(null);
const sexInput = ref('');
const heightInput = ref(0);
const weightInput = ref(0);

const cases = ref(null);

function updateInfo() {
  let data = new FormData();
  data.append('id', props.user.id);
  data.append('birthday', birthdayInput.value?.getTime() || 0);
  data.append('sex', sexInput.value);
  data.append('height', heightInput.value || 0);
  data.append('weight', weightInput.value || 0);

  displayError(axios
    .post('/user/update/info', data))
    .then(respData => {
      emit('userChanged');
      changeInfo.value = false;
    });
}

function setChangeInfo() {
  if (!changeInfo.value) {
    birthdayInput.value = props.user.birthday;
    sexInput.value = props.user.sex;
    heightInput.value = props.user.height;
    weightInput.value = props.user.weight;
  }
  changeInfo.value = !changeInfo.value;
}

function loadCases() {
  if (!props.user?.id) {
    return;
  }

  axios
    .get('/case/user', {
      params: {
        userId: props.user.id,
      }
    })
    .then(resp => {
      cases.value = resp.data;
    });
}

onMounted(loadCases);

watch(
  () => props.user?.id,
  id => {
    cases.value = null;
    loadCases();
  });

</script>

<template>
  <el-space fill wrap style="width:100%">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>
            基本信息
          </span>
          <el-button v-if="userSelf" @click="setChangeInfo">
            <el-icon>
              <Edit />
            </el-icon>
          </el-button>
        </div>
      </template>

      <el-descriptions :column="2">
        <el-descriptions-item label="生日">
          <span v-if="!changeInfo">
            {{ props.user.birthday?.getFullYear() }} 年
            {{ props.user.birthday && (props.user.birthday.getMonth() + 1) }} 月
            {{ props.user.birthday?.getDate() }} 日
          </span>
          <el-date-picker v-else v-model="birthdayInput"></el-date-picker>
        </el-descriptions-item>
        <el-descriptions-item label="性别">
          <span v-if="!changeInfo">
            {{ props.user.sex }}
          </span>
          <el-select v-else v-model="sexInput">
            <el-option label="男" key="男" value="男" />
            <el-option label="女" key="女" value="女" />
          </el-select>
        </el-descriptions-item>
        <el-descriptions-item label="身高">
          <span v-if="!changeInfo">
            {{ props.user.height }}
          </span>
          <el-input-number v-else v-model="heightInput" :min="0" :max="300"></el-input-number>
          <span> 厘米 </span>
        </el-descriptions-item>
        <el-descriptions-item label="体重">
          <span v-if="!changeInfo">
            {{ props.user.weight }}
          </span>
          <el-input-number v-else v-model="weightInput" :min="0" :max="500"></el-input-number>
          <span> 千克 </span>
        </el-descriptions-item>
      </el-descriptions>
      <el-button v-if="changeInfo" type="primary" @click="updateInfo"> 确认</el-button>
    </el-card>

    <el-card>
      <template #header>
        <div class="card-header">
          <span>
            个人案例
          </span>
          <el-button v-if="userSelf" @click="router.push('/casehome')">
            <el-icon>
              <Edit />
            </el-icon>
          </el-button>
        </div>
      </template>
      <div v-for="caseInfo of cases" style="margin:1%">
        
          <case-thumbnail :case-id="caseInfo.caseId" @click="router.push(
          '/case/' + caseInfo.caseId + '/' + store.state.userId)"></case-thumbnail>
        
      </div>
    </el-card>
  </el-space>
</template>


<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
