<script setup >
import { onMounted, ref, inject } from 'vue';
import Header from '@/components/Header.vue';
import { reactive } from 'vue';
import { useStore } from 'vuex';
// 引入组件
import { EditPen } from '@element-plus/icons-vue';
import Vue3Tinymce from '@jsdawn/vue3-tinymce';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import PublishCase from '../components/PublishCase.vue';
import { formatTime } from "@/utils/timeUtils.js"
const text1 = ref(['', '', '', '', 'hhh']);
const editType = ref('富文本');

const store = useStore();
const axios = inject('axios');


const state = reactive({
    content: ['选择一个你喜欢的文本编辑器', '', '', '', ''],
    // editor 配置项
    setting: {
        height: '71vmin', // editor 高度
        language: 'zh-Hans',
        language_url: '/tinymce/langs/zh-Hans.js',

        toolbar:
            'undo redo | fullscreen | blocks alignleft aligncenter alignright alignjustify | link unlink | numlist bullist | image media table | fontsize forecolor backcolor | bold italic underline strikethrough | indent outdent | superscript subscript | removeformat |',
        toolbar_mode: 'sliding image',
        quickbars_selection_toolbar:
            'removeformat | bold italic underline strikethrough | fontsize forecolor backcolor',
        plugins: 'link image table lists fullscreen quickbars',
        font_size_formats: '12px 14px 16px 18px',
        link_default_target: '_blank',
        link_title: false,
        nonbreaking_force_tab: true,
        images_upload_handler: (blobInfo, progress) => new Promise((resolve, reject) => {
            let data = new FormData();
            data.append('file', blobInfo.blob());
            axios
                .post('/image', data)
                .then(resp => {
                    if (resp.data) {
                        resolve(resp.data);
                    }
                    else {
                        reject(new Error('图片含有不当内容'));
                    }
                })
                .catch(error => {
                    reject(error);
                });
        })
    }
});

function handleUploadImage(event, insertImage, files) {
    for (let i in files) {
        const formData = new FormData();
        formData.append('file', files[i]);
        // console.log(formData);
        axios
            .post('/image', formData)
            .then(response => {
                if (response.data) {
                    insertImage({
                        url: response.data,

                        desc: 'DESC',
                    })
                }
                else {
                    ElMessage.error('图片含有不当内容')
                }
            },
                error => {

                }
            )
    }
}

const route = useRouter()

const active = ref(0)
const currentactive = ref(0)


const myCaseList = ref({
    case_id: route.currentRoute.value.params.publishId,
    name: '',
    intro: '',
    history: '1,',
    diagnosis: '2',
    treatment: '3',
    prevention: '4',
    userId: Number(store.state.userId),
    notes: '',
    tags: '',
    open: 1,
    textType: Number(editType === '富文本'),
    release_time: '',
})

const dialogVisible = ref(false)

const next = () => {
    active.value++;
    currentactive.value++;
    if (active.value > 5) {


        if (editType.value === '富文本') {
            myCaseList.value.intro = state.content[0];
            myCaseList.value.history = state.content[1];
            myCaseList.value.diagnosis = state.content[2];
            myCaseList.value.treatment = state.content[3];
            myCaseList.value.prevention = state.content[4];
        }
        else {
            myCaseList.value.intro = text1.value[0];
            myCaseList.value.history = text1.value[1];
            myCaseList.value.diagnosis = text1.value[2];
            myCaseList.value.treatment = text1.value[3];
            myCaseList.value.prevention = text1.value[4];
        }

        // console.log(myCaseList.value)
        axios
            .post('/case/modify/content', {
                caseId: myCaseList.value.case_id,
                name: myCaseList.value.name,
                intro: myCaseList.value.intro,
                history: myCaseList.value.history,
                diagnosis: myCaseList.value.diagnosis,
                treatment: myCaseList.value.treatment,
                prevention: myCaseList.value.prevention,
                notes: myCaseList.value.notes,
                textType: Number(editType === '富文本'),
                userId: myCaseList.value.userId,
                open: myCaseList.value.open,
                releaseTime: formatTime(),
            })
            .then(resp => {
                if (resp && resp.status === 200) {
                    dialogVisible.value = true;
                    active.value = 0; currentactive.value = 0;
                    myCaseList.value.case_id = resp.data.caseId
                    // console.log(myCaseList.value.case_id)
                }
            }).catch(() => {
                openError();
            })



    }
    if (currentactive.value >= 4) { currentactive.value = 4; }

}





const openError = () => {
    ElMessage.error('提交失败，请稍后重试。')
}


const previous = () => {
    active.value--;
    currentactive.value--;
}
const publishInfo = ref({
    dialogVisible: false,
    open: 1,
    tags: '',
    notes: '',
    name: '',
    publishId: 0,
    userId: Number(store.state.userId),
})


function jump_to_home() {
    route.push('/home')
}

function loadInfo(id) {
    axios
        .get(`/caseForPublish/${id}`)
        .then(resp => {
            if (resp && resp.status === 200) {
                publishInfo.value.open = resp.data.open;
                publishInfo.value.tags = resp.data.tags;
                publishInfo.value.name = resp.data.name;
                publishInfo.value.notes = resp.data.notes;
                publishInfo.value.publishId = resp.data.caseId;
                publishInfo.value.release_time = resp.data.releaseTime;
                if (resp.data.textType === 1) {
                    editType.value = '富文本';
                    state.content[0] = resp.data.intro;
                    state.content[1] = resp.data.history;
                    state.content[2] = resp.data.diagnosis;
                    state.content[3] = resp.data.treatment;
                    state.content[4] = resp.data.prevention;

                }
                else {
                    editType.value = 'Markdown';
                    state.content[0] = resp.data.intro;
                    state.content[1] = resp.data.history;
                    state.content[2] = resp.data.diagnosis;
                    state.content[3] = resp.data.treatment;
                    state.content[4] = resp.data.prevention;
                    text1.value[0] = resp.data.intro;
                    text1.value[1] = resp.data.history;
                    text1.value[2] = resp.data.diagnosis;
                    text1.value[3] = resp.data.treatment;
                    text1.value[4] = resp.data.prevention;

                }


            }
        })
}

onMounted(() => {
    loadInfo(route.currentRoute.value.params.publishId)
    console.log(publishInfo.value.tags);

})




</script>


<template>
    <header>
        <Header />
    </header>

    <main>
        <el-container>
            
            <el-main >
                <div v-if="publishInfo.notes === 'unSuccess'">
                    <el-result icon="error" title="不可编辑" sub-title="很抱歉，您并不是发布者或管理员，没有编辑权限。">
                        <template #extra>
                            <el-button type="primary" @click="jump_to_home">返回首页</el-button>
                        </template>
                    </el-result>
                </div>
                <div v-else>
                    <publish-case :case-info="publishInfo" />
                    <el-dialog v-model="dialogVisible" title="" width="30%" :show-close="false">
                        <el-result icon="success" title="提交成功" sub-title=""></el-result>
                        <template #footer>
                            <span class="dialog-footer">
                                <el-button @click="jump_to_home">返回首页</el-button>
                                <el-button type="primary"
                                    @click="this.$router.push({ name: 'Case', params: { caseId: myCaseList.case_id, visitorId: store.state.userId } })">
                                    查看案例
                                </el-button>
                            </span>
                        </template>
                    </el-dialog>
                    <el-steps :active="active" class="progress">
                        <el-step title="概况" />
                        <el-step title="患者病史" />
                        <el-step title="诊断" />
                        <el-step title="药物治疗" />
                        <el-step title="预防" />
                    </el-steps>

                    <el-card class="box-card" style="background-color: white;">
                        <template #header>
                            <span style="font-size:25px;font-weight:bolder">发布案例</span>
                            <el-button class="button" plain type="info"
                                style="font-size:18px;font-weight:bolder;margin-left: 3%;" :icon="EditPen"
                                @click="publishInfo.dialogVisible = true">修改案例信息</el-button>

                            <div class="selector">

                                <el-radio-group v-model="editType" style="float:right" v-if="(active === 0)">
                                    <el-radio-button label="富文本" />
                                    <el-radio-button label="Markdown" />
                                </el-radio-group>
                                <el-radio-group v-model="editType" disabled style="float:right" v-else>
                                    <el-radio-button label="富文本" />
                                    <el-radio-button label="Markdown" />
                                </el-radio-group>
                            </div>
                        </template>
                        <v-md-editor v-if="(editType === 'Markdown')" v-model="text1[currentactive]" height="71vmin"
                            left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | Publish  "
                            :toolbar="toolbar" disabled-menus="[]" @upload-image="handleUploadImage"></v-md-editor>
                        <!-- <v-md-editor v-if="(editType === 'Markdown' && active == 5)" v-model="text1[4]" height="71vmin"
                        left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | Publish  "
                        :toolbar="toolbar" disabled-menus="[]" @upload-image="handleUploadImage"></v-md-editor> -->
                        <vue3-tinymce v-if="(editType === '富文本')" v-model="state.content[currentactive]"
                            :setting="state.setting" />
                        <!-- <vue3-tinymce v-if="(editType === '富文本' && active == 5)" v-model="state.content[4]"
                        :setting="state.setting" /> -->
                        <el-button type="success" plain class="previous-button" @click="previous"
                            v-if="(active > 0)">上一步</el-button>
                        <el-button type="success" plain class="post-button" @click="next"
                            v-if="(active >= 5)">提交</el-button>
                        <el-button type="success" plain class="post-button" @click="next" v-else>下一步</el-button>
                    </el-card>
                </div>


            </el-main>

        </el-container>
    </main>

</template>


<style scoped>
.title {
    margin-left: 10%;
}

.selector {
    float: right;
}

.box-card {
    margin-top: 1%;
}

.post-button {
    margin-top: 0.5%;
    float: right;
    margin-bottom: 0.5%;
    font-weight: bolder;
    font-size: 17px;
}

.previous-button {
    margin-top: 0.5%;
    float: left;
    margin-bottom: 0.5%;
    font-weight: bolder;
    font-size: 17px;
}

.progress {
    width: 80%;
    margin-left: 10%;
    margin-top: 1%;
}
</style>