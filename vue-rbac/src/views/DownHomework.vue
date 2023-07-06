<template>
  <div>
    <div style="width: 900px;height: 900px;float: left" >
      <div class="admin-container" style="line-height: 30px;font-size: 19px">
        <el-card class="box-card" v-for="h in homeworks" >
          <div slot="header" class="clearfix" >
            <span @click="showAllDoHomework(h.name)">{{h.name}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="deleteHomework(h.id)">删除作业</el-button>
            <el-button style="float: left; padding: 3px 0" type="text" @click="openUpdateHomework(h.id)">修改作业</el-button>
          </div>
          <div class="userinfo-container">
          </div>
          <div>作业要求</div>
          <span >
        {{h.ask }}
      </span>
        </el-card>
      </div>
      <div style="float: left">
        <el-button type="primary" @click="downHomework">布置作业</el-button>
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
    <div style="float: left">
      <el-divider direction="vertical"></el-divider>
    </div>
<!--   作业详细-->
    <div style="width: 900px;height: 900px;float: left">
      <div class="admin-container" style="line-height: 30px;font-size: 19px">
        <el-card class="box-card" v-for="d in doHomeworks">
          <div slot="header" class="clearfix" >
            <span>{{d.studentName}}</span>
            <el-popover
                placement="top-start"
                title="作业内容"
                width="200"
                trigger="hover"
                :content="d.content">
            <el-button style="float: right; padding: 3px 0" type="text" slot="reference">查看作业</el-button>
            </el-popover>
            <el-switch
                active-text="已批改"
                @change="changeStatus(d.status1,d.id)"
                v-model="d.status1" style="float: left">
            </el-switch>

          </div>
          <div class="userinfo-container">
          </div>
          <div>作业内容</div>
          <span >
        {{d.content}}
      </span>
        </el-card>
      </div>
      <div style="display: flex;justify-content: flex-end;height: 40px;margin-top: 60px">
        <el-pagination
            background
            :page-size="doSize"
            layout="prev,pager,next,jumper,->,total"
            @current-change="doCurrentChange"
            :total="doTotal">
        </el-pagination>
      </div>
    </div>


<!--    布置作业或修改作业弹窗-->
    <el-dialog
        :title="operate"
        :visible.sync="openHomework"
        width="30%"
        :before-close="handleClose">
      <el-form :model="homework">
        <el-form-item label="作业" :label-width="formLabelWidth">
          <el-input v-model="homework.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="作业要求" :label-width="formLabelWidth">
          <el-input v-model="homework.ask" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="openHomework = false">取 消</el-button>
    <el-button type="primary" @click=submitHomework(homework.id)>确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data(){
    return {
      formLabelWidth: '120px',
      total: 0,
      currentPage: 1,
      size: 4,
      homeworks:[],
      homework:{},
      openHomework: false,
      operate:'',
      //-----------------------------
      doTotal: 1,
      doCurrentPage: 1,
      doSize: 4,
      doHomeworks:[],
      doHomework:{},
    }
  },
  created() {
    this.initHomework();
  },
  methods:{
    //分页查询所有的作业
    initHomework(){
      this.$axios.get("/homework/getAll?currentPage="+this.currentPage+"&size="+this.size).then((res=>{
        if(res){
          this.total = res.data.data.total;
          this.homeworks = res.data.data.list;
        }
      }))
    },
    //当分页的页数改变时触发
    currentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage=val;
      this.initHomework();
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    //弹出修改框，并且进行数据回显
    openUpdateHomework(hid){
      this.$axios.get("/homework/getSingleById?hid="+hid).then((res=>{
        if(res){
          this.operate='修改作业';
          this.openHomework =true;
          this.homework = res.data.data;
        }
      }))

    },
    //布置作业
    downHomework(){
      //修改弹出框标题
      this.operate="布置作业";
      this.openHomework=true;
      //清空homework的数据防止残留进行修改操作后的数据
      this.homework={};

    },
    //根据是否传入id判断进行修改作业还是布置作业
    submitHomework(hid){
      //存在就行修改操作,不存在就添加
      if(hid){
        this.$axios.put("/homework/updateHomework",this.homework).then((res=>{
          if(res){
            this.openHomework=false;
            this.initHomework();
          }
        }))
      }else {
        this.$axios.post("/homework/addHomework",this.homework).then((res=>{
             if(res){
               this.openHomework=false;
               this.initHomework();
             }
        }))
      }
    },
    //通过id删除作业
    deleteHomework(hid){
      this.$axios.delete("/homework/deleteHomework?hid="+hid).then((res=>{
        if(res){
          this.initHomework();
        }
      }))
    },

  //  ----------------------------------
  //  点击展示该类作业所有提交情况
    showAllDoHomework(homeworkName){
      this.initDoHomework(homeworkName);
    },
    //分页查询所有提交作业
    initDoHomework(homeworkName){
      this.$axios.get("/doHomework/getAll?currentPage="+this.doCurrentPage+"&size="+this.doSize+"&homeworkName="+homeworkName).then((res=>{
        if(res){
          this.doTotal = res.data.data.total;
          this.doHomeworks = res.data.data.list;
        }
      }))
    },
    //当分页的页数改变时触发
    doCurrentChange(val){
      console.log(`当前页: ${val}`);
      this.doCurrentPage=val;
      this.initDoHomework();
    },
  //  批改作业
    changeStatus(status1,did){
      if(status1 == true){
        this.$axios.get("/doHomework/updateDoHomeworkStatus?status="+1+"&did="+did);
      }else{
        this.$axios.get("/doHomework/updateDoHomeworkStatus?status="+0+"&did="+did);
      }

    }
  }
}
</script>

<style scoped>
.el-divider--vertical{
  width: 10px;
  height: 900px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}
.clearfix{
  font-size: 30px;
  height: 50px;
}

.box-card {
  width: 350px;
  height: 350px;
  margin-bottom: 50px;
}
.admin-container{
  display: flex;
  margin-top: 10px;
  justify-content: space-around;
  flex-wrap: wrap;
  height: 750px;
}
.userinfo-container{
  font-size: 30px;
  color: #333333;
}

</style>