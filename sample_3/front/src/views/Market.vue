<template>
  <div class="Market">
    <el-container style="height: 100% ">
      <el-aside width="auto"><Aside></Aside></el-aside>
      <el-container>
        <el-header><Header></Header></el-header>
        <el-main>
        <el-row><el-col>
            <el-page-header content="选手填写部分" @back="goBack"/>
        </el-col></el-row>
        <el-divider direction="horizontal " />

        <!-- <el-row v-for="item in list_data" v-bind:key="item" class="row1">
            <el-col :span="4" >
                <el-image src="item.img" class="images"/>
            </el-col>
            <el-col :span="26">
            <el-descriptions title="    " column="4" border>
                <el-descriptions-item label="名称">{{item.name}}</el-descriptions-item>
                <el-descriptions-item label="型号">{{item.type}}</el-descriptions-item>
                <el-descriptions-item label="数量">{{item.num}}</el-descriptions-item>
                <el-descriptions-item label="卖方">{{item.seller}}</el-descriptions-item>
                <el-descriptions-item label="额定功率">{{item.ratedPower}}</el-descriptions-item>
                <el-descriptions-item label="实际功率">{{item.actualPower}}</el-descriptions-item>
                <el-descriptions-item label="投入时间">{{item.inputTime}}</el-descriptions-item>
                <el-descriptions-item label="所在位置">{{item.position}}</el-descriptions-item>
            </el-descriptions>            
            </el-col>
            <el-col :span="3" >
                <el-button type="primary" round>购买</el-button>
                <p>       </p>
                <el-button type="link">查看详情</el-button>
            </el-col>
        </el-row> -->
        <el-row>
          <el-input placeholder="选手填写部分" v-model="选手填写部分" style="margin-bottom:20px">
            <template #append>
              <el-button 选手填写部分>
                <el-icon><Search /></el-icon>
              </el-button>
            </template>
          </el-input>

          <el-col
            v-for="item in list_data"
            :key="item"
            :span="8"
            :offset="2">

            <el-card :body-style="{ padding: '0px' }" style="margin-bottom: 20px">
              <img
              v-for="item2 in imgs"
              :key="item2"
                :src="item2.img"
                class="image"/>

                  <span>
                    总价:
                  </span>
                  <span>
                    选手填写部分
                  </span>

              <div style="padding: 14px">
                <span>选手填写部分</span>
                <div class="bottom">
                  <time class="time">{{ currentDate }}</time>
                  <el-button text @click="drawer = true">详细信息</el-button>  
                  <el-button text 选手填写部分>购买</el-button>             
                </div>
                <el-form size="mini">
                  <el-form-item label="型号:">
                    选手填写部分
                  </el-form-item>
                  <el-form-item label="卖方:">
                    选手填写部分
                  </el-form-item>
                  <el-form-item label="额定功率:">
                    选手填写部分
                  </el-form-item>
                  <el-form-item label="实际功率:">
                    选手填写部分
                  </el-form-item>
                  <el-form-item label="投入时间:">
                    选手填写部分
                  </el-form-item>
                  <el-form-item label="所在位置:">
                    选手填写部分
                  </el-form-item>
                </el-form>
              </div>
            </el-card>


              <el-drawer v-model="drawer" title="I am the title" :with-header="false">
                <span>太阳能电池又称为“太阳能芯片”或“光电池”，
                  是一种利用太阳光直接发电的光电半导体薄片。
                  单体太阳能电池不能直接做电源使用。
                  作电源必须将若干单体太阳能电池串、
                  并联连接和严密封装成组件</span>
              </el-drawer>
              
            <el-dialog v-model="transferorder" title="购买确认">
              <el-form>
                <el-form-item label="设备编号">
                  <input v-model="选手填写部分"/>
                </el-form-item>
                <el-form-item label="所属权">
                  <input v-model="选手填写部分"/>
                </el-form-item>
                <el-form-item label="当前账号">
                  <el-input v-model="选手填写部分"/>
                </el-form-item>         
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="transferorder = false">离开</el-button>
                  <el-button type="primary" 选手填写部分>确认</el-button>
                </span>
              </template>       
            </el-dialog>

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
import { ElButton, ElDrawer } from 'element-plus'
import { CircleCloseFilled, Search } from '@element-plus/icons-vue'
import image1 from '../assets/image1.png'

export default {
  name: 'Market',
  components: {Header,Aside,CircleCloseFilled,ElButton, ElDrawer,Search},
  data() {
    return {
      currentDate: new Date(),
      drawer: false,
      list_data: [],
      imgs: [{img: image1}],
      owner: {
        _numid: '',
        address:'',
        ownership:''
      },
      transferorder: false,
      searchs:''
    }
  },  

  methods: {
    goBack() {
        this.$router.push('/')
    },
    handleClick() {
      this.transferorder = true
    },
    transfer() {
      this.axios.post(`/SSPU_transfer`, this.owner).then((res) => {
        if (res.data.code == 200){
          alert('选手填写部分')
          this.transferorder = false
        }else{
          alert('选手填写部分')
        }
      })
    },
    // listinfo() {
    //   let _numid = "GB/T 2296-2001"
    //   this.axios.get(`/get_numid_Spu?_numid=${_numid}`).then((res) => {
    //     console.log(res.data.data)
    //     if(res.data.code == 200 ){
    //       this.list_data = res.data.data
    //     }else{
    //       alert(`失败,$(res.data.data)`)
    //     }
    //   })
    // },
    getData() {
      let _numid = this.searchs
      this.axios.get(`/get_numid_Spu?_numid=${_numid}`).then((res) => {
        console.log(res.data.data)
        if(res.data.code == 200 ){
          选手填写部分
          console.log(res.data.data)
        }else{
          alert(`失败,$(res.data.data)`)
        }
      })
    }
  },
  mounted() {
    // this.listinfo()
  }
}
</script>

<style scoped>

.images{
    width: 100px;
    height: 100px;
    border-radius: 100%;

}

.row1{
    /* display: inline-block;    */
    margin-top: 40px; 
    background-color:darkgrey;
}
.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  display: block;
  width: 150px;
  height: 150px;
}
</style>