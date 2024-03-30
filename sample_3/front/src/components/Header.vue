<template>
<header>
    <div class="l-content">
        <el-icon><Switch /></el-icon>
    </div>
    <h2 > 区块链绿色能源系统</h2>
    <div class="r-content">
        <el-dropdown size="mini">
            <span class="el-dropdown-link">
                <img src="./../assets/logo.png" class="user" alt="照片">
            </span>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item>个人中心</el-dropdown-item>
                    <el-dropdown-item disabled>Action 4</el-dropdown-item>
                    <el-dropdown-item @click="logout" divided>退出</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
</header>
</template>
<script>
import {Switch} from '@element-plus/icons-vue'

export default {
    components:{Switch},
    methods: {
        logout () {
            this.$router.push('/login')
        }
    }
}
</script>

<style lang="scss" scoped>
header {
  display: flex;
  height: 100%;
  align-items: center;
  justify-content: space-between;

}

.r-content {
    .user {
        width: 50px;
        height: 50px;
        border-radius: 50%;
    }
}

.l-content {
  display:  flex;
  align-items: center;
}

</style>