<template>
  <div id="foot">
    <el-row justify="space-evenly">
      <el-col :span="5"></el-col>
      <el-col :span="5">
        <img src="../assets/logo_with_name.png" style="height: 200px;" />
      </el-col>
      <el-col :span="3">
        <h2>关于我们</h2>
        <p>网站介绍</p>
        <p>开发团队</p>
      </el-col>
      <el-col :span="3">
        <h2>联系我们</h2>
        <p>使用帮助</p>
        <p>常见问题</p>
        <p>问题反馈</p>
      </el-col>
      <el-col :span="5"></el-col>
    </el-row>
  </div>
</template>

<style>
#foot {
  margin-top: 10px;
  height: 200px;
}
</style>