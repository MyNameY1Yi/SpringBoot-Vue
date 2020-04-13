<template>
    <div style="height:100%;">
        <view-box>
            <x-header slot="header" :left-options="{showBack:true}">
              <div>{{title}}</div>
                <div slot="right">
                    <x-button mini :plain="true" type="warn" @click.native="Logout" style="margin-top:-6px">注销</x-button>
                </div> 
            </x-header>
        
        <load-more :tip='"正在加载"' v-if="totalCount==-1"></load-more>
        <group >
          <cell v-for="(item,index) in list" :title="item.sname" is-link :key="index" @click.native="cellClick(item)"></cell>
        </group>

        <div v-transfer-dom>

          <FloatBall :text="'十'" @click.native="iclick()"></FloatBall>

          <van-dialog v-model="dialogFormVisible" title="添加学生" :show-confirm-button='false' :show-cancel-button='false' :close-on-click-overlay='true'>
            <br>
            <van-form @submit="onSubmit">
              <van-field
                v-model="tempStudent.sname"
                name="sname"
                label="学生姓名"
                placeholder="学生姓名"
                :rules="[{ required: true, message: '学生姓名' }]"
              />
              <van-field
                v-model="tempStudent.snum"
                name="snum"
                label="学号"
                placeholder="学号"
                :rules="[{ required: true, message: '请填写学号' }]"
              />
              <van-field
                v-model="tempStudent.school"
                name="school"
                label="学校"
                placeholder="学校"
                :rules="[{ required: true, message: '请填写学校' }]"
              />
              <br>
              <div style="margin: 16px;">
                <van-button round block type="info" native-type="submit">
                  提交
                </van-button>
              </div>
            </van-form>
          </van-dialog>
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
import { Group, Cell, XHeader,ViewBox,Tabbar,XInput,
          TabbarItem,Tab , TabItem,Scroller,Swiper,
          Marquee , MarqueeItem ,Panel ,XButton,LoadMore ,FormPreview,XDialog,TransferDomDirective as TransferDom
          } from 'vux'
import FloatBall from '../../FloatBall/FloatBall.vue';
export default{
    created(){
      this.getUser();
      this.getList();
      // window.location.reload();
    },
    directives: {
      TransferDom
    },
     data(){
        return{
          listQuery: {
            pageNum: 1,//页码
            pageRow: 50,//每页条数
            name: ''
          },
          user:'',
          tempStudent:{
            user:'',
            sname: '',
            snum: '',
            school:'',
          },
          dialogFormVisible:false,
          list:[],
          totalCount:-1,

            now:0,
            title:"我的学生",
        }
    },    
    components:{
        FloatBall,XInput,XDialog,FormPreview,Group, Cell, XHeader,ViewBox,Tabbar,TabbarItem,Tab , TabItem,Scroller,Swiper,Marquee , MarqueeItem ,Panel ,XButton,LoadMore,
    },
    methods:{
        getList(){
          var _this = this;
          // if (!this.hasPerm('students:list')) {
          //      window.location.reload();
          //      return
          //    }
          console.log("Getting list......");
          this.$axios.get("/api/students/listStudents")
            .then(response => {
              
              //console.log("studets" , response);
              _this.list=response.data.info.list;
              _this.totalCount=response.data.info.totalCount;
              if (_this.list==undefined){
                window.location.reload();
              }
              //console.log("students" , _this.list);
            })
        },
        getUser(){
          this.$store.dispatch("GetInfo").then(data =>{
            //console.log(data);
            this.user=data.userPermission.username;
            //console.log(this.user);
          })
        },
        cellClick(item){
          this.$router.push({path:'/students/sinfo' , query:{student:JSON.stringify(item)}});
        },
        Logout(){
			    this.$store.dispatch('LogOut').then(() => {
				  this.$router.push({path: '/login'});
				  //console.log("logout");
			    location.reload() // 为了重新实例化vue-router对象 避免bug
              })
        },
        iclick(){
          //console.log('1');
          this.dialogFormVisible=true;
          //console.log('1');
          //alert('asdasd');
        },
        onSubmit(values) {
          values.user=this.user;
          console.log(values);
          var _this = this;
          this.dialogFormVisible=false;
          this.$axios.post('api/students/addStudents' , values)
              .then(() => {
                this.$message({
                  message: '添加成功!',
                  center: true,
                  duration: 1800,
                  type: "success"
                });
               this.getList();
               this.tempStudent.snum='';
               this.tempStudent.school='';
               this.tempStudent.sname='';
               this.dialogFormVisible = false;
             })
          
        },
    }
}
</script>

<style lang="less" scoped>
@import '~vux/src/styles/close';

.dialog-demo {
  .weui-dialog{
    border-radius: 8px;
    padding-bottom: 8px;
    width: 35%;
  }
  .dialog-title {
    line-height: 30px;
    color: #666;
  }
  .img-box {
    height: 350px;
    overflow: hidden;
  }
  .vux-close {
    margin-top: 4px;
    margin-bottom: px;
  }
}
</style>