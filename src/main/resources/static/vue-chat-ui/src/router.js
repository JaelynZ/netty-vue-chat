import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Login from './views/user/Login.vue'
import ChatUi from './views/content/ChatUi.vue'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/home',
    name: 'homeLink',
    component: Home
  },
  {
    path: '/login',
    name: 'loginLink',
    component: Login
  },
  {
    path: '*',
    redirect: '/login'
  }]
})
