<script setup>
import { ref, watch,inject } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const select = ref({num:0})
const selectName = ref({val:0})
const input = ref('')

const axios = inject('axios');
const router = useRouter()

const props = defineProps({
  boxSize: String,
})

function handleSearch() {
  switch (select.value.num) {
    case '1':
      router.push({ path: '/caseSearch', query: { caseInput: input.value, kind:1 } });
      break;

    case '2':
      router.push({ path: '/queSearch', query: { queInput: input.value, kind:2 } });
      break;

    case '3':
      router.push({ path: '/userSearch', query: { userInput: input.value, kind:3 } });
      break;

    case '4':
      router.push({ path: '/caseSearch', query: { semanticInput: input.value, kind:4 } });
      break;

    default:
      break;
  }
}

const semanticString=ref('皮肤有问题')
function commitquery(){
  axios.get(`/semanticSearch/${semanticString.value}`)
      .then(resp=>{
        console.log(resp.data);
      })
}

//实现回车键搜索
function searchEnter() {
  handleSearch();
}

//节点点击事件
function handleClickNode(data) {
  console.log(data);
  select.value.num = data.value;
  selectName.value.val=data.label;
  // 选择器执行完成后，使其失去焦点隐藏下拉框的效果
  selectTree.value.visible=false;
}

const theTree=ref(null);
const selectTree =ref(null);
const tree = [
    {
      label: "案例",  //label对应父级标签
      value: "1",
      children: [   //父标签的children数组内每个对象生成该父标签的一个子标签
        {
          label: "模糊",
          value: "1",
        },
        {
          label: "语义",
          value: "4",
        },

      ],
    },
  {
    label: "问答",
    value: "2",
  },
  {
    label: "用户",
    value: "3",
  },
];

const defaultProps = {  //规定
  children: "children",
  label: "label",
};


</script>

<template>
  <el-input v-model="input" placeholder="请输入想要查询的内容" class="input-with-select" :size="boxSize"
    @keyup.enter.native="searchEnter">
    <template #prepend>
      <el-select ref="selectTree" v-model="select" placeholder="类型"  style="width: 80px;" :size="boxSize">
        <el-option hidden key="id" :value="select" :label="selectName.val">
        </el-option>
<!--        设置树形控件-->
        <el-tree
            :data="tree"
            :props="defaultProps"
            @node-click="handleClickNode"
            :expand-on-click-node="false"
            :check-on-click-node="true"
            ref="theTree"
            node-key="id"
            :default-expand-all="true"
            :filter-node-method="filterNode"
        >
        </el-tree>

      </el-select>
    </template>
    <template #append>
      <el-button @click="handleSearch" style="width: 50%;"><el-icon>
          <Search />
        </el-icon></el-button>
    </template>

  </el-input>

</template>

<style scoped>
.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
</style>