<template>
<el-row class="tac">
<el-col :span="12">
    <!-- <div style="width: 100;height: 100;"><el-image class="imag" src="./../assets/logo2.png" /></div> -->
    <h2> 首页 </h2>
    <el-menu @select="handleSelect" 
    default-active="0"
    class="el-menu-vertical-demo">
        <el-menu-item index="0">
            <el-icon><View /></el-icon>
            <template #title>仪表盘</template>
        </el-menu-item> 
        <el-sub-menu index="1">
            <template #title>
                <el-icon><User /></el-icon>
                <span>我的列表</span>
            </template>
            <el-menu-item-group>
                <el-menu-item index="1-2">我的能源</el-menu-item>                
                <el-menu-item index="1-1">我的产权</el-menu-item>
            </el-menu-item-group>
        </el-sub-menu>
        <el-menu-item index="2">
            <el-icon><Coin /></el-icon>
            <template #title>产权交易</template>
        </el-menu-item> 
        <el-menu-item index="3">
            <el-icon><Switch /></el-icon>
            <template #title>能源交易</template>
        </el-menu-item>
        <el-menu-item index="4">
            <el-icon><Eleme /></el-icon>
            <template #title>区块浏览器</template>
        </el-menu-item>            
    </el-menu> 
</el-col>
</el-row>
</template>

<script>
import {User, View, Switch, Coin, Eleme} from '@element-plus/icons-vue'

export default {
    name: "Aside",
    components:{User, View, Switch, Coin, Eleme},
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath)
            if (key == '0'){
                this.$router.push('/')
            }else if (key == '1-1'){
                this.$router.push('/myAsset')
            }else if (key == '1-2'){
                this.$router.push('/myEnergy')
            }else if (key == '2'){
                this.$router.push('/market')
            }else if (key == '3'){
                this.$router.push('/EnergyMarket')
            }else if (key == '4'){
                this.$router.push('/browser')
            }
        }
    }
}
</script>


<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 160px;
  min-height: 650px;
}

.imag{
    width: 50;
    height: 50;
}
</style>
