<template>
    <div style="height:100%;">
        <view-box>
            <x-header slot="header" :left-options="{showBack:false}">
            <!-- <div slot="left">直播</div> -->
            <div>{{title}}</div>
              <div slot="right">
				          <x-button mini :plain="true" type="warn" @click.native="Logout" style="margin-top:-6px">注销</x-button>
				 <!-- <button type="button" class="yongyin" @click="Logout">注销</button> -->
			        </div> 
          </x-header>
    <group>
      <cell :title="'我的学生'" is-link link='/students'></cell>
      <cell :title="'家庭成员'" is-link @click.native="now=2"></cell>
      <cell :title="'我的汽车'" is-link @click.native="now=3"></cell>
      <cell :title="'火车检测'" is-link link='/trainInfo'></cell>
      <cell :title="'人脸识别'" is-link link='/faceInfo'></cell>
      <!-- <cell :title="'测试页面'" is-link link="/test"></cell> -->
    </group> 
        <tabbar slot="bottom" style="position:fixed">
            <tabbar-item  link="/">
              <img src='../assets/房子.png' slot="icon">
              <span slot="label">首页</span>
            </tabbar-item>
            <tabbar-item link="/myPage" selected>
              <img src='../assets/用户.png' slot="icon">
              <span slot="label">我的</span>
            </tabbar-item>
          </tabbar>
    </view-box>
    </div>
</template>

<script>
import { Group, Cell, XHeader,ViewBox,Tabbar,
          TabbarItem,Tab , TabItem,Scroller,Swiper,
          Marquee , MarqueeItem ,Panel ,XButton,LoadMore
          } from 'vux'
export default{
     data(){
        return{
            now:0,
            title:"我的",
        }
    },
    components:{
        Group, Cell, XHeader,ViewBox,Tabbar,
          TabbarItem,Tab , TabItem,Scroller,Swiper,
          Marquee , MarqueeItem ,Panel ,XButton,LoadMore 
    },
    methods:{
        Logout(){
			    this.$store.dispatch('LogOut').then(() => {
				  this.$router.push({path: '/login'});
				  //console.log("logout");
			    location.reload() // 为了重新实例化vue-router对象 避免bug
              })
        },

    }
}
</script>