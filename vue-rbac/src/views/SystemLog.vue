<template>
  <div>
    <div>
      <el-table
          :data="logForm"
          border
          style="width: 100%">
        <el-table-column
            prop="username"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="role"
            label="角色"
            width="180">
        </el-table-column>
        <el-table-column
            prop="operate"
            label="操作">
        </el-table-column>
        <el-table-column
            prop="spendTime"
            label="花费时间">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间">
        </el-table-column>
      </el-table>
    </div>
    <div style="display: flex;justify-content: flex-end;height: 40px;margin-top: 60px">
      <el-pagination
          background
          :page-size="size"
          layout="prev,pager,next,jumper,->,total"
          @current-change="currentChange"
          :total="total">
      </el-pagination>
    </div>

  </div>

</template>

<script>
export default {
  created() {
    this.initLogForm();
  },
  data() {
    return {
      logForm: [],
      size:10,
      total:0,
      currentPage:1
    }
  },
  methods:{
    initLogForm(){
      this.$axios.get("/sysLog?currentPage="+this.currentPage+"&size="+this.size).then((res=>{
        if(res){
          this.total = res.data.data.total;
          this.logForm = res.data.data.list;
        }
      }))
    },
    currentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage=val;
      this.initLogForm();
    }
  }
}
</script>