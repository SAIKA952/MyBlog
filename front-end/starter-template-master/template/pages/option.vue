<template>
  <div style="position:relative;">
    <div>
      <br />
      <div style="width:1000px;left:25%;position:relative;">
        <el-tabs tab-position="left">
          <el-tab-pane>
            <span slot="label">
              <i class="el-icon-tickets"> 个人资料</i>
            </span>
            <div style="position:relative;left:100px;width:70%">
              <div style="height:100px">
                <div style="float:left">
                  <el-upload
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                    class="avatar-uploader"
                    action="http://115.29.209.156:8001/oss">
                    <el-avatar :src="avatarUrl" :size="100" class="avatar" />
                  </el-upload>
                </div>
                <el-upload
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
                  class="avatar-uploader"
                  action="http://115.29.209.156:8001/oss">
                  <el-button style="left:40px;position:relative;top:25px" type="success" plain round>更换头像</el-button>
                </el-upload>
                
              </div>
              <el-divider />
              <div>
                用户名：
                <el-input
                  style="width:30%;position:relative;left:70px"
                  placeholder="请输入内容"
                  v-model="userForm.username"
                  :disabled="true"
                />
              </div>
              <el-divider />
              <div>
                手机号：
                <el-input
                  style="width:30%;position:relative;left:70px"
                  placeholder="请输入内容"
                  v-model="userForm.phone"
                />
              </div>
              <el-divider />
              <div>
                email：
                <el-input
                  style="width:30%;position:relative;left:80px"
                  placeholder="请输入内容"
                  v-model="userForm.email"
                />
              </div>
              <el-divider />
              <div>
                性别：
                <el-radio-group v-model="userForm.sex" style="position:relative;left:90px">
                  <el-radio :label="1">
                    <i class="el-icon-male">男</i>
                  </el-radio>
                  <el-radio :label="0">
                    <i class="el-icon-female">女</i>
                  </el-radio>
                </el-radio-group>
              </div>
              <el-divider />
              <div>
                生日：
                <el-date-picker
                  v-model="userForm.birthday"
                  type="date"
                  placeholder="选择日期"
                  style="position:relative;left:90px"
                />
              </div>
              <br />
              <br />
              <el-button type="success" plain round @click="updateAccount()">保存</el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane>
            <span slot="label">
              <i class="el-icon-lock"> 修改密码</i>
            </span>
            <div style="position:relative;left:70px;width:70%">
              <div style="width:500px">
                <el-form
                  :model="password"
                  status-icon
                  :rules="rules"
                  ref="password"
                  label-width="100px"
                  class="demo-ruleForm"
                >
                  <el-form-item label="旧密码：" prop="oldPassword">
                    <el-input
                      type="password"
                      v-model="password.oldPassword"
                      autocomplete="off"
                      show-password
                      maxlength="16"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="新密码：" prop="newPassword">
                    <el-input
                      type="password"
                      v-model="password.newPassword"
                      autocomplete="off"
                      show-password
                      maxlength="16"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="确认密码：" prop="checkPassword">
                    <el-input
                      type="password"
                      v-model="password.checkPassword"
                      autocomplete="off"
                      show-password
                      maxlength="16"
                    ></el-input>
                  </el-form-item>
                </el-form>
              </div>
              <br />
              <el-button type="success" plain round @click="updatePassword()">修改</el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
import cookie from "js-cookie";
import blogApi from "@/api/blog";
import likedApi from "@/api/liked";
import followApi from "@/api/follow";

export default {
  data() {
    var validateOldPassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };
    var validateNewPassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (value.length < 8) {
        callback(new Error("密码长度不能小于8位"));
      } else {
        if (this.password.checkPassword !== "") {
          this.$refs.password.validateField("checkPassoword");
        }
        callback();
      }
    };
    var validateCheckPassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.password.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else if (value.length < 8) {
        callback(new Error("密码长度不能小于8位"));
      } else {
        callback();
      }
    };
    return {
      userInfo: {},
      avatarUrl: "", // 头像地址
      userForm: {
        // 用户资料表单
        sex: "", // 性别
        email: "", // 邮箱
        username: "", // 用户名
        birthday: "", // 生日
        phone: "" // 手机号
      },
      password: {
        // 密码表单
        oldPassword: "",
        newPassword: "",
        checkPassword: ""
      },
      submitPasswordForm: {}, // 提交修改密码的表单
      submitAvatarForm: {}, // 提交修改头像的表单
      rules: {
        oldPassword: [
          { validator: validateOldPassword, trigger: "blur", required: true }
        ],
        newPassword: [
          { validator: validateNewPassword, trigger: "blur", required: true }
        ],
        checkPassword: [
          { validator: validateCheckPassword, trigger: "blur", required: true }
        ]
      }
    };
  },
  mounted() {
    this.initData(); // 初始化页面数据
  },
  methods: {
    // 将新头像的地址保存到数据库中
    avatarSubmit(userId, avatarUrl) {
      this.submitAvatarForm.userId = userId
      this.submitAvatarForm.avatar = avatarUrl
      accountApi.avatarSubmit(this.submitAvatarForm).then(response => {
          this.$message({
            message: '上传成功',
            type: 'success'
          })
          this.$forceUpdate()
        })
    },
    // 头像上传成功的方法
    handleAvatarSuccess(res, file) {
      this.avatarUrl = res.data.url
      if (!res.data.url) {
        this.$message({
          message: '上传失败',
          type: 'error'
        })
      } else {
        this.avatarSubmit(this.userInfo.id, this.avatarUrl)
      }
    },
    // 头像上传之前的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png'
      const isLt10M = file.size / 1024 / 1024 < 10

      if (!isJPG && !isPNG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!')
      }
      if (!isLt10M) {
        this.$message.error('上传头像图片大小不能超过 10MB!')
      }
      return isJPG || isPNG && isLt10M
    },
    // 修改用户的密码
    updatePassword() {
      this.submitPasswordForm.userId = this.userInfo.id
      this.submitPasswordForm.oldPassword = this.password.oldPassword
      this.submitPasswordForm.newPassword = this.password.newPassword
      accountApi.updateAccountPassword(this.submitPasswordForm).then(response => {
        console.log(response)
        if (response.data.code === 21001) {
          this.$message({
            message: "旧密码与新密码不能相同",
            type: "error"
          });
        } else if (response.data.code === 21002) {
          this.$message({
            message: "密码错误，请重新输入",
            type: "error"
          });
        } else {
          this.$message({
            message: "修改成功",
            type: "success"
          });
        }
      })
    },
    // 根据用户id获取用户信息
    getAccountInfoById(userId) {
      accountApi.getAccountInfoById(userId).then(response => {
        this.userForm = response.data.data.account;
        this.avatarUrl = response.data.data.account.avatar; // 获取头像地址
      });
    },
    updateAccount() {
      accountApi.updateAccount(this.userForm).then(response => {
        this.$message({
          message: "保存成功",
          type: "success"
        });
      });
    },
    // 从cookie中获取登录账号的id
    getLoginInfo() {
      var userInfoStr = cookie.get("user_info");
      this.userInfo = JSON.parse(userInfoStr);
    },
    // 初始化页面数据
    initData() {
      this.getLoginInfo();
      this.getAccountInfoById(this.userInfo.id);
    }
  }
};
</script>

<style>
</style>