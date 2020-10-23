<template>
  <div>
    <div>
      <header style="position:fixed;z-index:999;width:100%;background-color:white">
        <div style="height:60px;border-bottom:1px solid #DCDCDC">
          
          <div style="position:relative;float:left;left:25%">
            <el-button type="text" style="font-size:28px" @click="goHome()">主页</el-button>
          </div>
          
          
          <div style="float:left;position:relative;top:10px;left:40%">
            <div style="float:left;position:relative;">
              <el-input v-model="input" placeholder="请输入内容" />
            </div>
            <div style="float:left;position:relative;left:10px">
              <el-button icon="el-icon-search" @click="searchContent()" circle />
            </div>
          </div>

          <div style="float:left;position:relative;left:52%;top:10px">
            <el-dropdown @command="handleWriteCommand">
              <el-button type="primary" class="el-icon-edit">创作</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-edit-outline" command="write">写博客</el-dropdown-item>
                <el-dropdown-item icon="el-icon-folder-opened" command="draft">草稿箱</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

          <div style="float:left;position:relative;left:53%;top:8px">
            <div v-if="!accountInfo" :key="accountInfo.id">
              <el-button type="text">
                <a style="color:#808080;font-size:18px;" @click="login()">登录</a>
                <el-divider direction="vertical" />
                <a style="color:#808080;font-size:18px;" @click="regist()">注册</a>
              </el-button>
            </div>
            <div v-if="accountInfo" :key="accountInfo.id">
              <el-dropdown @command="handleAccountCommand">
                <router-link :to="{ path: '/user/' + accountInfo.id }">
                  <el-avatar :size="40" :src="accountInfo.avatar" />
                  <span id="userName" class="vam disIb">{{ accountInfo.nickname }}</span>
                </router-link>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-user" command="myPage">我的主页</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-setting" command="option">设置</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-warning-outline" command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
        </div>
        <!-- <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          active-text-color="#87CEEB"
          background-color="#F0F8FF"
        >
          <div style="left:10%;position:relative;float:left"></div>

          <el-menu-item index="1">发现</el-menu-item>
          <el-menu-item index="2">消息</el-menu-item>

          <div style="float:left;position:relative;top:10px;left:30%">
            <div style="float:left;position:relative;">
              <el-input v-model="input" placeholder="请输入内容" />
            </div>
            <div style="float:left;position:relative;left:10px">
              <el-button icon="el-icon-search" circle />
            </div>
          </div>

          <div style="float:left;position:relative;left:44%;top:10px">
            <el-dropdown @command="handleWriteCommand">
              <el-button type="primary" class="el-icon-edit">创作</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-edit-outline" command="write">写博客</el-dropdown-item>
                <el-dropdown-item icon="el-icon-folder-opened" command="draft">草稿箱</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

          <div style="float:left;position:relative;left:45%;top:8px">
            <div v-if="!accountInfo" :key="accountInfo.id">
              <el-button type="text">
                <a style="color:#808080;font-size:18px;" @click="login()">登录</a>
                <el-divider direction="vertical" />
                <a style="color:#808080;font-size:18px;" @click="regist()">注册</a>
              </el-button>
            </div>
            <div v-if="accountInfo" :key="accountInfo.id">
              <el-dropdown @command="handleAccountCommand">
                <a href="/accountInfo">
                  <el-avatar :size="40" :src="accountInfo.avatar" />
                  <span id="userName" class="vam disIb">{{ accountInfo.nickname }}</span>
                </a>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-s-home">我的主页</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-setting">设置</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-warning-outline" command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
        </el-menu> -->
      </header>
    </div>

    <div>
      <nuxt />
    </div>

    <div>
      <footer>
        <!-- <div style="height:60px;border-bottom:1px solid #DCDCDC">
          
        </div> -->
      </footer>
    </div>
  </div>
</template>

<script>
import cookie from "js-cookie";
import accountApi from "@/api/account";
import blogApi from "@/api/blog";

export default {
  data() {
    return {
      activeIndex: "1",
      activeIndex2: "1",
      accountInfo: "",
      input: '', // 输入内容
      searchList: {} // 搜索结果的列表
    };
  },
  watch: {
    $route(to, from) {
      // 路由变化方式，路由发生变化，方法就会执行
      if (cookie.get("login_token")) {
        this.getAccountInfo();
      }
    }
  },
  created() {
    setTimeout(function () {},1);
    if (cookie.get('login_token')) {
      this.getAccountInfo()
    }
  },
  mounted() {

  },
  methods: {
    // 查询内容
    searchContent() {
      // blogApi.searchContent(this.input).then(response => {
      //   console.log(response)
      //   this.searchList = response.data.data.searchRes
      // })
      if (this.input === '') {
        this.$router.push({ path:'/' })
      } else {
        this.$router.push({ path:'/search/' + this.input })
      }
    },
    // 处理头像的下拉菜单
    handleAccountCommand(command) {
      if (cookie.get('login_token')) {
        if (command === 'logout') {
          // 清空cookie信息
          cookie.set('login_token', '', { domain: 'localhost' })
          cookie.set('login_info', '', { domain: 'localhost' })
          cookie.set('user_info', '', { domain: 'localhost' })
          this.$message({
            message: "退出成功",
            type: "success"
          }); 
          this.$router.go(0)
        } else if (command === 'myPage') {
          this.$router.push({ path: '/user/' + this.accountInfo.id })
        } else if (command === 'option') {
          this.$router.push({ path: '/option' })
        }
      } else {
        this.$message({
          message: "请先登录",
          type: "error"
        });
      }
    },
    // 处理创作的下拉菜单
    handleWriteCommand(command) {
      if (cookie.get('login_token')) {
        if (command === 'write') {
          this.$router.push({ path: '/write' })
        } else if (command === 'draft') {
          this.$router.push({ path: '/user/' + this.accountInfo.id , query: { menu: 'draft' }})
        }
      } else {
        this.$message({
          message: "请先登录",
          type: "error"
        });
        this.$router.push({ path: '/loginAndRegist' })
      }
    },
    getAccountInfo() {
      accountApi.getAccountInfoByToken().then(response => {
        this.accountInfo = response.data.data.accountInfo;
        // if (!this.accountInfo.avatar) {
        //   this.accountInfo.avatar = 'https://edu-952.oss-cn-hangzhou.aliyuncs.com/timg.jpg'
        // }
      });
    },
    login() {
      this.$router.push({ name: "loginAndRegist", params: { id: 1 } });
    },
    regist() {
      this.$router.push({ name: "loginAndRegist", params: { id: 2 } });
    },
    goHome() {
      this.$router.push({ path: '/' })
    }
  }
};
</script>

<style>
html {
  font-family: "Source Sans Pro", -apple-system, BlinkMacSystemFont, "Segoe UI",
    Roboto, "Helvetica Neue", Arial, sans-serif;
  font-size: 16px;
  word-spacing: 1px;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
}

*,
*:before,
*:after {
  box-sizing: border-box;
  margin: 0;
}

.button--green {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #3b8070;
  color: #3b8070;
  text-decoration: none;
  padding: 10px 30px;
}

.button--green:hover {
  color: #fff;
  background-color: #3b8070;
}

.button--grey {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #35495e;
  color: #35495e;
  text-decoration: none;
  padding: 10px 30px;
  margin-left: 15px;
}

.button--grey:hover {
  color: #fff;
  background-color: #35495e;
}
</style>

