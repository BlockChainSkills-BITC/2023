<template>
  <div class="Echarts">
    <el-container style="height: 100% ">
      <el-aside width="auto"><Aside></Aside></el-aside>
      <el-container>
        <el-header><Header></Header></el-header>
        <el-main>
            <el-row>
                <el-col>
                    <div id="myChart" :style="{ width: '300px', height: '300px' }"></div>
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
  name: 'Echarts',
  components: {
    Header,
    Aside
  },
  data(){   
  },
  mounted(){
    this.drawChart();
  },
  methods: {
    drawChart() {
      let myChart = echarts.init(document.getElementById("myChart"));
      // 指定图表的配置项和数据
      myChart.setOption({
        title: { text: "总用户量" },
        tooltip: {},
        xAxis: {
          data: ["12-3", "12-4", "12-5", "12-6", "12-7", "12-8"],
        },
        yAxis: {},
        series: [
          {
            name: "用户量",
            type: "line",
            data: [5, 20, 36, 10, 10, 20],
          },
        ],
      });
    },
  }
}
</script>

