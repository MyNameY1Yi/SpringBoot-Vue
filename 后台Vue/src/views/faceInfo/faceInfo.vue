<template>
  <div class="app-container">
    <!-- <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" @click="showCreate" v-if="hasPerm('picture:add')">添加
          </el-button>

        </el-form-item>
      </el-form>
    </div> -->
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>

        <el-table-column align = "center" prop = "user" label = "申请人" style="width: 60px;">
      </el-table-column>

       <el-table-column align = "center" label = "人脸信息">
         <template slot-scope="scope">
            <img :src="scope.row.faceImg" style="width: 100%" />
         </template>
      </el-table-column>

      <el-table-column align = "center" prop = "number" label = "工号" style="width: 60px;">
      </el-table-column>

      <el-table-column align = "center" prop = "create_time" label = "申请时间" width=150>
      </el-table-column>

      <el-table-column align="center" label="管理" width="120" v-if="hasPerm('picture:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="pass(scope.$index)">通过</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
          name: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '创建信息'
        },
        tempPicture: {
          date1 : "" ,
          part : "" ,
          status : "" ,
          car : "" ,
          imageUrl : "" ,
        }
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        //查询列表
        if (!this.hasPerm('faceInfo:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/faceInfo/listAllfaceInfo",
          method: "post",
          //params: this.listQuery
        }).then(response => {
          //console.log("response" , response);
          this.listLoading = false;
          this.list = response;
          
          this.totalCount = response.totalCount;
          console.log(this.list) ;
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val
        this.handleFilter();
      },
      changeFile(file) {
          var This = this;
          //this.imageUrl = URL.createObjectURL(file.raw);
          var reader = new FileReader();
          reader.readAsDataURL(file.raw);
          reader.onload = function(e){
             // this.result // 这个就是base64编码了
              This.tempPicture.imageUrl = this.result;
              //console.log(This.imageUrl);
          }
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempPicture.date1 = "";
        this.tempPicture.car = "";
        this.tempPicture.part = "";
        this.tempPicture.status = "";
        this.tempPicture.imageUrl = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempPicture.date1 = this.list[$index].date1;
        this.tempPicture.car = this.list[$index].car;
        this.tempPicture.part = this.list[$index].part;
        this.tempPicture.status = this.list[$index].status;
        this.tempPicture.imageUrl = this.list[$index].imageUrl;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createPicture() {
        //保存新文章
        this.api({
          url: "/picture/addPicture",
          method: "post",
          data: this.tempPicture
        }).then(() => {
          this.getList();
          this.$refs.upload.clearFiles();
          this.dialogFormVisible = false
          //this.$refs['my-upload'].upload.clearFiles();
        })
      },
      updatePicture() {
        //修改文章
        this.api({
          url: "/picture/updatePicture",
          method: "post",
          data: this.tempPicture
        }).then(() => {
          this.getList();
          this.$refs.upload.clearFiles();
          this.dialogFormVisible = false

        })
      },
      handleRemove(file, fileList) {
          //console.log(file, fileList);
      },
      handlePreview(file) {
          //console.log(file);
      },
      handleExceed(files, fileList) {
          this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
          return this.$confirm(`确定移除 ${ file.name }？`);
      },
      pass($index){
          console.log(this.list[$index]) ;
          this.api({
          url: "/faceInfo/passfaceInfo",
          method: "post",
          data: this.list[$index] 
            }).then(response => {
                console.log(response) ;
                window.location.reload() ;
        })
      }
    }
  }
</script>
