<template>
  <div style="position:relative;top:60px">
    <el-backtop />
    <div>
      <div style="position:relative;left:25%;width:960px;">
        <br />
        <div style="min-height:865px">
          <br />
          <div style="left:110px;position:relative">
            <div>
              <div style="float:left">
                <el-avatar :size="70" :src="userInfo.avatar" />
              </div>
              <div style="position:relative;left:20px">
                <div>
                  <h4 style="font-size:30px;">{{ userInfo.username }}</h4>
                </div>
                <div>
                  <p style="font-size:20px">
                    博客：{{ blogCount }}
                    <el-divider direction="vertical" />
                    喜欢：{{ likedBlogCount }}
                    <el-divider direction="vertical" />
                    收藏：{{ collectCount }}
                    <el-divider direction="vertical" />
                    关注：{{ followCount }}
                    <el-divider direction="vertical" />
                    粉丝：{{ fansCount }}
                  </p>
                </div>
              </div>
            </div>
            <!-- <el-divider/> -->
            <br />
            <br />

            <div>
              <div style="width:75%">
                <el-tabs v-model="activeName" stretch>
                  <el-tab-pane name="first">
                    <span slot="label">文章 {{ blogCount }}</span>
                    <div v-for="blog in blogList" :key="blog.id">
                      <div>
                        <div>
                          <router-link
                            :to="{ path: '/blog/' + blog.blogId }"
                            style="text-decoration:none;color:black"
                          >
                            <h3>{{ blog.blogTitle }}</h3>
                          </router-link>
                          <br />
                          <div style="color:#A9A9A9">{{ blog.blogContent }}</div>
                        </div>
                        <br />
                        <div style="color:#A9A9A9">
                          <i class="el-icon-caret-top">{{ blog.likedCount }} 赞</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-star-off">{{ blog.collectCount }}</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-chat-dot-square">{{ blog.commentsCount }}</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-view">{{ blog.views }}</i>
                          <el-divider direction="vertical" />
                          {{ blog.createOn }}
                        </div>
                      </div>
                      <el-divider />
                    </div>
                  </el-tab-pane>
                  <el-tab-pane name="second">
                    <span slot="label">喜欢 {{ likedBlogCount }}</span>
                    <div v-for="liked in likedList" :key="liked.id">
                      <div>
                        <div>
                          <router-link
                            :to="{ path: '/blog/' + liked.blogId }"
                            style="text-decoration:none;color:black"
                          >
                            <h3>{{ liked.blogTitle }}</h3>
                          </router-link>
                          <br />
                          <div style="color:#A9A9A9">{{ liked.blogContent }}</div>
                        </div>
                        <br />
                        <div style="color:#A9A9A9">
                          作者：{{ liked.authorName }}
                          <el-divider direction="vertical" />
                          <i class="el-icon-caret-top">{{ liked.likedCount }} 赞</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-star-off">{{ liked.collectCount }}</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-chat-dot-square">{{ liked.commentsCount }}</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-view">{{ liked.views }}</i>
                          <el-divider direction="vertical" />
                          {{ liked.createOn }} 赞了这篇文章
                        </div>
                      </div>
                      <el-divider />
                    </div>
                  </el-tab-pane>
                  <el-tab-pane name="third">
                    <span slot="label">收藏 {{ collectCount }}</span>
                    <div v-for="collect in collectList" :key="collect.id">
                      <div>
                        <div>
                          <router-link
                            :to="{ path: '/blog/' + collect.blogId }"
                            style="text-decoration:none;color:black"
                          >
                            <h3>{{ collect.blogTitle }}</h3>
                          </router-link>
                          <br />
                          <div style="color:#A9A9A9">{{ collect.blogContent }}</div>
                        </div>
                        <br />
                        <div style="color:#A9A9A9">
                          作者：{{ collect.authorName }}
                          <el-divider direction="vertical" />
                          <i class="el-icon-caret-top">{{ collect.likedCount }} 赞</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-star-off">{{ collect.collectCount }}</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-chat-dot-square">{{ collect.commentsCount }}</i>
                          <el-divider direction="vertical" />
                          <i class="el-icon-view">{{ collect.views }}</i>
                          <el-divider direction="vertical" />
                          {{ collect.createOn }} 收藏了这篇文章
                        </div>
                      </div>
                      <el-divider />
                    </div>
                  </el-tab-pane>
                  <el-tab-pane name="fourth">
                    <span slot="label">关注 {{ followCount }}</span>
                    <div v-for="follow in followInfo" :key="follow.id">
                      <div>
                        <div>
                          <router-link
                            :to="{ path: '/user/' + follow.followId }"
                            style="text-decoration:none;color:black"
                          >
                            <div style="float:left">
                              <el-avatar :size="50" :src="follow.avatar" />
                            </div>
                            <h3 style="left:10px;position:relative">{{ follow.username }}</h3>
                          </router-link>
                          <div style="color:#A9A9A9;font-size:14px;left:10px;position:relative">
                            文章：{{ follow.blogCount }}
                            <el-divider direction="vertical" />
                            喜欢：{{ follow.likedCount }}
                            <el-divider direction="vertical" />
                            收藏：{{ follow.collectCount }}
                            <el-divider direction="vertical" />
                            关注：{{ follow.followCount }}
                            <el-divider direction="vertical" />
                            粉丝{{ follow.fansCount }}
                          </div>
                        </div>
                      </div>
                      <el-divider />
                    </div>
                  </el-tab-pane>
                  <el-tab-pane name="fifth">
                    <span slot="label">粉丝 {{ fansCount }}</span>
                    <div v-for="fans in fansList" :key="fans.id">
                      <div>
                        <div>
                          <router-link
                            :to="{ path: '/user/' + fans.userId }"
                            style="text-decoration:none;color:black"
                          >
                            <div style="float:left">
                              <el-avatar :size="50" :src="fans.avatar" />
                            </div>
                            <h3 style="left:10px;position:relative">{{ fans.username }}</h3>
                          </router-link>
                          <div style="color:#A9A9A9;font-size:14px;left:10px;position:relative;">
                            文章：{{ fans.blogCount }}
                            <el-divider direction="vertical" />
                            喜欢：{{ fans.likedCount }}
                            <el-divider direction="vertical" />
                            收藏：{{ fans.collectCount }}
                            <el-divider direction="vertical" />
                            关注：{{ fans.followCount }}
                            <el-divider direction="vertical" />
                            粉丝{{ fans.fansCount }}
                          </div>
                        </div>
                      </div>
                      <el-divider />
                    </div>
                  </el-tab-pane>
                  <el-tab-pane v-if="this.loginInfo.id === this.userInfo.id" name="sixth">
                    <span slot="label">草稿箱 {{ draftList.length }}</span>
                    <div v-for="blog in draftList" :key="blog.id">
                      <div>
                        <div>
                          <router-link
                            :to="{name: 'write', query: {bid: blog.id, status: 0}}"
                            style="text-decoration:none;color:black"
                          >
                            <h3>{{ blog.title }}</h3>
                          </router-link>
                          <br />
                          <div style="color:#A9A9A9">{{ blog.content }}</div>
                        </div>
                        <br />
                        <div style="color:#A9A9A9">{{ blog.createOn }}</div>
                      </div>
                      <el-divider />
                    </div>
                  </el-tab-pane>
                </el-tabs>
              </div>
            </div>
          </div>
        </div>
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
  // params.id获取路径的id，注意这里params后面的属性名要和文件名一致
  asyncData({ params, error }) {
    return accountApi.getAccountInfoById(params.uid).then(response => {
      return {
        userInfo: response.data.data.account // 查看的用户信息
      };
    });
  },
  data() {
    return {
      loginInfo: {}, // 登录的用户信息
      likedList: {}, // 该用户点赞的文章
      collectList: {}, // 该用户收藏的文章
      fansList: {}, // 该用户的粉丝信息列表
      followInfo: {}, // 该用户的关注用户信息列表
      fansCount: "", // 粉丝数量
      followCount: "", // 关注数量
      blogList: "", // 该用户写的文章
      blogCount: "", // 该用户写的博客数量
      collectCount: "", // 收藏文章的数量
      likedBlogCount: "", // 点赞文章的数量
      activeName: "first",
      draftList: "" // 草稿
    };
  },
  mounted() {
    if (this.$route.query.menu == "draft") {
      // 页头点击草稿箱，跳转到第五个标签
      console.log(111);
      this.activeName = "sixth";
    }
    this.getAllBlogsByUserId(this.userInfo.id);
    this.getLikedListByUserId(this.userInfo.id);
    this.getCollectListByUserId(this.userInfo.id);
    this.getFansListByUserId(this.userInfo.id);
    this.getFollowListByUserId(this.userInfo.id);
    this.getLoginInfo();
    if (this.userInfo.id === this.loginInfo.id) {
      // 如果当前用户页面是登录用户的用户页面，就查找草稿箱
      this.getDraftByUserId(this.loginInfo.id);
    }
  },
  methods: {
    // 根据用户id获取该用户关注的所有用户信息
    getFollowListByUserId(userId) {
      followApi.getFollowListByUserId(userId).then(response => {
        this.followInfo = response.data.data.followInfoList;
        this.followCount = response.data.data.followCount;
      });
    },
    // 根据用户id获取该用户所有粉丝信息
    getFansListByUserId(followId) {
      followApi.getFansListByUserId(followId).then(response => {
        this.fansList = response.data.data.fansInfoList;
        this.fansCount = response.data.data.fansCount;
      });
    },
    // 根据用户id查询该用户收藏的文章
    getCollectListByUserId(userId) {
      likedApi.getCollectListByUserId(userId).then(response => {
        this.collectList = response.data.data.collectList;
        this.collectCount = response.data.data.count;
      });
    },
    // 根据用户id查询该用户点赞的文章
    getLikedListByUserId(userId) {
      likedApi.getLikedListByUserId(userId).then(response => {
        this.likedList = response.data.data.indexList;
        this.likedBlogCount = response.data.data.count;
      });
    },
    // 根据用户id查询该用户草稿箱里的博客
    getDraftByUserId(id) {
      blogApi.getDraftByUserId(id).then(response => {
        this.draftList = response.data.data.draftList;
        console.log(this.draftList);
      });
    },
    // 从cookie中获取当前登录用户的信息
    getLoginInfo() {
      if (cookie.get("user_info")) {
        var loginInfoStr = cookie.get("user_info"); // 从cookie中获取登录信息的字符串
        this.loginInfo = JSON.parse(loginInfoStr); // 将字符串转化为JSON格式
      }
    },
    // 根据用户id查询用户的所有博客
    getAllBlogsByUserId(id) {
      blogApi.getAllBlogsByUserId(id).then(response => {
        this.blogList = response.data.data.blogList; // 博客列表
        this.blogCount = response.data.data.blogCount; // 博客数
      });
    }
  }
};
</script>

<style>
h3:hover {
  text-decoration: underline;
  color: #000000;
}
</style>