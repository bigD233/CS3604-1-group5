<script setup>

import {onMounted, nextTick, ref, inject} from 'vue';
import {ElInput} from 'element-plus'
import {DocumentAdd, Delete} from '@element-plus/icons-vue';

import {useStore} from 'vuex';
import {template} from 'lodash';

const store = useStore();
const axios = inject('axios');
const props = defineProps(["caseId"]);
const myData = ref({});

function loadCase() {
  axios.get(`/case/${props.caseId}/${store.state.userId}`)
      .then(
          resp => {
            if (resp && resp.status === 200) {
              myData.value = resp.data;
            }
          }
      )
}

function selectColor(index) {
  const colors = ['default', 'success', 'info', 'warning', 'danger'];
  return colors[index % colors.length];
}


onMounted(() => {
  loadCase();
})

</script>

<template>
  <el-card class="case-card" shadow="hover" style="background-color:#FAFCFF">
    <template #header>
            <span style="font-size:17px;font-weight:bolder;cursor: pointer;"
                  @click="this.$router.push(`case/${myData.caseId}/${store.state.userId}`)"
            >案例名称：{{ myData.name }}</span>
    </template>
    <div class="case-box">
      <div v-if="myData.open === 1" class="case-content-wrapper">


        <div class="statement-wrapper">
          <p class="case-content"><span style="font-weight:bolder">诊断：</span>
            <v-md-editor
                :model-value="myData.diagnosis" mode="preview" class="markdownBody"
                style="background-color:#FAFCFF"></v-md-editor>
          </p>
          <div class="mask"></div>
        </div>
        <div class="statement-wrapper">
          <p class="case-content"><span style="font-weight:bolder">治疗方法：</span>
            <v-md-editor
                :model-value="myData.treatment" mode="preview" class="markdownBody"></v-md-editor>
          </p>
          <div class="mask"></div>
        </div>

        <div class="statement-wrapper">
          <p class="case-content"><span style="font-weight:bolder">预防：</span>
            <v-md-editor
                :model-value="myData.prevention" mode="preview" class="markdownBody"></v-md-editor>
          </p>
          <div class="mask"></div>
        </div>

        <el-space wrap style="margin-top:2vmin;">
          <el-tag v-for="(tag) in myData.tags" :type="selectColor(tag.tagId)" size="large" effect="dark"
                  :disable-transition="false">
            {{ tag.tagName, tag.tagId }}
          </el-tag>
        </el-space>

        <el-button plain type="danger" class="deletecase" size="small" :icon="Delete" v-show="showManageCases"
                   @click="deleteCase(index)">删除
        </el-button>
      </div>
      <div v-else style=" height: 38vmin;">
        <el-result icon="warning" title="不可查看" sub-title="该案例已被发布者关闭。" style="margin-top:2%">

        </el-result>
      </div>
    </div>
  </el-card>
</template>

<style>
.markdownBody {
  margin: 0%;
  padding: 0px 0px !important;

}

.case-content > * .vuepress-markdown-body {
  padding: 0px 0px !important;
}

.case-content {
  max-height: 10vmin;
  overflow-y: hidden;
}

.statement-wrapper {
  overflow-y: hidden;
  max-height: 11vmin;
}

.case-content-wrapper {
}

.mask {
  width: 100%;
  height: 7vmin;
  z-index: 40;
  position: relative;
  bottom: 6vmin;
  background-image: linear-gradient(-180deg, rgba(255, 255, 255, 0) 0%, #ffffff 80%);
}
</style>