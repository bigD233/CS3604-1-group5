<script setup>

import {ref, inject, onMounted} from 'vue';
import {displayError} from '@/utils/messageUtils.js';
import QAThumbnail from "@/components/QAThumbnail.vue";

const axios = inject('axios');

const reportedAnswers = ref([]);

const punishments = ref([]);
const showPunishDialog = ref(false);
const punishUserId = ref(null);
const unpunished = ref(null);


function loadReportedAnswers() {
  axios
      .get('/answer/reported')
      .then(resp => {
        reportedAnswers.value = resp.data;
      });
}


function loadPunishData() {
  displayError(axios
      .get('/user/punishment/all'))
      .then(data => {
        punishments.value = data;
        loadPunishUsers();
      });
}

function loadPunishUsers() {
  for (let i = 0; i < punishments.value.length; i++) {
    displayError(axios
        .get('/user/get', {
          params: {
            id: punishments.value[i].userId,
          }
        }))
        .then(respData => {
          punishments.value[i].user = respData;
        });
  }
}

function stopPunish(userId) {
  punishUserId.value = userId;
  unpunished.value = false;
  showPunishDialog.value = true;
}

onMounted(loadPunishData);
onMounted(loadReportedAnswers);

</script>

<template>

  <el-tabs tab-position="left">
    <el-tab-pane label="举报处理">
      <el-space fill wrap>
        <div class="management-content" v-for="answer in reportedAnswers">
          <QAThumbnail :answer-id="answer.answerId" :report-reason="answer.reason"></QAThumbnail>
        </div>
      </el-space>
    </el-tab-pane>

    <el-tab-pane label="禁言用户">
      <el-space fill wrap>
        <el-card v-for="punishment of punishments">
          <div class="flex-space-between">
            <div class="user-info">
              <el-avatar :size="60" :src="punishment.user?.avatar"/>
              <div>
                <el-link :underline="false" :href="'#/user/' + punishment.userId">
                  <span class="name"> {{ punishment.user?.username }} </span>
                </el-link>
                <div class="description"> {{ punishment.user?.sign }}</div>
                <div class="punishment">
                  已禁言：
                  该用户在 {{ punishment.startTime }} 至 {{ punishment.endTime }} 期间无法发表内容
                </div>
              </div>
            </div>
            <el-button type="danger" @click="stopPunish(punishment.userId)">
              撤销禁言
            </el-button>
          </div>
        </el-card>
      </el-space>
    </el-tab-pane>
  </el-tabs>
  <UserPunishDialog v-model="showPunishDialog" :userId="punishUserId" :unpunished="unpunished"
                    @changed="loadPunishData">
  </UserPunishDialog>

</template>

<style scoped>
.management-content {
  padding: 1vmin 0;
  margin: 0;
}

.time {
  color: #909399;
  font-size: 14px;
}

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

.punishment {
  font-size: 16px;
  font-weight: 600;
  color: red;
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
