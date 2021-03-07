<template>
  <div style="min-width:1300px">
    <el-backtop />
    <div style="position:relative;left:16%;top:20px;width:1000px;float:left;">
      <div v-if="isPageLoading">
        <div>
          <el-card shadow="hover">
            <div style="text-align:center;height:90px;top:40px;position:relative">
                <i class="el-icon-loading"></i>
            </div>
          </el-card>
        </div>
      </div>

      <div v-if="!isPageLoading">
        <div style="position:relative;">
          <el-image style="width:1000px;height:400px" src="https://edu-952.oss-cn-hangzhou.aliyuncs.com/wallhaven-k7787d.jpg" fit="cover"></el-image>
        </div>
        <br />
        <div>
          <div v-for="index in indexList" :key="index.id">
            <el-card shadow="hover">
              <div>
                <div
                  style="text-align:center;cursor:pointer;width:140px;display:inline-block;"
                  @click="gotoUserPage(index.authorId)"
                >
                  <div style="width:100%">
                    <div class="block">
                      <el-avatar :size="50" :src="index.authorAvatar" />
                    </div>
                    <div>{{ index.authorName }}</div>
                  </div>
                </div>
                <div style="left:20px;position:relative;display:inline-block;width:640px;">
                  <div>
                    <div
                      style="position:absolute;top:-60px;cursor:pointer"
                      @click="gotoBLogPage(index.blogId)"
                    >
                      <h1 id="title" style="text-decoration:none;">{{ index.blogTitle }}</h1>
                      <p style="color:#808080;position:relative;top:10px;font-size:12px">{{ index.blogContent }}</p>
                    </div>
                  </div>
                  <div style="float:left;font-size:13px;top:20px;position:relative">
                    <p style="color:#A9A9A9">
                      <i class="el-icon-caret-top">{{ index.likedCount }} 赞</i>
                      <el-divider direction="vertical" />
                      <i class="el-icon-star-off">{{ index.collectCount }}</i>
                      <el-divider direction="vertical" />
                      <i class="el-icon-chat-dot-square">{{ index.commentsCount }}</i>
                      <el-divider direction="vertical" />
                      <i class="el-icon-view">{{ index.views }}</i>
                      <el-divider direction="vertical" />
                      {{ index.createOn }}
                    </p>
                  </div>
                </div>
                <el-image v-if="index.imgURL.length > 1" @click="gotoBLogPage(index.blogId)" :src="index.imgURL" style="float:right;width:140px;height:80px;cursor:pointer" fit="contain"></el-image>
                <br />
              </div>
            </el-card>
            <br />
          </div>

          <div
            style="position:relative;height:80px;left:50%;top:30px"
            v-if="this.indexPageInfo.hasNextPage"
          >
            <el-button type="primary" v-if="!this.isButtonLoading" round @click="readMore(page)">阅读更多</el-button>
            <el-button type="primary" v-if="this.isButtonLoading" :loading="true" round>加载中...</el-button>
          </div>

          <div
            style="position:relative;height:80px;left:50%;top:30px;color:#A9A9A9"
            v-if="this.indexPageInfo.isLastPage"
          >没有更多内容了</div>
        </div>
      </div>
    </div>

    <div style="float:left;position:relative;top:20px;left:17%;width:250px">

      <div v-if="accountInfo.id > 0">
        <div>
          <el-card shadow="always">
            <div>
              <div style="float:left;">
                <el-avatar :size="40" :src="accountInfo.avatar" />
              </div>

              <div style="position:relative;left:16px">
                <h2 style="position:relative;font-size:16px">{{ accountInfo.username }}</h2>
                <div style="color:#A9A9A9">
                  关注：{{ followCount }}
                  <el-divider direction="vertical" />
                  粉丝：{{ fansCount }}
                </div>
              </div>
            </div>

            <el-divider>
              <i class="el-icon-paperclip"></i>
            </el-divider>
            <div style="color:	#000080">
              <h4 id="text1" @click="gotoMyPage(1)" style="position:relative;height:30px">我的文章：{{ blogCount }}</h4>
              <h4 id="text2"  @click="gotoMyPage(2)" style="position:relative;height:30px">我的收藏：{{ collectCount }}</h4>
              <h4 id="text3" @click="gotoMyPage(3)" style="position:relative;height:30px">我喜欢的文章：{{ likedCount }}</h4>
            </div>
            <el-divider>
              <i class="el-icon-medal"></i>
            </el-divider>

            <div style="color:	#000080">
              <!-- <p style="position:relative;height:30px">我的成就</p> -->
              <h4 style="position:relative;height:30px">文章共被赞：{{ allLikedCount }}</h4>
              <h4 style="position:relative;height:30px">文章共被收藏：{{ allCollectCount }}</h4>
              <h4 style="position:relative;height:30px">文章共被浏览：{{ allViewsCount }}</h4>
            </div>
            <el-divider>
              <i class="el-icon-date"></i>
            </el-divider>
            <div style="color:	#000080">
              <h4>入站时间：{{ accountInfo.createOn.substring(0,10) }}</h4>
            </div>
          </el-card>
        </div>
      </div>

      <div v-if="!accountInfo">
        <div>
          <el-card shadow="always">
            <div style="text-align:center;color:#A9A9A9">
              <p>
                <i class="el-icon-warning-outline"></i> 请登录
              </p>
            </div>
          </el-card>
        </div>
      </div>
  
      <br />
      <div>
        <div>
          <el-card shadow="always">
            <P style="color:	#8B008B;text-align:center"><i class="el-icon-trophy"></i> 排行榜</P>
            <br />
            <div v-if="!isChampionLoading">
              <div v-for="(blog, i) in championList" :key="blog.id">
                <div v-if="i === 0">
                  <h4 style="color:red;float:left;width:16px">1</h4>
                </div>
                <div v-if="i === 1">
                  <h4 style="color:orange;float:left;width:16px">2</h4>
                </div>
                <div v-if="i === 2">
                  <h4 style="color:gold;float:left;width:16px">3</h4>
                </div>
                <div v-if="i > 2">
                  <h4 style="color:DarkGray;float:left;width:16px">{{ i + 1 }}</h4>
                </div>
                <div>
                  <a id="championTitle" :href="'/blog/' + blog.id">{{ blog.title }}</a>
                </div>
                <br />
              </div>
            </div>
            <div v-if="isChampionLoading">
              <div style="text-align:center">
                <i class="el-icon-loading"></i>
              </div>
            </div>
          </el-card>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
import commentApi from "@/api/comment";
import likedApi from "@/api/liked";
import followApi from "@/api/follow";
import cookie from "js-cookie";
import blogApi from "@/api/blog";

export default {
  data() {
    return {
      indexPageInfo: "",
      indexList: {},
      moreIndexList: {},
      userAvatarUrl: "",
      page: 1,
      isButtonLoading: false,
      isPageLoading: true,
      isChampionLoading: false,
      idAndUsername: "",
      loginInfo: "",
      accountInfo: "",
      followCount: "",
      fansCount: "",
      blogCount: "",
      collectCount: "",
      likedCount: "",
      allLikedCount: "",
      allCollectCount: "",
      allViewsCount: "",
      championList: "",

      imgURL: ""
    };
  },
  watch: {
    moreIndexList(val, oldVal) {
      this.isButtonLoading = false;
    },
    indexList(val, oldVal) {
      this.isPageLoading = false;
    },
    championList(val, oldVal) {
      this.isChampionLoading = false
    }
  },
  mounted() {
    setTimeout(() => {
      this.init();
    }, 500);
  },
  methods: {
    searchAll(page) {
      this.isPageLoading = true;
      blogApi.searchAll(page).then(response => {
        this.indexPageInfo = response.data.data.pageInfo;
        this.indexList = this.indexPageInfo.list;
      });
    },
    // 点击阅读更多
    readMore(page) {
      // 传入page，让page+1作为参数查找页面
      this.isButtonLoading = true;
      this.page = page + 1;
      blogApi.searchAll(this.page).then(response => {
        this.indexPageInfo = response.data.data.pageInfo;

        this.moreIndexList = this.indexPageInfo.list;
        // 将查出来的数据与原来的数据indexList相加
        // 两个数组相加用concat
        this.indexList = this.indexList.concat(this.moreIndexList);
      });
    },
    // 点击作者头像，去该用户的主页
    gotoUserPage(id) {
      this.$router.push({ path: "/user/" + id });
    },
    gotoBLogPage(bid) {
      this.$router.push({ path: "/blog/" + bid });
    },
    // 初始化，获取首页数据
    init() {
      this.searchAll(this.page);
      this.getChampion()
      this.getAccountInfo();
      if (this.loginInfo.id > 0) {
        this.getFollowCountByUserId(this.loginInfo.id);
        this.getFansCountByUserId(this.loginInfo.id);
        this.getBlogCountByUserId(this.loginInfo.id);
        this.getCollectCountByUserId(this.loginInfo.id);
        this.getLikedCountByUserId(this.loginInfo.id);
        this.getAllBlogsLikedCountByUserId(this.loginInfo.id);
        this.getAllBlogsCollectCountByUserId(this.loginInfo.id);
        this.getAllBlogsViewsCountByUserId(this.loginInfo.id);
      }
    },
    // 获取用户信息
    getAccountInfo() {
      if (cookie.get("user_info")) {
        var loginInfoStr = cookie.get("user_info"); // 从cookie中获取登录信息的字符串
        this.loginInfo = JSON.parse(loginInfoStr); // 将字符串转化为JSON格式
        accountApi.getAccountInfoById(this.loginInfo.id).then(response => {
          this.accountInfo = response.data.data.account;
        });
      }
      
    },
    // 获取用户关注数
    getFollowCountByUserId(id) {
      followApi.getFollowCountByUserId(id).then(response => {
        this.followCount = response.data.data.count;
      });
    },
    // 获取用户粉丝数
    getFansCountByUserId(id) {
      followApi.getFansCountByUserId(id).then(response => {
        this.fansCount = response.data.data.count;
      });
    },
    // 获取用户博客数
    getBlogCountByUserId(id) {
      blogApi.getBlogCountByUserId(id).then(response => {
        this.blogCount = response.data.data.blogCount;
        console.log(this.blogCount);
      });
    },
    // 根据用户id获取用户收藏的文章数
    getCollectCountByUserId(id) {
      likedApi.getCollectCountByUserId(id).then(response => {
        this.collectCount = response.data.data.collectCount;
      });
    },
    // 根据用户id获取用户点赞的文章数
    getLikedCountByUserId(id) {
      likedApi.getLikedCountByUserId(id).then(response => {
        this.likedCount = response.data.data.likedCount;
      });
    },
    // 根据用户id统计该用户的所有文章获取的点赞数
    getAllBlogsLikedCountByUserId(id) {
      likedApi.getAllBlogsLikedCountByUserId(id).then(response => {
        this.allLikedCount = response.data.data.count;
      });
    },
    // 根据用户id统计该用户的所有文章获取的收藏数
    getAllBlogsCollectCountByUserId(id) {
      likedApi.getAllBlogsCollectCountByUserId(id).then(response => {
        this.allCollectCount = response.data.data.count;
      });
    },
    // 统计该用户所有博客的浏览数
    getAllBlogsViewsCountByUserId(id) {
      blogApi.getAllBlogsViewsCountByUserId(id).then(response => {
        this.allViewsCount = response.data.data.count;
      });
    },
    // 获取排行榜数据（按浏览数取前十）
    getChampion() {
      blogApi.getChampion().then(response => {
        this.championList = response.data.data.championList
        this.isChampionLoading = false
      })
    },
    gotoMyPage(menu) {
      if (menu === 1) {
        this.$router.push({ path:'/user/' + this.loginInfo.id, query: { menu: 'blog' } })
      } else if (menu === 2) {
        this.$router.push({ path:'/user/' + this.loginInfo.id, query: { menu: 'collect' } })
      } else if (menu === 3) {
        this.$router.push({ path:'/user/' + this.loginInfo.id, query: { menu: 'like' } })
      }
    }
  }
};
</script>

<style>
h2:hover {
  text-decoration: underline;
  color: #000000;
}
h4:hover {
  text-decoration: none;
  /* color: #0400ff; */
}
#championTitle {
  text-decoration: none;
  color: #000000;
}
#text1:hover,#text2:hover,#text3:hover {
  cursor: pointer;
  color:mediumslateblue
}
#title{
  font-size:20px
}

#title:hover {
  color:#66B1FF
}
</style>

