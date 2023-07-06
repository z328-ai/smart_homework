<template>
  <div>
    <el-form
        :rules="rules"
        ref="registerForm"
        :model="registerForm"
        class="loginContainer ">
      <h3 class="loginTitle">用户注册</h3>
      <el-form-item prop="name">
        <el-input type="text" auto-complete="false" v-model="registerForm.name" placeholder="请输入用户名">
        </el-input>
      </el-form-item>
      <el-form-item prop="username">
        <el-input type="text" auto-complete="false" v-model="registerForm.username" placeholder="请输入账号">
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input type="text" auto-complete="false" v-model="registerForm.email" placeholder="请输入邮箱">
        </el-input>
        <el-button @click="sendEmail">点击获取邮箱</el-button>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" auto-complete="false" v-model="registerForm.password" placeholder="请输入密码">
        </el-input>
      </el-form-item>
      <el-button type="primary" style="width: 100%;" @click="registerUser">注册</el-button>
    </el-form>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  data(){
    return{
      rules: {
        name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        username: [{required: true, message: '请输入账号', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        email: [{required: true, message: '请输入邮箱', trigger: 'blur'}],
      },
      registerForm: {
        name:'',
        username: '',
        password: '',
        email:''
      },
      emailPassword:''
    }
  },
  methods:{
    registerUser(){
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          //当输入密码和邮箱密码相同时，才能进行注册
          if(this.emailPassword === this.registerForm.password){
            this.$axios.post('/register',this.registerForm).then(res=>{
              //注册成功进行跳转
              if(res){
                this.$router.replace('/login');
              }
            })
            return ;
          }
          this.$message.error("密码错误");
        } else {
          this.$message.error('请输入所有字段！')
          return false;
        }
      })
    },
    //发送邮箱获取密码
    sendEmail(){
      //获取后端发来的邮箱中密码，交给前端比较
      this.$axios.get("/email?email="+this.registerForm.email).then((res)=>{
        if(res){
          this.emailPassword = res.data.data;
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
