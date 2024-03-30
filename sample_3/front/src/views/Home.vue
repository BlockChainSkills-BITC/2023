<template>
  <div class="Home">
    <el-container style="height: 100% ">
      <el-aside width="auto"><Aside></Aside></el-aside>
      <el-container>
        <el-header><Header></Header></el-header>
        <el-main><router-view></router-view>
        <el-row>
          <el-col :span="6">
            <el-card shadow="hover" :body-style="{ padding: '20px' }">
              <div style="padding: 14px;">
                <span>区块数量 ：</span>
                <div>{{this.block}}</div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="6"  >
            <el-card shadow="hover" :body-style="{ padding: '20px' }">
              <div style="padding: 14px;">
                <span>节点数量 ：</span>
                <div>{{4}}</div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="6"  >
            <el-card shadow="hover" :body-style="{ padding: '20px' }">
              <div style="padding: 14px;">
                <span>交易数量 ：</span>
                <div>{{this.block}}</div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="6"  :gutter="6">
            <el-card shadow="hover" :body-style="{ padding: '20px' }">
              <div style="padding: 14px;">
                <span>待交易数量 ：</span>
                <div>{{0}}</div>
              </div>
            </el-card>
          </el-col>
          
            <el-col>
              <el-card>
              <div id="myChart3" :style="{  width: '50%', height: '300px' }"></div>
              </el-card>
            </el-col>

            <!-- <el-col>
              <el-card>
              <div id="myChart2" :style="{ width: '100%', height: '300px' }"></div>
              </el-card>
            </el-col> -->

            <el-col>
              <el-card>
              <div id="myChart" :style="{ width: '100%', height: '300px' }"></div>
              </el-card>
            </el-col>
        
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

export default {
  name: 'Home',
  components: {
    Header,
    Aside
  },
  data(){   
    return {
      block:''
    }
  },
  mounted(){
    this.drawChart()
    // this.drawChart2()
    this.drawChart3()
    this.getblock()
  },
  methods: {
    getblock(){
      this.axios.get(`/show/getblockNum`).then((res) => {
        console.log(res.data.data)
        if(res.data.code == 200 ){
          this.block = res.data.data
          console.log(res.data.data)
        }else{
          alert(`失败,$(res.data.data)`)
        }
      })
    },
    drawChart() {
      let myChart = echarts.init(document.getElementById("myChart"));
      // 指定图表的配置项和数据
      myChart.setOption({
        title: {
          text: '订单情况(数量)'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['总订单', '产权订单', "能量订单", "运输订单"]
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '总订单',
            type: 'line',
            stack: 'Total',
            data: [25, 35, 31, 38, 48, 63, 41]
          },
          {
            name: '产权订单',
            type: 'line',
            stack: 'Total',
            data: [10, 12, 11, 23, 29, 30, 31]
          },
          {
            name: '能量订单',
            type: 'line',
            stack: 'Total',
            data: [15, 23, 20, 15, 19, 33, 10]
          },
        ]
      });
    },
    drawChart3() {
      let _Address = this.$cookies.get('useraddress')
      this.axios.get(`/get_Assert?_Address=${_Address}`).then((res) => {
        if(res.data.code == 200 ){
          console.log(res.data.data)
          let myChart3 = echarts.init(document.getElementById("myChart3"));
          // 指定图表的配置项和数据
          myChart3.setOption({
            title: {
              text: '产品(数量)'
            },
            series: [
              {
                type: 'pie',
                data: [
                  {
                    value: 335,
                    name: res.data.data[0]
                  },
                  {
                    value: 234,
                    name: res.data.data[1]
                  },
                  {
                    value: 1548,
                    name: res.data.data[2]
                  },
                  {
                    value: 500,
                    name: res.data.data[3]
                  }
                ]
              }
            ]
          })
        }else{
          alert(`失败,$(res.data.data)`)
        }
      })
    }
  }
}
</script>

<style lang="scss">
.el-header{
  padding-top: 0;
  background: darkgray;
}
.el-main {
  padding-top: 0;
}
.el-aside{
  background: whitesmoke;
}
</style>