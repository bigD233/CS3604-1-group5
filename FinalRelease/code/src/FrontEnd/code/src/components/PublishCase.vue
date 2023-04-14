<script lang="ts" setup>
import { inject, onMounted, ref, nextTick, reactive, onBeforeMount, watch } from 'vue';
import { useRouter } from 'vue-router'
import { formatTime } from "@/utils/timeUtils.js"
const router = useRouter()


const props = defineProps(["caseInfo"]);

const rules = reactive({
    name: [
        { required: true, message: '请填写案例名称', trigger: 'change' },
    ],
    notes: [
        {
            required: true,
            message: '请填写案例简述',
            trigger: 'change',
        },
    ],
    open: [
        {
            required: true,
            message: '请确认是否开放',
            trigger: 'change',
        },
    ],


})
const axios = inject('axios');
const formSize = ref('default')
const ruleFormRef = ref(null)

const submitForm = async () => {
    if (!ruleFormRef) return;
    ruleFormRef.value.validate((valid) => {
        if (valid) {
            console.log("submit!");
            //    router.push({ name: 'Casepublish', params: { publishId: 0, name: 222, notes:3, open: 5, tags: 4} })
            axios
                .post('/case/add', {
                    caseId: props.caseInfo.publishId,
                    name: props.caseInfo.name,
                    intro: '',
                    history: '',
                    diagnosis: '',
                    treatment: '',
                    prevention: '',
                    notes: props.caseInfo.notes,
                    textType: 1,
                    userId: props.caseInfo.userId,
                    open: props.caseInfo.open,
                    releaseTime: formatTime(),
                }, {
                    params: { tags: dynamicTags.value.join(";") }
                })
                .then(resp => {
                    if (resp && resp.status === 200) {
                        props.caseInfo.publishId = resp.data.caseId;
                        router.push({ name: 'Casepublish', params: { publishId: resp.data.caseId } });
                        console.log(dynamicTags.value.join(";"));
                        props.caseInfo.dialogVisible = false;
                    }
                })
            return false;
        }
    });
};


const inputValue = ref('')
const dynamicTags = ref([])
const relatedTags = ref([])



console.log(props.caseInfo.tags);

const inputVisible = ref(false)
const InputRef = ref<InstanceType<typeof ElInput>>()

const handleClose = (tag: string) => {
    dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1)
}

const showInput = () => {
    inputVisible.value = true
    nextTick(() => {
        InputRef.value!.input!.focus()
    })
}

const handleInputConfirm = () => {
    if (inputValue.value) {
        dynamicTags.value.push(inputValue.value)
    }
    inputVisible.value = false
    inputValue.value = ''
}


function clear() {
    props.caseInfo.dialogVisible = false;


}

const querySearch = (queryString, cb) => {
    if (queryString) {
        relatedTags.value = [];
    }
    axios
        .get(`/tagSearch/${queryString}`)
        .then(
            resp => {
                if (resp && resp.status === 200) {


                    for (let item of resp.data) {
                        relatedTags.value.push({ value: item.tagName });
                        console.log(item);
                    }
                    console.log(relatedTags.value);

                }
            }
        )
    cb(relatedTags.value);
}
const handleSelect = (item) => {
    console.log(item)
}


watch(  // 监听 props.questionId 变化
    () => props.caseInfo.tags,
    (newProp) => {
        console.log("!!!!!!");
        for (let oldTag of props.caseInfo.tags) {
            dynamicTags.value.push(oldTag.tagName);
        }
    }
);




</script>

<template>
    <el-dialog v-model="props.caseInfo.dialogVisible" title="" width="30%" :show-close="false">

        <el-form ref="ruleFormRef" :model="props.caseInfo" :rules="rules" label-width="120px" class="demo-ruleForm"
            :size="formSize" status-icon>
            <el-form-item label="案例名称" prop="name">
                <el-input v-model="props.caseInfo.name" />
            </el-form-item>
            <el-form-item label="案例简述" prop="notes">
                <el-input v-model="props.caseInfo.notes" />
            </el-form-item>
            <el-form-item label="是否开放" prop="open">
                <el-switch v-model="props.caseInfo.open" active-text="开放" inactive-text="关闭" active-value="1"
                    inactive-value="0" />
            </el-form-item>
            <el-form-item label="添加标签">
                <el-tag v-for="tag in dynamicTags" class="question-tag" :key="tag" closable :disable-transitions="false"
                    @close="handleClose(tag)" round size="large">
                    {{ tag }}
                </el-tag>
                <el-autocomplete v-if="inputVisible" ref="InputRef" v-model="inputValue" placeholder="输入标签后按回车键以添加"
                    size="default" @keyup.enter="handleInputConfirm" @blur="handleInputConfirm"
                    :trigger-on-focus="false" :fetch-suggestions="querySearch" @select="handleSelect" />
                <el-button v-else @click="showInput">
                    + 添加标签
                </el-button>
            </el-form-item>


        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="clear">返回</el-button>
                <el-button type="primary" @click="submitForm(ruleFormRef)">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>