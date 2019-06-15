<template>
  <el-form ref="AccountFrom" :model="account" :rules="rules" label-position="left" label-width="0px"
           class="demo-ruleForm login-container" style="width: 500px;text-align: center;margin: auto;">
    <h3 class="title" style="text-align: center;">聊天系统</h3>
    <el-form-item prop="u_name">
      <el-input type="text" v-model="account.u_name" auto-complete="off" placeholder="请输入账号"></el-input>
    </el-form-item>
    <el-form-item prod="u_password">
      <el-input type="password" v-model="account.u_password" auto-complete="off" placeholder="请输入密码"></el-input>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%" @click="handleLogin" :loading="logining">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import qs from 'qs'
import axios from 'axios'
import { setCookie } from '../../util/util'
import md5 from 'js-md5'

export default {
  data () {
    return {
      logining: false,
      account: {
        u_name: '',
        u_password: ''
      },
      // 表单验证规则
      rules: {
        u_name: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        u_password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    // 初始化
  },
  methods: {
    handleLogin () {
      this.$refs.AccountFrom.validate((valid) => {
        if (valid) {
          this.logining = true

          // 其中 'm/login' 为调用的接口，this.account为参数
          /* this.$post('m/login',this.account).then(res => {
              this.logining = false;
              if(res.errCode !== 200) {
                this.$message({
                  message: res.errMsg,
                  type:'error'
                });
              } else {
                let expireDays = 1000 * 60 * 60 ;
                this.setCookie('session',res.errData.token,expireDays); //设置Session
                this.setCookie('u_uuid',res.errData.u_uuid,expireDays); //设置用户编号
                if(this.$route.query.redirect) {
                  this.$router.push(this.$route.query.redirect);
                } else {
                  this.$router.push('/home'); //跳转用户中心页
                }
              }
            }); */
          let self = this
          var param = { 'name': self.account.u_name, 'password': md5(self.account.u_password) }
          axios.post('/apis/user/login', param)
            .then(function (response) {
              return response.data
            })
            .then(function (data) {
              if (data.code == 1) {
                self.$ckq.layer({
                  message: data.msg,
                  time: 1500
                })
                self.logining = false
                return false
              }

              self.user = data.data
              console.log(self.user)

              let expireDays = 1000 * 60 * 60
              setCookie('user', JSON.stringify(self.user), expireDays)
              self.$router.push('/home') // 跳转用户中心页
            })
        } else {
          console.log('error submit')
          return false
        }
      })
    }
  }
}
</script>
