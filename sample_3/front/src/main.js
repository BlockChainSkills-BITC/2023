import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from "axios";
import VueAxios from 'vue-axios'
import VueCookies from 'vue-cookies'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as echarts from 'echarts'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App);
app.echarts=echarts
app.use(ElementPlus)
app.use(router)
app.use(VueAxios, axios)
app.use(VueCookies)
app.use(store)
axios.defaults.baseURL='/api'
axios.defaults.withCredentials = true
app.mount('#app')
