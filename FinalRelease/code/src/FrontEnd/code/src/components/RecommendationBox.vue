<template>
  <div>
    <el-space wrap>
      <el-card v-for="i in 7" :key="i" class="box-card" style="width: 250px">
        <template #header>
          <div class="card-header">
            <span>为你推荐</span>
          </div>
        </template>
        <div v-for="o in 2" :key="o" class="text item">
          {{ "症状 " + o }}
        </div>
      </el-card>
    </el-space>
  </div>
</template>