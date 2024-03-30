<template>
  <div class="MyEnergy">
    <el-container style="height: 100% ">
      <el-aside width="auto"><Aside></Aside></el-aside>
      <el-container>
        <el-header><Header></Header></el-header>
        <el-main>
          <el-row>
            <el-col :span="6">
              <el-card style=" height: 100%">
                <img :src='this.img' class="image">
                	  <div style="padding: 14px;">
                    <span>总能量 ：{{this.total}}</span>
                      <div class="bottom clearfix">
                        <el-button type="text" class="button" @click="updata" >更新</el-button>
                      </div>
                    </div>
              </el-card>
            </el-col>

            <el-col :span="16" >

                <div id="myChart2" :style="{ width: '100%', height: '300px' }"></div>
                  <!-- <el-row class="Toprow" >
                    <el-card style="height: 250px; width:800px">
                    <el-col :span="4" 
                            :offset="1" 
                            v-for="item in listData" 
                            v-bind:key="item"
                            style="margin-bottom:20px"
                          >
                      <el-card shadow="hover">
                        <i>{{item.name}}</i>
                        <div>
                          {{item.value}}
                        </div>
                      </el-card>
                    </el-col>
                    </el-card>
                  </el-row> -->
          </el-col>

            <el-divider direction="horizontal " />
            

            <el-row>
              <div>
              <el-card style="height:300px">
                <el-col :span="4"><h3>能源明细 :</h3></el-col>
                <el-col class="table">
                  <el-table :data="tableData" border stripe style="width: 100%" >
                    <el-table-column prop="encryptType" label="变化" width="500px" />
                    <el-table-column prop="hashValue" label="哈希值" width="800px" />             
                  </el-table>
                </el-col>
              </el-card>
              </div>
            </el-row>


          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Header from '../components/Header.vue'
import Aside from '../components/Aside.vue'
import * as echarts from 'echarts'
import img from '../assets/logo2.png'

export default {
  name: 'MyEnergy',
  components: {Header,Aside},
  data() {
    return {
      img: img,
      total:'',
      tableData: [{
        encryptType: 0,
        hashValue: "8af27cf74aac2688cd726c07a54289d4ba183944dedc36dfdfdf457c8a8d2e11"
      }],    
      listData: []        
    }
  },
  methods: {
    updata(){
      let _Address = this.$cookies.get('useraddress')
      this.axios.get(`/Energy_update?_Address=${_Address}`).then((res) =>  {
        if(res.data.code == 200){
          alert("更新成功")
        }else(
          alert("失败")
        )
      })
    },
    gettotal(){
      let _Address = this.$cookies.get('useraddress')
      this.axios.get(`/get_Total_Energy?_Address=${_Address}`).then((res) =>  {
        if(res.data.code == 200){
          console.log(JSON.parse(res.data.data))
         this.total = JSON.parse(res.data.data)
        }else(
          alert("失败")
        )
      })
    },
    getHash() {
      this.axios.get(`/show/gethash`).then((res) =>  {
        if(res.data.code == 200){
          console.log(res.data.data)
          this.tableData = res.data.data
        }else(
          alert("失败")
        )
      })
    },
    drawChart2(){
      let _Address = this.$cookies.get('useraddress')
      this.axios.get(`/get_numid_Energy?_Address=${_Address}`).then((res) => {
        console.log(res.data.data)
        if(res.data.code == 200 ){
          console.log(res.data.data)
          let myChart2 = echarts.init(document.getElementById("myChart2"));
          myChart2.setOption({
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: {
              data: res.data.data
            },
            yAxis: {},
            series: [
              {
                data: res.data.data, 
                type: 'line',
                smooth: true
              }
            ]        
          })
        }else{
          alert(`失败,$(res.data.data)`)
        }
      })
    },
  },
  mounted(){
    this.drawChart2()
    this.getHash()
    this.gettotal()
  }
}
</script>


<style scoped>
.Toprow{
  height: 300px;
  width: 90%;
}

.el-card{
  height: 80px;
}
.table{
  text-align: center;
  margin: auto;  
}

.bottom {
	margin-top: 13px;
	line-height: 12px;
  }

.button {
	padding: 0;
	float: right;
  }

.image {
	width: 300px;
  height: 200px;
	display: block;
  }
</style>