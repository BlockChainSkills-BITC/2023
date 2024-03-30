<template>
<div class="building">
  <el-row style="height: 100%;">
    <el-col :span="8" :offset="8" class="login-box">

      <el-row >
        <el-col :span="16" :offset="4">
          <el-form label-width="100px">
            <h2>电力能源交易系统登录</h2>
            <el-form-item label="地址:">
              <input type="primary" 选手填写部分 v-model="logindata.address"/>
            </el-form-item>
            <el-form-item label="密码:">
              <input 选手填写部分 v-model="选手填写部分"/>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      
      <el-row style="height: 50px">
        <el-col :span="16" :offset="4">
        <el-button type="primary" 选手填写部分 round>登录</el-button>
        <el-button type="primary" 选手填写部分 round>注册</el-button>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</div>
</template>

<script>
export default {
    name: "Login",
    data() {
        return {
          logindata: {
            address: '',
            password: ''
          },
          // uaddress:''
        }
    },
    methods: {
      loginfun() {
        // 表单信息检查
        选手填写部分
        console.log(this.logindata.password )
        this.axios.post('/account/login', this.logindata).then((response) => {
          if (response.data.code == 选手填写部分) {
              this.$cookies.set("useraddress", response.data.data)
              console.log(this.$cookies.get('useraddress'))
              // this.uaddress = response.data.data
              选手填写部分
          }
        })
      },
      register: function () {
        选手填写部分
      },
    },
}
</script>

<style >
/* #building{
  background:url('../assets/');
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
} */
/* .building{
  background: beige;
  background-size:100% 100%;
} */

.login-box{
  border-style: solid;
  border-color: #e6e6e6;
  margin-top: 5%;
  /* background-color:rgba(255,255,255,0.7); */
  background-color:#d1dbe5
}

</style>

