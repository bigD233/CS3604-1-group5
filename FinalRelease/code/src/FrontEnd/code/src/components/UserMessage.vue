<script setup>
import UserMessageUser from '@/components/UserMessageUser.vue';

const props = defineProps(['userId']);
</script>

<template>
    <el-tabs tab-position="left">
        <el-tab-pane label="私信">
            <UserMessageUser :userId="props.userId" style="width: 100%"></UserMessageUser>
        </el-tab-pane>
    </el-tabs>
</template>
