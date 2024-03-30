<template>
  <div class="Browser">
    <el-container style="height: 100% ">
      <el-aside width="auto"><Aside></Aside></el-aside>
      <el-container>
        <el-header><Header></Header></el-header>
        <el-main>
          <el-row stripe style="width: 100%" max-height="250">
          <el-col >
            <el-page-header content="选手填写部分" @back="goBack" />
          </el-col>
          <el-divider direction="horizontal " />
          <el-col>
            <el-table
                :data="contracts"
                style="width: 100%">
              <el-table-column
                  prop="合约名称"
                  label="选手填写部分"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="合约地址"
                  label="选手填写部分"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="部署时间"
                  label="选手填写部分">
              </el-table-column>
              <el-table-column
                  prop="创建时间"
                  label="选手填写部分">
              </el-table-column>
              <el-table-column
                  prop="修改时间"
                  label="选手填写部分">
              </el-table-column>
            </el-table>

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

export default {
  name: 'Browser',
  components: {
    Header,
    Aside
  },
  data () {
    return {
      contracts:[]
    }
  },
  created() {
    this.axios.get(`/list`).then((response) => {
      if(response.data.result){
        this.contracts = 选手填写部分
      }
    })
  },
  methods: {
    goBack() {
        this.$router.push('/')
    }
  } 
}
</script>