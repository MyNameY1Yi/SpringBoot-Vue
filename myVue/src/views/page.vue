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

          	<scroller :lock-y='true'>
		            <div>
		              <tab :line-width="1" custom-bar-width="60px">
		                <tab-item selected>推荐</tab-item>
		                <tab-item>资讯</tab-item>
		                <tab-item>科技</tab-item>
		                <tab-item>娱乐</tab-item>
		              </tab>
		            </div>
		          </scroller>

          <tabbar slot="bottom" style="position:fixed">
            <tabbar-item selected>
              <img src='../assets/房子.png' slot="icon">
              <span slot="label">首页</span>
            </tabbar-item>
            <tabbar-item link="/myPage">
              <img src='../assets/用户.png' slot="icon">
              <span slot="label">我的</span>
            </tabbar-item>
          </tabbar>

		       <swiper :duration=1000 :loop='true' :auto='true' :interval=4000 :list="demo01_list" v-model="demo01_index" @on-index-change="demo01_onIndexChange"></swiper>
						
            <group>
							<cell :title="marquee_title">
								<marquee :interval=3500  :duration=500>
									<marquee-item v-for="i in 5" :key="i">{{"公告序号:"}}{{i}}</marquee-item>
								</marquee>
							</cell>
						</group> 
		            <panel :list="panelList" v-if="this.totalCount>=1" ></panel>
					<load-more :tip='"正在加载"' v-if="this.totalCount==-1"></load-more>
					<load-more :show-loading="false" :tip="'暂无数据'" background-color="#fbf9fe" v-if="this.totalCount==0"></load-more>

		  
         </view-box>
    </div>
</template>


<script>
import { Group, Cell, XHeader,ViewBox,Tabbar,
          TabbarItem,Tab , TabItem,Scroller,Swiper,
          Marquee , MarqueeItem ,Panel ,XButton,LoadMore 
          } from 'vux'
//import mainPage from '../components/mainPage/mainPage.vue'
//import myPage from '../components/myPage/myPage.vue'
const baseList = [{
  url: 'javascript:',
  img: 'http://cquzy.cn/earth.jpg',
  title: 'Earth'
}, {
  url: 'javascript:',
  img: 'http://cquzy.cn/mars1.jpg',
  title: 'Mars'
}, {
  url: 'javascript:',
  img: 'http://cquzy.cn/saturn.jpg',
  title: 'Saturn',
}, {
  url: 'javascript:',
  img: 'http://cquzy.cn/mercury.jpg',
  title: 'Mercury',
}]
export default{
    created(){
      this.getList();
    },
    components: {
        XHeader,Group,Cell,ViewBox,Tabbar,TabbarItem,Tab,TabItem,Scroller,Swiper,Marquee , MarqueeItem,Panel ,XButton,LoadMore 
    },
    data(){
        return{
          title:"首页",
          choose: 0,
          marquee_title: "公告",
          listQuery: {
            pageNum: 1,//页码
            pageRow: 50,//每页条数
            name: ''
          },
          list: [],
          totalCount:-1,
          demo01_list: baseList,
          demo01_index: 0,
          panelList:[]
        }
    },
    methods:{
          getList() {
            //  if (!this.hasPerm('picture:list')) {
            //   console.log(111122222);
            //   return
            // }
            var _this=this;
            this.$axios.get('api/picture/listPicture' , {param:_this.listQuery})
                .then(response => {
                    _this.list=response.data.info.list;
                    _this.totalCount=response.data.info.totalCount;
                    //console.log(_this.list);
                    for(let i = 0 ; i < _this.totalCount ; i++){
                      _this.panelList.push({
                        src: _this.list[i]["imageUrl"],
                        title: _this.list[i]["car"] + "的相关信息",
                        desc: "日期:" + _this.list[i]["date1"] + "  部位:" + _this.list[i]["part"] + "  状态:" + _this.list[i]["status"],
                        url: ''
                      })
                    }
                    //console.log(_this.panelList)
                    
                    
                  })
                .catch(function (error){
                    console.log(error) ;
					          console.log(_this.totalCount);
                })
        },
        demo01_onIndexChange(index){
          //console.log(index);
          this.demo01_index = index;
        },
		
		    Logout(){
			    this.$store.dispatch('LogOut').then(() => {
				  this.$router.push({path: '/login'});
				  //console.log("logout");
			    location.reload() // 为了重新实例化vue-router对象 避免bug
			  })
		  }
		}
}


</script>

<style>
.vux-demo {
  text-align: center;
}
.logo {
  width: 100px;
  height: 100px
}
.yongyin {
     width:100px;
     text-align:center;
     line-height:100%;
     padding:0.3em;
     font:16px ;
     font-style:normal;
     text-decoration:none;
     margin:-1px;
     vertical-align:text-bottom;
     zoom:1;
     outline:none;
     font-size-adjust:none;
     font-stretch:normal;
     border-radius:50px;
     box-shadow:0px 1px 2px rgba(0,0,0,0.2);
     text-shadow:0px 1px 1px rgba(0,0,0,0.3);
     color:#fefefe;
     border:0.2px solid #2299ff;
     background-repeat:repeat;
     background-size:auto;
     background-origin:padding-box;
     background-clip:padding-box;
     background-color:#ffffff;
     background: linear-gradient(to bottom, #ffffff 0%,#ffffff 100%);
}
</style>