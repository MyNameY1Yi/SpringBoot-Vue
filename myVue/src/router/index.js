import Vue from 'vue'
import Router from 'vue-router'
import page from '../views/page.vue'
import login from '../views/login/index'
import myPage from '../views/myPage'
import students from '../components/myPageComponents/students/students.vue'
import sinfo from '../components/myPageComponents/students/sinfo.vue'
import trainInfo from '../components/myPageComponents/trainInfo/trainInfo.vue'
import faceInfo from '../components/myPageComponents/faceInfo/faceInfo.vue'
import test from '../components/myPageComponents/test/test.vue'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */

//const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  {
    path: '/login',
    component: login,
    meta: {
      title: "登录",
    }
  },
 {
   path: '/',
   component: page,
   meta: {
     title: "首页",
   }
 },
 {
   path: '/myPage',
   component: myPage,
   meta: {
     title: "我的",
   }
 },
    {
     path: '/students',
     component: students,
     meta: {
       title: "我的学生",
     } 
   },
   {
     path: '/students/sinfo',
     component: sinfo,
     meta: {
       title: "详细资料",
     } 
   },
   {
     path: '/trainInfo',
     component: trainInfo,
     meta: {
       title: "火车检测",
     } 
   },
   {
    path: '/faceInfo',
    component: faceInfo,
    meta: {
      title: "人脸识别",
    } 
  },{
    path:'/test',
    component: test,
    meta:{
      title:"测试页面"
    }
  }
]
export const asyncRouterMap = []
export default new Router({
  routes: constantRouterMap
})
