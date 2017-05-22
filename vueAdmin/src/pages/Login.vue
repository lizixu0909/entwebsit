<template>
  <el-form ref="ruleForm" :model="ruleForm" label-position="left" label-width="0px;"
           class="demo-ruleForm login-container">
    <h3 class="title">后台管理系统登录</h3>
    <el-form-item lable="用户名">
      <el-input v-model="ruleForm.userName" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item lable="密码">
      <el-input v-model="ruleForm.password" type="password" placeholder="密码"/>
    </el-form-item>
    <!--<el-checkbox v-model="checked" checked class="rember">记住密码</el-checkbox>-->
    <el-form-item style="width:100%;">
      <el-button :loading="logining" type="primary" style="width: 100%;" @keyup.enter="submitForm"
                 @click.submit.prevent="submitForm">提交
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  export default{
    data(){
      return {
        ruleForm: {
          userName: 'admin',
          password: 'admin'
        },
        logining: false,
      }
    },
    created: function () {

    },
    methods: {
      submitForm(){
        this.logining = true;
        this.$http.post('login', this.ruleForm)
          .then((response) => {
            this.logining = false;
            sessionStorage.setItem('user', JSON.stringify(response.data.data));
            this.$router.push('/welcome');
          });
      },
      resetForm(){
      }
    }
  }
</script>
<style type="text/scss" scoped>
  .login-container {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 100px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>
