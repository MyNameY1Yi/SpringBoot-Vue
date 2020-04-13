<template>
    <div style="height:100%">
         <view-box>
            <x-header slot="header" :left-options="{showBack:true}">
              <div>{{title}}</div>
                <div slot="right">
                    <x-button mini :plain="true" type="warn" @click.native="Logout" style="margin-top:-6px">注销</x-button>
                </div> 
            </x-header>
            <transition name = "fade">
            <div v-if="hasPermission == -1">
                <br><br>
                <load-more :tip='"正在加载"'></load-more>
            </div>
            <div v-if="hasPermission == 1">
                <!-- <van-notice-bar
                color="#1989fa"
                background="#ecf9ff"
                left-icon="success"
                >
                    您已经拥有了访问权限！
                </van-notice-bar> -->
                <!-- <van-cell-group>
                     <van-cell title="单元格" icon="location-o" right-icon="arrow"/> 
                     <van-switch-cell v-model="hasPermission" title="删除权限" @click.native="delPermission"/> 
                     <van-button plain hairline type="primary" style="float: right;">删除权限</van-button> 
                     <van-button plain hairline type="primary" @click.native="delPermission">删除权限</van-button> 
                </van-cell-group> -->
                <van-cell title="您已经拥有了访问权限"  icon="passed" :center="true">
                <!-- 使用 title 插槽来自定义标题 -->
                    <template slot="right-icon">
                        <!-- <span class="custom-title">单元格</span>
                        <van-tag type="danger">标签</van-tag> -->
                        <van-button icon="delete" @click.native="delPermission">点击删除</van-button>
                    </template>
                </van-cell>
            </div>

            <div v-if="hasPermission == 0">
                <!-- <van-notice-bar
                color="#1989fa"
                background="#ecf9ff"
                left-icon="info-o"
                >
                    您尚未获得权限，请输入邀请码获取。
                </van-notice-bar> -->
                <van-cell title="您尚未获得权限，请输入邀请码获取。"  icon="info-o" :center="true">
                </van-cell>
                <van-form  @submit="OnSubmit">
                    <van-field 
                        :name="id"
                        v-model="id"
                        label="邀请码"
                        placeholder="在此处输入"
                        :rules="[{required:true , message:'邀请码不能为空'}]"    
                    />
                    <div style="margin: 16px;">
                        <van-button round block type="info" native-type="submit">
                                提交
                        </van-button>
                    </div>
                </van-form>
            </div>
            
            </transition>

            <tabbar slot="bottom" style="position:fixed">
            <tabbar-item >
              <img src='../../../assets/策略.png' slot="icon">
              <span slot="label">推荐</span>
            </tabbar-item>
            <tabbar-item  link="/">
              <img src='../../../assets/房子.png' slot="icon">
              <span slot="label">首页</span>
            </tabbar-item>
            <tabbar-item link="/myPage" selected>
              <img src='../../../assets/用户.png' slot="icon">
              <span slot="label">我的</span>
            </tabbar-item>
          </tabbar>
        </view-box>
    </div>
</template>
<script>
import { Group, Cell, XHeader,ViewBox,Tabbar,XInput,
          TabbarItem,Tab , TabItem,Scroller,Swiper,
          Marquee , MarqueeItem ,Panel ,XButton,LoadMore ,FormPreview,XDialog,TransferDomDirective as TransferDom
          } from 'vux'
export default{
    components:{
        XInput,XDialog,FormPreview,Group, Cell, XHeader,ViewBox,Tabbar,TabbarItem,Tab , TabItem,Scroller,Swiper,Marquee , MarqueeItem ,Panel ,XButton,LoadMore,
    },
    created(){
        this.getUserAndPermission() ;
    },
    data(){
        return{
            user:'',
            id:"",
            function:"train",
            title:"火车检测",
            hasPermission:-1,
            permissions:{}
        }
    },
    methods:{
        getUserAndPermission(){
          var _this = this ;

          this.$store.dispatch("GetInfo").then(data =>{
            //console.log("GetInfo" , data);
            this.user=data.userPermission.username;
            
            //console.log({user:_this.user}) ;
            this.$axios.post("/api/function/listFunction" , {user:_this.user})
                .then(response => {
                    _this.permissions = response.data.info;
                    //console.log("getPermission" , response) ;
                    //console.log("permissions" , _this.permissions) ;
                    //console.log(_this.permissions[_this.function]) ;
                    _this.hasPermission = _this.permissions[_this.function] ;
                    // if (_this.hasPermission){
                    //     this.$message({
                    //         message: '权限验证成功!',
                    //         center: true,
                    //         duration: 1200,
                    //         type: "success"
                    //     });
                    // }
                })
          })
        },
        Logout(){
			    this.$store.dispatch('LogOut').then(() => {
				  this.$router.push({path: '/login'});
				  //console.log("logout");
			    location.reload() // 为了重新实例化vue-router对象 避免bug
              })
        },
        OnSubmit(value){
            var _this = this ;
            //console.log("submit" , {user:_this.user , functionName:_this.function , id:_this.id}) ;
            this.$axios.post("/api/function/addFunction" , 
                {user:_this.user , functionName:_this.function , id:_this.id})
                .then(response => {
                    //console.log(response) ;
                    if (response.data.msg == "邀请码错误"){
                        this.$message({
                            message: '邀请码错误!',
                            center: true,
                            duration: 1200,
                            type: "warning"
                        });
                    }else{
                        if (response.data.msg == "请求成功"){
                            window.location.reload();
                            //this.$router.push({path: '/trainInfo'});
                        }
                    }
                })
        },
        delPermission(){
            console.log(1) ;
            var _this = this ;
            this.$axios.post("api/function/delFunction" , 
                {user:_this.user , functionName:_this.function})
                .then(response => {
                    //console.log(response);
                    window.location.reload();
                })
        }
    }
}    
</script>