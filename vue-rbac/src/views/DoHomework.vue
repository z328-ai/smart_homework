<template>
  <div>
    <div class="admin-container" style="line-height: 100px;font-size: 19px">
      <el-card class="box-card" v-for="h in homeworks" >
        <div slot="header" class="clearfix" >
          <span >{{h.name}}</span>
          <el-button style="float: right; padding: 3px 0" type="text" v-if="h.content">已做作业</el-button>
          <el-button style="float: right; padding: 3px 0" type="text" v-else @click="doHomework(h)">未做作业</el-button>
          <el-button style="float: left; padding: 3px 0" type="text" v-if="h.status==1">已批改</el-button>
          <el-button style="float: left; padding: 3px 0" type="text" v-else>未批改</el-button>
        </div>
        <div class="userinfo-container">
        </div>
        <div>作业要求</div>
        <span >
        {{h.ask}}
      </span>
      </el-card>
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
  data(){
    return{
      formLabelWidth: '120px',
      total: 0,
      currentPage: 1,
      size: 4,
      homeworks:[],
      homework:{},
    }
  },
  created() {
    this.initHomework()
  },
  methods:{
    //分页查询所有的作业
    initHomework(){
      this.$axios.get("/homework/getAllHomework?currentPage="+this.currentPage+"&size="+this.size).then((res=>{
        if(res){
          this.total = res.data.data.total;
          this.homeworks = res.data.data.list;
          console.log(res.data.data)
        }
      }))
    },
    //当分页的页数改变时触发
    currentChange(val) {
      this.currentPage=val;
      this.initHomework();
    },
    //做作业
    doHomework(h){
      this.$prompt('请输入内容', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        h.content = value;
          this.$axios.post("/doHomework/DoHomework",h).then((res=>{
            if(res){
              this.initHomework();
            }
          }))
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    }
  }
}
</script>

<style scoped>
.admin-container{
  display: flex;
  margin-top: 10px;
  justify-content: space-around;
  flex-wrap: wrap;
  height: 750px;
}
</style>