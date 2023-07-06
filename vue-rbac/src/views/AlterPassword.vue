<template>
<div>
  <el-input
      placeholder="请输入要修改的密码"
      v-model="password"
      style="width: 200px;margin-right: 20px"
      clearable>
  </el-input>

  <el-button type="primary" @click="updatePassword">
    修改
  </el-button>
</div>
</template>

<script>
export default {
  name: "home",
  data(){
    return {
      password:''
    }
  },
  methods:{
    updatePassword(){
      if(this.password===''){
        this.$message.error("请输入要修改密码")
      }else{
        this.$axios.put("/updatePassword?password="+this.password)
      }
    }
  }
}
</script>

<style scoped>

</style>