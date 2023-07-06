<template>
<div>
  <el-container >
    <el-header style="height: 70px">
        管理系统
      <div style="float: right;margin-left: 20px">
        <el-button @click="loginOut">注销</el-button>
      </div>
        <div style="float: right">
          姓名:{{per.name}}
          角色:{{per.role}}
        </div>
    </el-header>
    <el-container style="height: 950px">
      <el-aside >
        <div>
          <el-menu router unique-opened>
            <!--这里绑定的index是路由的路径，点击可以展示相应组件-->
            <el-menu-item :index="p.path" v-for="(p) in permissions" :key="indexj">
              {{p.comment}}
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>
      <!--这里展示组件-->
      <el-main>
        <keep-alive>
          <router-view class="homeRouterView"></router-view>
        </keep-alive>

      </el-main>
    </el-container>
  </el-container>




</div>
</template>

<script>
export default {
  name: "IndexView",
  created() {
  },
  data(){
    return {
        per: JSON.parse(window.sessionStorage.getItem("permissions"))
    }
  },
  //实时计算存在vuex里面的权限，即使切换用户也可以实时计算权限
  computed:{
    permissions(){
       return this.$store.state.permissions;
    }
 },
  methods:{
    loginOut(){
      this.$axios.get("/loginOut").then((res=>{
        if(res){
          window.sessionStorage.removeItem("token");
          window.sessionStorage.removeItem("permissions");
          this.$router.replace("/login")
        }
      }))
    }
  }

}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

</style>