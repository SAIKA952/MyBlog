<template>
  <div>
    <div id="background">
      <div style="top:30%;position:relative">
        <div style="text-align:center">
          <h1>后台管理登录</h1>
        </div>
        <br />
        <div id="form">
          <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" label-width="100px" class="demo-ruleForm" >
            <el-form-item label="账号" prop="username">
              <el-input type="username" v-model="loginForm.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="info" @click="login('loginForm')">登录</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
import msgApi from "@/api/msg";
import cookie from "js-cookie";

export default {
  layout: "blank",
  data() {
    return {
        loginForm: {
            username: "",
            password: ""
        },
        rules: {
            username: [
                { message: "请输入用户名", trigger: "blur" },
                { min: 3, max: 12, message: "长度在 3 到 12 个字符", trigger: "blur" }
            ],
            password: [
                { message: "请输入密码", trigger: "blur" },
                { min: 8, max: 16, message: "长度在 8 到 16 个字符", trigger: "blur" }
            ],
        }
    };
  },
  created() {},
  methods: {
    // 管理员登录
    login() {
      accountApi.adminLogin(this.loginForm).then(response => {
        if (response.data.code === 20002) {
          this.$message({
            message: "用户名不存在",
            type: "error"
          });
        } else if (response.data.code === 20003) {
          this.$message({
            message: "密码错误",
            type: "error"
          });
        } else if (response.data.code === 20005) {
          this.$message({
            message: "没有权限",
            type: "error"
          });
        } else {
          this.$message({
            message: "登陆成功",
            type: "success"
          });
          cookie.set('admin_info', response.data.data.account.id, { domain: '115.29.209.156' }) // 115.29.209.156 localhost
          this.$router.push({ path: '/manage'})
        }
        
      })
    }
  }
};
</script>

<style>
h1 {
  color: #ffffff;
}
p {
  color: #ffffff;
  font-size: 24px;
  float: left;
}
#form {
    position: relative;
    left:37%;
    width:22%;
}
#background {
  background-color: #24292e;
  position: absolute;
  height: 100%;
  width: 100%;
}
</style>