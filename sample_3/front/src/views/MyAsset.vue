<template>
  <div class="MyAsset" >
    <el-container style="height: 100% ">
      <el-aside width="auto"><Aside></Aside></el-aside>
      <el-container>
        <el-header><Header></Header></el-header>
        <el-main>
        <el-row gutter="20" type="flex" justify="space-between">
          <el-col style="margin-bottom:0px">
            <el-page-header content="资产列表" @back="goBack"/>
          </el-col>
          <el-divider direction="horizontal " />
          <el-col :span="2" style="margin-bottom:10px">
              <el-button primary 选手填写部分>新增设备</el-button>
          </el-col>
        </el-row>
        <el-row gutter="20" type="flex" justify="space-between">
        <el-col>
        <el-table :data="tableData" stripe border  style="width: 100%">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column fixed prop="numid" label="选手填写部分" width="150" show-overflow-tooltip />
            <el-table-column prop="name" label="选手填写部分" width="150" />
            <el-table-column prop="actual_Power" label="选手填写部分" width="150" />
            <el-table-column prop="rated_Power" label="选手填写部分" width="150" />
            <el-table-column prop="input_Time" label="选手填写部分" width="100" />
            <el-table-column prop="position" label="选手填写部分" width="150" />
            <el-table-column prop="price" label="选手填写部分" width="120" />           
            <el-table-column prop="Ownership" label="选手填写部分" show-overflow-tooltip width="170" />
            <el-table-column fixed="right" label="选手填写部分" width="150">
            <template #default>
                <el-button link type="primary" size="small" 选手填写部分 >出售</el-button>
            </template>
            </el-table-column>
        </el-table>
        </el-col></el-row>



        <!-- 新增弹窗 -->
        <el-dialog v-model="dialogFormVisible" title="选手填写部分">
          <el-form :model="form">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="设备编号: " :label-width="shebie">
                  <el-input v-model="选手填写部分" autocomplete="off" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="设备名称: " :label-width="shebie">
                  <el-input v-model="form.name" autocomplete="off" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">              
              <el-col :span="12">
                <el-form-item label="实际功率: " :label-width="shebie">
                  <el-input v-model="form.actual_Power" autocomplete="off" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="额定功率: " :label-width="shebie">
                  <el-input v-model="form.rated_Power" autocomplete="off" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="结束时间: " :label-width="shebie">
                  <el-input v-model="form.input_Time" autocomplete="off" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工作地点: " :label-width="shebie">
                  <el-input v-model="form.position" autocomplete="off" />
                </el-form-item>
              </el-col>
            </el-row>  
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="单价: " :label-width="shebie">
                  <el-input v-model="form.price" autocomplete="off" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="所属权: " :label-width="shebie">
                  <el-input v-model="form.address" autocomplete="off" />
                </el-form-item>
              </el-col>
            </el-row>                                                          
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogFormVisible = false">离开</el-button>
              <el-button type="primary" @click="insert">确认</el-button>
            </span>
          </template>
        </el-dialog>


      <!-- 售卖弹窗 -->
        <el-dialog v-model="dialogFormVisible2" title="创建订单">
          <el-form>
            <el-form-item label="设备编号">
              <input v-model="createOrderPrice._numid"/>
            </el-form-item>
            <el-form-item label="所属权">
              <input v-model="选手填写部分"/>
            </el-form-item>
            <el-form-item label="价格">
              <el-input v-model="选手填写部分"/>
            </el-form-item>         
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button 选手填写部分>离开</el-button>
              <el-button type="primary" 选手填写部分>确认</el-button>
            </span>
          </template>          
        </el-dialog>
        
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script >
import Header from '../components/Header.vue'
import Aside from '../components/Aside.vue'
import { List } from '@element-plus/icons-vue'
import { el } from 'element-plus/es/locale'
// import { reactive, ref } from 'vue'

// const dialogFormVisible = ref(false)

export default {
  name: 'Home',
  components: {Header,Aside},
  data () {
    return {
      dialogFormVisible: false,
      dialogFormVisible2: false,
      createOrderPrice: {
        _numid: '',
        _price:'',
        address:''
      },
      tableData:[],
      form: {
        _numid:'',
        name:'',
        actual_Power: '',
        rated_Power: '',
        input_Time: '',
        position:'',
        price: '',
        address:'' 
      },     
    }
  },
  methods: {
    goBack() {
        this.$router.push('/')
    },
    // 新增
    add() {
      选手填写部分
    },
    handleClick() {
      选手填写部分
    },
    getAssert(){
      let _Address = 选手填写部分
      this.axios.get(`/get_Address_Assert?_Address=${_Address}`).then((res) => {
        if (res.data.code == 200){
          选手填写部分
        }else{
          alert(`失败,$(res.data.data)`)
        }
      })     
    },
    // listinfo() {
    //   let _Address = "0xb55d15fd16910c67e00b88cb38f0e24b8f3f5ce0"
    //   let _numid = "GB/T2020-0801"
    //   // let _Address = this.$cookies.get('address')
    //   this.axios.get(`/get_numid_Spu?_numid=${_numid}`).then((res) => {
    //     console.log(res.data.data)
    //     if(res.data.code == 200 ){
    //       this.tableData = res.data.data
    //     }else{
    //       alert(`失败,$(res.data.data)`)
    //     }
    //   })
    // },
    insert() {
      this.axios.post(`选手填写部分`, this.form).then((res) => {
        if (res.data.code == 选手填写部分){
          alert('添加成功')
          this.listinfo()
          this.dialogFormVisible = false
        }else('添加失败')
      })
    },
    saller() {
      this.axios.post(`/SPU_saller`, this.createOrderPrice).then((res) => {
        if (res.data.code == 200){
          alert('添加成功')
          this.dialogFormVisible2 = 选手填写部分
          this.listinfo()
        }else('添加失败')
      })
    }
  },
  mounted() {
    this.getAssert()
  }


}
</script>