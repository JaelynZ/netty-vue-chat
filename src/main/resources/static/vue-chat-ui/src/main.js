import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ckq from 'vue-ckq-layer'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css' // 默认主题
import { getCookie } from './util/util'

Vue.config.productionTip = false
Vue.use(ckq)
// 注册element-ui
Vue.use(ElementUI)

new Vue({
  router,
  store,
  created () {
    this.checkLogin()
  },
  methods: {
    checkLogin () {
      // 检查是否存在session
      // cookie操作方法在源码里有或者参考网上的即可
      console.log('初始化vue打印cookie:' + getCookie('user'))
      if (!getCookie('user')) {
        // 如果没有登录状态则跳转到登录页
        this.$router.push('/login')
      } else {
        // 否则跳转到登录后的页面
        this.$router.push('/home')
      }
    }
  },
  render: h => h(App)
}).$mount('#app')
