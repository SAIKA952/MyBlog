<template>
  <div style="position:relative;height:100%;width:100%;">
    <div style="background-color:#F5F5F5;width:100%;height:100%;position:fixed"/>
    <div class="m-container" style="position:relative;top:100px;left:35%;width:600px">
      <div>
        <el-card shadow="always">
          <div>
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
                <div style="position:relative;left:43%">
                  <div>
                    <el-checkbox v-model="rememberMe">记住我</el-checkbox>
                  </div>
                  <br />
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
                    <el-form-item label="验证码" prop="code" style="width:80%">
                      <div>
                        <el-input style="float:left;width:55%" v-model="registForm.code" prefix-icon="el-icon-paperclip" />
                        <el-button v-if="time === 60" style="float:right" type="success" size="medium" @click="sendCode()">{{ codeText }}</el-button>
                        <el-button v-if="time !== 60" style="float:right" disabled type="success" size="medium">{{ codeText }}</el-button>
                      </div>
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
                <div style="position:relative;left:43%">
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
import msgApi from "@/api/msg";
import cookie from 'js-cookie'

export default {
  data() {
    return {
      time: 60,
      codeText: "发送验证码",
      activeName: "first",
      rememberMe: false, // 记住我
      // 登录表单
      loginForm: {
        username: "",
        password: ""
      },
      // 注册表单
      registForm: {
        username: "",
        password: "",
        code: "",
        phone: ""
      },
      userInfo: {},
      // 表单输入规则
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 8, max: 16, message: "长度在 8 到 16 个字符", trigger: "blur" }],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { min: 11, max: 11, message: "请输入11位手机号", trigger: "blur" }
        ],
        code: [
          {required: true, message: "请输入验证码", trigger: "blur"}
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
    // this.isLogin()
  },
  methods: {
    // 验证手机号是否合法
    validatePhone(phone) {
      let regs = /^((13[0-9])|(17[0-3,6-8])|(15[^4,\\D])|(18[0-9]))\d{8}$/;
      if (!regs.test(phone)) {
        this.$message({
          message: "手机号不合法",
          type: "error"
        });
        return false;
      }
      return true;
    },
    // 发送验证码
    sendCode() {
      if (this.validatePhone(this.registForm.phone)) { // 验证成功，发送验证码
        msgApi.sendCode(this.registForm.phone)
        let clock = window.setInterval(() => {
          this.time--
          this.codeText = this.time + 's后重新发送'
          if (this.time == 0) {
            window.clearInterval(clock)
            this.codeText = '重新发送验证码'
            this.time = 60
          }
        }, 1000)
      } else {
        console.log('不合法')
      }
    },
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

          if (this.rememberMe) { // 用户点了记住我
            // 将token保存在cookie中，expires设置7天内cookie不过期。如果不设置expires，则退出浏览器的时候自动删除cookie
            cookie.set('login_token', response.data.data.accountToken, { expires: 7, domain: '115.29.209.156' }) // 115.29.209.156 localhost
          } else {
            cookie.set('login_token', response.data.data.accountToken, { domain: '115.29.209.156' }) // 115.29.209.156 localhost
          }

          if (cookie.get('login_token')) { 

            accountApi.getAccountInfoByToken().then(response => { // 获取登录信息
              this.accountInfo = response.data.data.accountInfo
              this.idAndUsername.id = this.accountInfo.id
              this.idAndUsername.username = this.accountInfo.username

              cookie.set('user_info', this.idAndUsername, { domain: '115.29.209.156' })
              this.$router.push({ path: '/' })
            })
          }
        } else if (response.data.code === 6666) { // 管理员登录
          this.$router.push({ path: '/manage' });
        }
      })
    },
    // 用户注册
    regist() {
      if (this.registForm.username.trim() === '' || this.registForm.username.length < 3 || this.registForm.username.length > 8) {
        this.$message({
          message: "用户名不合法",
          type: "error"
        });
      } else if (this.registForm.password.trim() === '' || this.registForm.password.length < 8 || this.registForm.password.length > 16) {
        this.$message({
          message: "密码不合法",
          type: "error"
        });
      } else  if (this.registForm.code.length !== 6) {
        this.$message({
          message: "请输入6位数字验证码",
          type: "error"
        });
      } else {
        var flag = true
        // 校验验证码
        msgApi.validate(this.registForm.code, this.registForm.phone).then(response => {
          if (response.data.code === 20006) {
            flag = false
            this.$message({
              message: "验证码不正确",
              type: "error"
            });
          }

          if (flag) { // 验证码正确
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
          }
        })
        
      }
    },
    clear() {
      this.loginForm = {};
      this.registForm = {};
    },
    // 判断用户是否已经登录
    isLogin() {
      if (cookie.get("login_token")) {
        this.$router.push({ path: '/' })
      }
    }
  }
};
</script>

<style>
</style>