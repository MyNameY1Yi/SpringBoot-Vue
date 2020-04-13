<template>
    <div style="height:100%;">
        <view-box>
            <x-header slot="header" :left-options="{showBack:true}">
              <div>详细信息</div>
                <!-- <div slot="right">
                    <x-button mini type="warn" @click.native="Logout" style="margin-top:-6px">注销</x-button>
                </div>  -->
            </x-header>
            <divider>个人资料</divider>
                <x-table>
                    <!-- <thead>
                    <tr>
                        <th>学生资料</th>
                        <th>Price</th>
                    </tr>
                    </thead> -->
                    <tbody>
                    <tr>
                        <td>姓名</td>
                        <td>{{student.sname}}</td>
                    </tr>
                    <tr>
                        <td>学校</td>
                        <td>{{student.school}}</td>
                    </tr>
                    <tr>
                        <td>学号</td>
                        <td>{{student.snum}}</td>
                    </tr>
                    </tbody>
                </x-table>
                <br>
                <x-button type="primary">管理</x-button>

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
import { Divider ,XTable ,Group, Cell, XHeader,ViewBox,Tabbar,XInput,
          TabbarItem,Tab , TabItem,Scroller,Swiper,
          Marquee , MarqueeItem ,Panel ,XButton,LoadMore ,FormPreview,XDialog,TransferDomDirective as TransferDom
          } from 'vux'

    export default{
        created(){
          this.student=JSON.parse(this.$route.query.student);  
        },
        date(){
            return{
                title:"学生资料",
            }
        },
        components:{
            Divider ,XTable,XInput,XDialog,FormPreview,Group, Cell, XHeader,ViewBox,Tabbar,TabbarItem,Tab , TabItem,Scroller,Swiper,Marquee , MarqueeItem ,Panel ,XButton,LoadMore,
    },
    }
</script>