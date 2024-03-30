<template>
  <div class="EnergyMarket">
    <el-container style="height: 100% ">
      <el-aside width="auto"><Aside></Aside></el-aside>
      <el-container>
        <el-header><Header></Header></el-header>
        <el-main>

          <el-row>
            <el-col style="margin-bottom:0px" :span="4">
              <el-page-header content="能源市场"></el-page-header>
            </el-col>

            <el-col :span="4" :offset="16">
              <div >
                <el-button primary @click="Visible = true" >挂单出售</el-button></div>
            </el-col>
          </el-row>

          <el-divider direction="horizontal " />

          <!-- 筛选 -->
          <!-- <el-card style="height: 500px;border: 1px; solid #ebeef5;"> -->
          <el-row>
            <el-col :span="6" :offset="16">
              <el-input placeholder="请输入内容" v-model="tableData.date" clearable @clear="getData">
              <!-- <el-alert title="没有找到" type="error" center show-icon v-show="show_alert == true" class="alert" /> -->
              <template #append>
                <el-button @click="getData">
                  <el-icon><Search /></el-icon>
                </el-button>
              </template>
              </el-input>
            </el-col>
          </el-row>

            <el-dialog v-model="Visible" title="交易订单">
              <el-form>
                <el-form-item label="卖家">
                  <input v-model="Order._from"/>
                </el-form-item>
                <el-form-item label="买家">
                  <input v-model="Order._to"/>
                </el-form-item>
                <el-form-item label="能源量">
                  <el-input type="number" v-model="Order._total"/>
                </el-form-item>  
                <el-form-item label="单价">
                  <el-input v-model="Order._price"/>
                </el-form-item>        
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="Visible = false">离开</el-button>
                  <el-button type="primary" @click="sell">确认</el-button>
                </span>
              </template>       
            </el-dialog>

          <!-- 表 -->

          <el-row>
            <!-- <el-button @click="resetDateFilter">reset date filter</el-button>
            <el-button @click="clearFilter">reset all filters</el-button> -->
            <el-table :data="tableData" :default-sort="{ prop: 'date', order: 'descending' }">
                  <el-table-column fixed sortable prop="date" label="日期" width="300" />
                  <el-table-column prop="from" label="卖家" width="200" />
                  <el-table-column prop="to" label="买家" width="300" />
                  <el-table-column sortable prop="total" label="能源量(kW·h)" width="200" />
                  <el-table-column sortable prop="price" label="单价(元/kW·h)" width="200" />

            </el-table>
          </el-row>

          <div style="margin-top:360px">
            <hr class="my-4" />
            <el-pagination
              :hide-on-single-page="value"
              :total="5"
              layout="prev, pager, next"
            />
          </div>

          <!-- </el-card> -->
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
<Login ref="login"></Login>
import Header from '../components/Header.vue'
import Aside from '../components/Aside.vue'
import {Search} from '@element-plus/icons-vue'
import Login from './Login.vue'

export default {
  name: 'EnergyMarket',
  components: {
    Header,Aside,Search,Login
  },
  data() {
    return {
      value : false,
      tableData: [],
      Visible: false,
      Order: {
        _from: '',
        _to: '',
        _total: '',
        _price : ''
      },
      user:''
    }
  },
  methods: {
    formatter(row, column) {
      return row.price;
    },
    async getData() {
      const {data: res} = await this.$http.get('users', {
        params: this.tableData
      })
      console.log(res)
      if (res.meta.status !== 200) {
        return this.$message.error('获取用户列表失败')
      }
      this.userlist = res.data.users
      this.total = res.data.total      
    },
    listinfo() {
      let _Address = this.$cookies.get('useraddress')
      console.log(this.$cookies.get('useraddress'))
      this.axios.get(`/get_Energy_Order?_Address=${_Address}`).then((res) => {
        console.log(res.data.data)
        if(res.data.code == 200 ){
          this.tableData = res.data.data
        }else{
          alert(`失败,$(res.data.data)`)
        }
      })
    },
    sell(){
      this.axios.post(`/Energy_transfer`, this.Order).then((res) => {
        if (res.data.code == 200){
          alert('购买成功')
          this.Visible = false
        }else{
          alert('购买失败')
        }
      })      
    }
  },
  mounted() {
    this.listinfo()
  }
}
</script>