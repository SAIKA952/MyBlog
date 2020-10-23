<template>
  <div style="position:fixed;height:100%;width:100%;background-color:#F5F5F5">
    <div class="m-container" style="position:relative;top:100px;left:35%;width:30%">
      <div>
        <el-card shadow="always">
          <div style="width:80%;position:relative;left:10%">
            <el-tabs v-model="activeName" @tab-click="clear" stretch>
              <el-tab-pane label="登录" name="first">
                <div class="m-content">
                  <el-form
                    ref="editForm"
                    :model="loginForm"
                    :rules="rules"
                    status-icon
                    label-width="80px"
                  >
                    <div style="text-align:center"><el-avatar :size="100" :src="circleUrl"></el-avatar></div>
                    <br />
                    <el-form-item label="用户名" prop="username" style="width:80%">
                      <el-input v-model="loginForm.username" prefix-icon="el-icon-user"/>
                    </el-form-item>
                    <el-form-item label="密码" prop="password" style="width:80%">
                      <el-input
                        v-model="loginForm.password"
                        type="password"
                        show-password
                        maxlength="16"
                        prefix-icon="el-icon-lock"
                      />
                    </el-form-item>
                  </el-form>
                </div>
                <div style="position:relative;left:35%">
                  <el-button type="primary" round @click="login()">登录</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane label="注册" name="second">
                <br />
                <div class="m-content">
                  <el-form
                    ref="editForm"
                    :model="registForm"
                    :rules="rules"
                    status-icon
                    label-width="80px"
                  >
                    <el-form-item label="用户名" prop="username" style="width:80%">
                      <el-input v-model="registForm.username" prefix-icon="el-icon-user" />
                    </el-form-item>
                    <el-form-item label="手机号码" prop="phone" style="width:80%">
                      <el-input v-model="registForm.phone" prefix-icon="el-icon-phone-outline" />
                    </el-form-item>
                    <el-form-item label="密码" prop="password" style="width:80%">
                      <el-input
                        v-model="registForm.password"
                        type="password"
                        show-password
                        maxlength="16"
                        prefix-icon="el-icon-lock"
                      />
                    </el-form-item>
                  </el-form>
                </div>
                <div style="position:relative;left:35%">
                  <el-button round type="success" @click="regist()">注册</el-button>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
import cookie from 'js-cookie'

export default {
  data() {
    return {
      activeName: "first",
      // 登录表单
      loginForm: {
        username: "",
        password: ""
      },
      // 注册表单
      registForm: {
        username: "",
        password: ""
      },
      userInfo: {},
      // 表单输入规则
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 50, message: "长度在 3 到 16 个字符", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { min: 11, max: 11, message: "请输入11位手机号", trigger: "blur" }
        ]
      },
      accountInfo: {},
      // 封装用户信息传到cookie
      idAndUsername: {
        id: '',
        username: ''
      },
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
    };
  },
  created() {
    if (this.$route.params.id === 1) {
      this.activeName = "first";
    } else {
      this.activeName = "second";
    }
  },
  methods: {
    // 用户登录
    login() {
      accountApi.userLogin(this.loginForm).then(response => {
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
        } else if (response.data.code === 20000) {
          this.$message({
            message: "登录成功",
            type: "success"
          });
          // 将token保存在cookie中
          cookie.set('login_token', response.data.data.accountToken, { domain: 'localhost' })
          if (cookie.get('login_token')) {

            accountApi.getAccountInfoByToken().then(response => {
              this.accountInfo = response.data.data.accountInfo
              this.idAndUsername.id = this.accountInfo.id
              this.idAndUsername.username = this.accountInfo.username

              cookie.set('user_info', this.idAndUsername, { domain: 'localhost' })
              this.$router.push({ path: '/' })
            })
          }
        }
      })
    },
    // 用户注册
    regist() {
      console.log(this.registForm);
      accountApi.userRegist(this.registForm).then(response => {
        // 获取后端发来的状态码，如果是20001说明用户名已存在
        if (response.data.code === 20001) {
          this.$message({
            message: "用户名已存在",
            type: "error"
          });
        } else if (response.data.code === 20000) {
          this.$message({
            message: "注册成功",
            type: "success"
          });
          this.loginForm = this.registForm;
          this.activeName = "first";
        }
      });
    },
    clear() {
      this.loginForm = {};
      this.registForm = {};
    }
  }
};
</script>

<style>
</style>