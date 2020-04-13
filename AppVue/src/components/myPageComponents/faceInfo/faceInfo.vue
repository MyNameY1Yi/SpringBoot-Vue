<template>
    <div style="height:100%">
        <view-box>
            <x-header slot="header" :left-options="{showBack:true}">
              <div>{{title}}</div>
                <div slot="right">
                    <x-button mini :plain="true" type="warn" @click.native="Logout" style="margin-top:-6px">注销</x-button>
                </div> 
            </x-header>
            <!-- <input ref="file" type="file" accept="image/*" capture="user"> -->

            <div v-if="status==0">
                <van-cell title="请填写个人信息"  icon="manager-o" :center="true"></van-cell>

                <van-form>
                    <van-field
                        v-model="number"
                        label="工号"
                        placeholder="请输入工号"
                        :rules="[{ required: true, message: '请填写工号' }]"
                    />

                    <van-field  
                        label="照片" 
                        :rules="[{ required: true, message: '请添加照片' }]">
                        <template #input>
                            <!-- <video id="video" width="318" height="270" preload autoplay loop muted></video>
                            <canvas id="canvas" width="318" height="270" ></canvas>
                             <button type="button" @click="submit">上传照片</button>
                            <button type="button" name="button" @click="openCamera">点击我拍照</button> -->
                             <van-uploader :after-read="afterRead" image-fit="contain" preview-size="215px" accept="image/*" v-model="uploader" :max-count="1"/> 
                        </template>
                    </van-field>
                    <div style="text-align:center">
                    <img :src="imgfile" v-show="false" id="ig"/>    
                    </div>

                     <div style="margin: 16px;" v-if="exist == 0">
                        <van-button round block type="info" native-type="submit" @click.native = "testExist">
                        人脸检测
                        </van-button>
                    </div> 
                    
                    <div style="margin: 16px;" v-else>
                        <van-button round block type="info" native-type="submit" @click.native = "onSubmit">
                        提交
                        </van-button>
                    </div>
                </van-form>
            </div>

            <div v-if="status==1">
                <van-cell title="提交成功，您的信息正在审核中 ！"  icon="clock-o" :center="true">
                    <template slot="right-icon">
                        <van-button icon="delete" @click.native="cancelApply">点击撤销</van-button>
                    </template>
                </van-cell>
            </div>


            <div v-if="status==2">
                <van-cell title="审核已通过 !" icon="passed" :center="true">
                    <template slot="right-icon">
                        <van-button icon="delete" @click.native="cancelPassedApply">点击删除</van-button>
                    </template>
                </van-cell>
            </div>
            
            

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
  require('tracking/build/tracking-min.js')
    require('tracking/build/data/face-min.js')
//       require('tracking/build/data/mouth-min.js')
//    require('tracking/examples/assets/stats.min.js')
import store from "../../../store"
import { Group, Cell, XHeader,ViewBox,Tabbar,XInput,
          TabbarItem,Tab , TabItem,Scroller,Swiper,
          Marquee , MarqueeItem ,Panel ,XButton,LoadMore ,FormPreview,XDialog,TransferDomDirective as TransferDom
          } from 'vux'
const output=[3];
output[0] = "无记录"
output[1] = "待审核"
output[2] = "已通过"

export default{
    components:{
        XInput,XDialog,FormPreview,Group, Cell, XHeader,ViewBox,Tabbar,TabbarItem,Tab , TabItem,Scroller,Swiper,Marquee , MarqueeItem ,Panel ,XButton,LoadMore,
    },
    data(){
        return { 
            exist:0,
            status:-1,
            user:"",
            imgfile:"",
            sms:'',
            rate:3,
            title:"人脸识别",   
            number:"",
            uploader:[],
            statusString:output,
            time:0
        }
    },
    created(){
        this.getUser() ;
    },
    methods:{

        afterRead(){    
            this.imgfile = this.uploader[0].content;
            this.exist = 0 ;
        },
        testExist(){
            let img = document.getElementById('ig');
            // console.log("dy") ;
            //console.log(img)
            var tracker = new tracking.ObjectTracker('face');
            tracker.setInitialScale(4) ;
            tracker.setStepSize(2) ;
            tracker.setEdgesDensity(0.1) ;
            tracking.track(img, tracker);
            //console.log(tracker) ;
            var _this = this ;
            //console.log(22222) ;
            tracker.on("track" , function(event) {
               // console.log(22222) ;
                _this.exist = event.data.length;
                console.log(_this.exist);
                if (_this.exist == 0){
                 _this.$toast.fail({
                     duration:3000,
                     message: '未检测到人脸，请重新上传照片 !',
                     forbidClick: false,
                     //loadingType: 'spinner'
                    });
                }
                else if (_this.exist >= 1){
                    _this.$toast.success({
                        duration:3000,
                        message: "成功检测到人脸 可以继续提交 !",
                        forbidClick: true,
                        //loadingType: 'spinner'
                    });
                }
            });

             
        },
        Logout(){
			this.$store.dispatch('LogOut').then(() => {
				this.$router.push({path: '/login'});
			    location.reload() // 为了重新实例化vue-router对象 避免bug
            })
        },
        onSubmit(values){
            var _this=this ;
            var params = {user:_this.user , number:_this.number , faceImg:_this.imgfile} ;
            console.log("add params" , params) ;
            _this.$toast.loading({
                duration:15000,
                message: '正在提交...',
                forbidClick: true,
                //loadingType: 'spinner'
                });
             this.$axios.post("/api/faceInfo/addfaceInfo" , params)
                 .then(data => {
                     console.log("add:" , data) ;
                     window.location.reload() ;
                 })
        },
        getList(){
            var _this=this ;
            this.$axios.post("/api/faceInfo/listfaceInfo" , {user:_this.user})
                .then(data => {
                    _this.status = data.data.info[0].status ;
                    console.log("status:" , _this.statusString[_this.status]) ;
                })
        },
        getUser(){
            this.user = store.getters.username;
            console.log("username:" , this.user);
            this.getList() ;
        },
        cancelApply(){
            this.$toast.loading({
                duration:15000,
                message: '正在撤销...',
                forbidClick: true,
                //loadingType: 'spinner'
                });
            var _this = this ;
            this.$axios.post('/api/faceInfo/cancelApply' , {user:_this.user})
                .then(response => {
                    console.log(response) ;
                    window.location.reload() ;
                })
        },
        cancelPassedApply(){
            this.$toast.loading({
                duration:15000,
                message: '正在删除...',
                forbidClick: true,
                //loadingType: 'spinner'
                });
            var _this = this ;
            this.$axios.post('/api/faceInfo/cancelPassedApply' , {user:_this.user})
                .then(response => {
                    console.log(response) ;
                    window.location.reload() ;
                })
        },
    }
}
</script>

<style lang="less" scoped>
  #photo:hover .rect {
    opacity: .75;
    transition: opacity .75s ease-out;
  }

  .rect:hover * {
    opacity: 1;
  }

  .rect {
    border-radius: 2px;
    border: 3px solid white;
    box-shadow: 0 16px 28px 0 rgba(0, 0, 0, 0.3);
    cursor: pointer;
    left: -1000px;
    opacity: 0;
    position: absolute;
    top: -1000px;
  }

  .arrow {
    border-bottom: 10px solid white;
    border-left: 10px solid transparent;
    border-right: 10px solid transparent;
    height: 0;
    width: 0;
    position: absolute;
    left: 50%;
    margin-left: -5px;
    bottom: -12px;
    opacity: 0;
  }

  input {
    border: 0px;
    bottom: -42px;
    color: #a64ceb;
    font-size: 15px;
    height: 30px;
    left: 50%;
    margin-left: -90px;
    opacity: 0;
    outline: none;
    position: absolute;
    text-align: center;
    width: 180px;
    transition: opacity .35s ease-out;
  }

  #img {
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -173px 0 0 -300px;
  }
  </style>