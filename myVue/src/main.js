import Vue from 'vue'
import 'normalize.css/normalize.css'// A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import App from './App'
import router from './router'
import store from './store'
import '@/icons' // icon
import {default as api} from './utils/api'
import {hasPermission} from "./utils/hasPermission";
import FastClick from 'fastclick'
import qs from 'qs'
import axios from 'axios'
import Vant from 'vant';
import 'vant/lib/index.css';
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css' // Progress 进度条样式
import {getToken} from '@/utils/auth' // 验权
const whiteList = ['/login', '/404'] //白名单,不需要登录的路由
 Vue.use(Vant);

Vue.prototype.$axios=axios
Vue.prototype.$qs=qs
FastClick.attach(document.body)
Vue.use(ElementUI, {locale})
Vue.prototype.api = api
//全局的常量
Vue.prototype.hasPerm = hasPermission
//生产环境时自动设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = (process.env.NODE_ENV != 'production')

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    //如果已经登录

    if (to.path === '/login') {
      next({path: '/'})
      NProgress.done() // 结束Progress
    } else if (!store.getters.role) {
      store.dispatch('GetInfo').then(() => {
        next({...to})
      })
    } else {
      next()
    }
  } else if (whiteList.indexOf(to.path) !== -1) {
    //如果前往的路径是白名单内的,就可以直接前往
    next()
  } else {
    //如果路径不是白名单内的,而且又没有登录,就跳转登录页面
    store.commit('RESET_USER')
    console.log(12133434234)
    next('/login')
    NProgress.done() // 结束Progress
  }
})
router.afterEach(() => {
  NProgress.done() // 结束Progress
})

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app-box')