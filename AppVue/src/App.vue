<template>
  <div id="app" style="height:100%;">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style lang="less">
 // @import '~normalize.css/normalize.css';// normalize.css 样式格式化
 // @import './styles/index.scss'; // 全局自定义的css样式
  @import '~vux/src/styles/reset.less';
</style>
