import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/myAsset',
    name: 'MyAsset',
    component: () => import('../views/MyAsset.vue')
  },
  {
    path: '/market',
    name: 'Market',
    component: () => import('../views/Market.vue')
  },
  {
    path: '/myEnergy',
    name: 'MyEnergy',
    component: () => import('../views/MyEnergy.vue')
  },
  {
    path: '/browser',
    name: 'Browser',
    component: () => import('../views/Browser.vue')    
  },
  {
    path: '/energyMarket',
    name: 'EnergyMarket',
    component: () => import('../views/EnergyMarket.vue')    
  },
  {
    path: '/echarts',
    name: 'Echarts',
    component: () => import('../views/Echarts.vue')   
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
