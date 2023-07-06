<template>
  <div>
    <el-form
        :rules="rules"
        ref="loginForm"
        :model="loginForm"
        class="loginContainer ">
      <h3 class="loginTitle" >系统登录</h3>
       <el-button @click="register" type="primary" style="width: 5%;position: absolute;right: 950px;top: 213px" >注册</el-button>

      <el-form-item prop="username">
        <el-input type="text" auto-complete="false" v-model="loginForm.username" placeholder="请输入用户名">
        </el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input type="password" auto-complete="false" v-model="loginForm.password" placeholder="请输入密码">
        </el-input>
      </el-form-item>
      <el-button type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
    </el-form>
  </div>
</template>

<script>
// @ is an alias to /src
import {activeRouter} from "../util/perimission"

export default {
  data(){
    return{
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
      },
      loginForm: {
        username: '',
        password: '',
      },
    }
  },
  methods:{
    open(res) {
      this.$prompt('请输入验证码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then((value) => {
        console.log(res.data.data);
        console.log(value)
          if(res.data.data !== value.value){
            this.$message({
              type: 'error',
              message: '验证码错误'
            });
            return ;
          }
          //验证码通过进行获取权限和token
       this.$axios.get('/login2').then((res)=>{
         let user = res.data.data.user;
         //本地存储token
         window.sessionStorage.setItem("token",res.data.data.token);
         //存放用户。用户里面有权限
         window.sessionStorage.setItem("permissions",JSON.stringify(user));
         //更新路由
         activeRouter(this.$router,user.permissions,this.$store);
         this.$router.push('/mvc_zengzhiqi')
       })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    //点击获取验证码
    sendEmail() {

    },
    register(){
       this.$router.push("/register");
     },
    submitLogin(){
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$axios.post('/login1',this.loginForm).then(res=>{
            //如果账号密码正确，那么进行验证码认证
            if(res){
              this.open(res);
            }
          })
        } else {
          this.$message.error('请输入所有字段！')
          return false;
        }
      })
    }
  },
  components: {

  }
}
</script>

<style scoped>
.loginContainer {
  /*指定容器四个角半圆的半径*/
  border-radius: 15px;
  background-clip: padding-box;
  margin: 188px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.loginTitle {
  margin: 0px auto 40px auto;
  /*把HTML元素中的文本排列到中间*/
  text-align: center;
}

.loginRemember {
  text-align: left;
  margin: 0px 0px 15px 0px;
}

/*居中对齐弹性盒*/
.el-form-item__content {
  display: flex;
  align-items: center;
}
/*.captcha{*/
/*  width: 200px;*/
/*  position: absolute;*/
/*  top: 0px;*/
/*}*/
/*img{*/
/*  position: absolute;*/
/*  right: 10px;*/
/*  height: 40px;*/
/*}*/
</style>
