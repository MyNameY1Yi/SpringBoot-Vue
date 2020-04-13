<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" @click="showCreate" v-if="hasPerm('picture:add')">添加
          </el-button>

        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
       <el-table-column align = "center" label = "图片">
         <template slot-scope="scope">
            <img :src="scope.row.imageUrl" style="width: 100%" />
         </template>
      </el-table-column>

      <el-table-column align = "center" prop = "date1" label = "日期" style="width: 60px;">
      </el-table-column>

      <el-table-column align = "center" prop = "car" label = "车牌号" style="width: 60px;">

      </el-table-column>

      <el-table-column align = "center" prop = "part" label = "部位" style="width: 60px;">

      </el-table-column>

      <el-table-column align = "center" prop = "status" label = "状态" width=150>
      </el-table-column>

      <el-table-column align="center" label="管理" width="120" v-if="hasPerm('picture:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempPicture" label-position="left" label-width="80px"
               style='width: 300px; margin-left:120px;'>
        <el-form-item label="日期">
          <el-input type="text" v-model="tempPicture.date1">
          </el-input>
        </el-form-item>
        <el-form-item label="车牌号">
          <el-input type="text" v-model="tempPicture.car">
          </el-input>
        </el-form-item>
        <el-form-item label="部位">
          <el-input type="text" v-model="tempPicture.part">
          </el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-input type="text" v-model="tempPicture.status">
          </el-input>
        </el-form-item>
        <br>

        <el-upload
          ref="upload"
          class="upload-demo"
          action=""
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="1"
          :on-change="changeFile"
          :auto-upload="false"
          :on-exceed="handleExceed"
          clearFiles>
          <!-- <img id="giftImg" v-if="imageUrl" :src="imageUrl" class="avatar"> -->
          <el-button size="small" type="primary">点击上传图片</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createPicture" >创 建</el-button>
        <el-button type="primary" v-else @click="updatePicture">修 改</el-button>
      </div>
    </el-dialog>
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
        if (!this.hasPerm('picture:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/picture/listPicture",
          method: "get",
          params: this.listQuery
        }).then(data => {
          console.log(data);
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
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
              console.log(This.imageUrl);
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
          console.log(file, fileList);
      },
      handlePreview(file) {
          console.log(file);
      },
      handleExceed(files, fileList) {
          this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
          return this.$confirm(`确定移除 ${ file.name }？`);
      },
    }
  }
</script>
