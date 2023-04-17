<template>
  <div >

    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button>其他</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 30%"></el-input>
      <el-button style="margin-left: 10px" type="primary" @click="list">查询</el-button>
    </div>

    <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column
            prop="id"
            label="ID" sortable
        >
        </el-table-column>
        <el-table-column
            prop="name"
            label="家居名" >
        </el-table-column>
        <el-table-column
            prop="maker"
            label="厂家">
        </el-table-column>
        <el-table-column
            prop="price"
            label="价格">
        </el-table-column>
        <el-table-column
            prop="sales"
            label="销量">
        </el-table-column>
        <el-table-column
            prop="stock"
            label="库存">
        </el-table-column>

      <el-table-column fixed="right" label="操作" width="100">
        <template #default="scope">
          <el-button  @click="handleEdit2(scope.row)" type="text">编辑</el-button>

          <el-popconfirm
              title="确定删除吗？" @confirm="handleDel(scope.row.id)">
            <template #reference>
              <el-button size="mini" type="text">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handlePageSizeChange" @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5,10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules" ref="form" label-width="120px">
        <el-form-item label="家居名" prop="name">
          <el-input v-model="form.name" style="width: 80%"></el-input>
          {{this.validMsg.name}}
        </el-form-item>
        <el-form-item label="厂商" prop="maker">
          <el-input v-model="form.maker" style="width: 80%"></el-input>
          {{this.validMsg.maker}}
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" style="width: 80%"></el-input>
          {{this.validMsg.price}}
        </el-form-item>
        <el-form-item label="销量" prop="sales">
          <el-input v-model="form.sales" style="width: 80%"></el-input>
          {{this.validMsg.sales}}
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="form.stock" style="width: 80%"></el-input>
          {{this.validMsg.stock}}
        </el-form-item>
      </el-form>
      <template #footer>
<span class="dialog-footer">
<el-button @click="dialogVisible = false">取 消</el-button>
<el-button type="primary" @click="save">确 定</el-button>
</span>
      </template>
    </el-dialog>
</div>

</template>

<script>
// @ is an alias to /src
import request from "@/utils/request";

export default {
  name: 'HomeView',
  components: {

  },
  data(){
    return{
      validMsg:{},
      currentPage:1,
      pageSize:3,
      total:10,
      form: {},
      dialogVisible: false,
      search:'',
      tableData: [],
      rules: {
        name: [
          {required: true, message: '请输入称家居名', trigger: 'blur'}
        ],maker: [
          {required: true, message: '请输入称制造商', trigger: 'blur'}
        ],price: [
          {required: true, message: '请输入价格', trigger: 'blur'}, {pattern: /^(([1-9]\d*)|(0))(\.\d+)?$/, message: '请输入数字', trigger: 'blur'}
        ],sales: [
          {required: true, message: '请输入销量', trigger: 'blur'}, {pattern: /^(([1-9]\d*)|(0))$/, message: '请输入数字', trigger: 'blur'}
        ],stock: [
          {required: true, message: '请输入库存', trigger: 'blur'}, {pattern: /^(([1-9]\d*)|(0))$/, message: '请输入数字', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.list()
  },
  methods:{

    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.list()
    },

    handlePageSizeChange(pageSize){
      this.pageSize = pageSize
      this.list()
    },

    handleDel(id){
      request.delete("/api/del/" + id).then(res => {
        if(res.code == "200"){
          this.$message({
            type:"success",
            message:"删除成功"
              })
        }else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
      this.list()
    },

    list(){
      // request.get("/api/furns").then(res => {
      //   this.tableData = res.data
      // })
      request.get("/api/furnsBySearchPage",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save(){
      if (this.form.id){
        request.put("/api/update",this.form).then(
            res => {
              if (res.code == "200"){
                this.$message({
                  type:"success",
                  message:"更新成功"
                })
              }else {
                this.$message({
                  type:"error",
                  message:"更新失败"
                })
              }
              this.list()
              this.dialogVisible = false
            }

        )
      }else {
      // request.post("/api/save",this.form).then(
      //     res => {
      //       console.log(res)
      //       this.dialogVisible = false
      //       this.list()
      //     }
      // )
        this.$refs['form'].validate((valid) =>{
          if(valid){
            request.post("/api/save",this.form).then(
                res => {

                  if (res.code == "200"){
                    this.dialogVisible = false
                    this.list()
                  }else if (res.code == "400"){
                    this.validMsg.name = res.data.name
                    this.validMsg.maker = res.data.maker
                    this.validMsg.price = res.data.price
                    this.validMsg.sales = res.data.sales
                    this.validMsg.stock = res.data.stock

                  }


                }
            )
          }else {
            this.$message({
              type:"error",
              message:"验证失败，不提交"
            })
            return false;
          }
        })
      }
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true

    },
    handleEdit2(row){
      request.get("/api/select/" +row.id).then(res => {
        this.form = res.data
      })
      this.dialogVisible = true
    },
    add(){
      this.dialogVisible = true
      this.form = {}
      this.$refs['form'].resetFields()
      this.validMsg = {}
    }
  }
}
</script>
