<template>
	<div style="height:100%;">
				
				<scroller :lock-y='true'>
		            <div>
		              <tab :line-width="1" custom-bar-width="60px">
		                <tab-item selected>推荐</tab-item>
		                <tab-item>资讯</tab-item>
		                <tab-item>科技</tab-item>
		                <tab-item>娱乐</tab-item>
		                <tab-item>体育</tab-item>
		                <tab-item v-if="this.list.length">{{list[0]["date1"]}}</tab-item>
		              </tab>
		            </div>
		          </scroller>
		
		          
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
	</div>
</template>

<script>
import { Group, Cell, XHeader,ViewBox,Tabbar,
          TabbarItem,Tab , TabItem,Scroller,Swiper,
          Marquee , MarqueeItem ,Panel ,XButton,LoadMore 
					} from 'vux'
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
    components: {
        XHeader,Group,Cell,ViewBox,Tabbar,TabbarItem,Tab,TabItem,Scroller,Swiper,Marquee , MarqueeItem,Panel ,XButton,LoadMore 
    },
    created() {
      this.getList() ;
      //this.init() ;
    },
    data(){
        return{
          marquee_title: "公告",
          totalCount:-1,
          listQuery: {
            pageNum: 1,//页码
            pageRow: 50,//每页条数
            name: ''
          },
          list: [],
          demo01_list: baseList,
          demo01_index: 0,
          panelList:[]
        }
    },
    methods:{
        getList() {
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
        // init() {
        //   console.log(this.totalCount + 22222);
          
        // },
        demo01_onIndexChange(index){
          //console.log(index);
          this.demo01_index = index;
        },
		
		Logout(){
			this.$store.dispatch('LogOut').then(() => {
				this.$router.push({path: '/'});
				//console.log("logout");
			  location.reload() // 为了重新实例化vue-router对象 避免bug
			})
		}
    }
}


</script>
