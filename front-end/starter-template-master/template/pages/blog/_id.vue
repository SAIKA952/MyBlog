<template>
  <div style="position:relative;min-height:885px;width:100%;min-width:1100px">
    <el-backtop />
    <br />

    <div>
      <div>
        <div style="left:20%;position:relative;width:200px;float:left;z-index:1">
          <div>
            <el-card class="box-card" shadow="hover">
              <div slot="header" class="clearfix" style="height:80px">
                <div style="cursor:pointer">
                  <div style="float:left" @click="gotoUserPage(author.id)">
                    <el-avatar :size="50" :src="author.avatar" />
                  </div>
                  <div style="float:left;position:relative;left:10px">
                    <h4 @click="gotoUserPage(author.id)">{{ author.username }}</h4>
                    <el-button v-if="!isFollow" type="text" @click="follow()">关注</el-button>
                    <el-button v-if="isFollow === 1" type="text" @click="follow()">
                      <i class="el-icon-check">已关注</i>
                    </el-button>
                  </div>
                </div>
                <div style="font-size:10px;float:left;color:#A9A9A9">
                  文章 {{ blogCount + 1 }}
                  <el-divider direction="vertical" />
                  关注 {{ followCount }}
                  <el-divider direction="vertical" />
                  粉丝 {{ fansCount }}
                </div>
              </div>

              <div>
                <p style="font-size:18px">作者的其他文章：</p>
              </div>
              <div v-if="otherBlogs.length > 0">
                <div v-for="blog in otherBlogs" :key="blog.id" style="top:10px;position:relative;">
                  <div>
                    <a
                      :href="'/blog/' + blog.id"
                      style="text-decoration:none;color:black"
                    >{{ blog.title }}</a>
                    <p style="font-size:10px">浏览：{{ blog.views }}</p>
                    <br />
                  </div>
                </div>
              </div>

              <div v-if="otherBlogs == ''">
                <div style="height:50px;position:relative;top:25px;color:#A9A9A9">
                  <p style="font-size:14px;text-align:center">该作者暂无其他文章</p>
                </div>
              </div>
              <el-button
                v-if="this.otherBlogs.length === 5"
                type="text"
                @click="gotoUserPage(author.id)"
              >查看更多</el-button>
            </el-card>
          </div>
        </div>

        <div style="width:900px;left:23%;position:relative;float:left">
          <el-card shadow="hover">
            <br />
            <div style="left:25px;position:relative;">
              <div style="width:95%">
                <h1>{{ blog.title }}</h1>
              </div>
              <br />
              <div class="block" style="position:relative;display:inline;">
                <div>
                  <div style="display:inline;position:relative;">

                    <div>
                      <p style="font-size:14px;color:#A9A9A9">
                        作者：{{ author.username }}
                        <el-divider direction="vertical" />
                        发布于：{{ blog.createOn }}
                        <el-divider direction="vertical" />
                        <i class="el-icon-view">{{ blog.views }}</i>

                        <el-divider v-if="author.id === userInfo.id" direction="vertical" />
                        <i class="el-icon-edit" v-if="author.id === userInfo.id">
                          <el-button type="text" style="color:#A9A9A9" @click="goToEdit()">编辑</el-button>
                        </i>

                        <el-divider v-if="author.id === userInfo.id" direction="vertical" />
                        <i class="el-icon-delete" v-if="author.id === userInfo.id">
                          <el-popconfirm
                            confirmButtonText="确定"
                            @onConfirm="deleteBlog"
                            cancelButtonText="取消"
                            icon="el-icon-info"
                            iconColor="red"
                            title="确定删除这篇博客？"
                          >
                            <el-button slot="reference" type="text" style="color:#A9A9A9">删除</el-button>
                          </el-popconfirm>
                        </i>
                      </p>
                    </div>
                    <br />
                  </div>
                </div>
              </div>
              <!-- <div>{{ author.username }}</div> -->
            </div>
            <!-- 预览样式 -->
            <div class="mavonEditor" style="position:relative;z-index:2">
              <no-ssr>
                <mavon-editor
                  :toolbars="markdownOption"
                  toolbarsBackground="	#F5F5F5"
                  defaultOpen="preview"
                  :boxShadow="false"
                  :subfield="false"
                  v-model="blog.content"
                  previewBackground="#FFFFFF"
                />
              </no-ssr>
            </div>
            <br />

            <!-- 点赞/收藏 -->
            <div style="left:35px;position:relative">
              <el-button v-if="this.isLiked == 0" @click="like()">
                <i style="font-size:14px" class="el-icon-caret-top">{{ blogLikedCount }} 赞同</i>
              </el-button>
              <el-button v-if="this.isLiked == 1" type="primary" @click="like()">
                <i style="font-size:14px" class="el-icon-caret-top">{{ blogLikedCount }} 已赞同</i>
              </el-button>

              <el-button v-if="this.isCollected == 0" @click="collect()">
                <i style="font-size:14px;" class="el-icon-star-off">{{ blogCollectCount }} 收藏</i>
              </el-button>
              <el-button v-if="this.isCollected == 1" type="primary" @click="collect()">
                <i style="font-size:14px;" class="el-icon-star-on">{{ blogCollectCount }} 已收藏</i>
              </el-button>
            </div>
            <br />

            <!-- 评论区 -->
            <div>
              <div>
                <div v-if="userInfo.id > 0" style="float:left;left:25px;position:relative">
                  <el-avatar :size="30" :src="userInfo.avatar" />
                </div>
                <div>
                  <el-input
                    type="textarea"
                    autosize
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="commentForm.content"
                    style="width:85%;left:35px"
                  ></el-input>
                  <el-button
                    type="primary"
                    size="small"
                    style="left:45px;position:relative"
                    @click="submitComment()"
                  >发表</el-button>
                </div>
              </div>
              <br />

              <div style="position:relative;left:75px">
                <i class="el-icon-chat-line-square">全部评论 {{ commentCount }}</i>
                <!-- <div style="display:inline;left:270px;position:relative">
                  <el-button style="color:#A9A9A9" type="text">按时间倒序</el-button>
                  <el-divider direction="vertical" />
                  <el-button style="color:#A9A9A9" type="text">按时间正序</el-button>
                </div> -->
              </div>

              <div>
                <br />
                <div v-if="commentCount > 0">
                  <div
                    v-for="comment in commentInfo"
                    :key="comment.id"
                    style="position:relative;width:80%;left:10%"
                  >
                    <div v-if="!comment.parentId">
                      <div style="height:auto;min-height:50px">
                        <div style="float:left;">
                          <div style="top:5px;position:relative">
                            <div class="block">
                              <el-avatar :size="30" :src="comment.userAvatar" />
                            </div>
                          </div>
                        </div>
                        <div style="left:2%;position:relative">
                          <p>{{ comment.username }}</p>
                          <p style="font-size:8px;color:#A9A9A9">{{ comment.createOn }}</p>
                          <br />
                          <p style="position:relative;left:5%">{{ comment.content }}</p>
                          <a style="left:5%;position:relative;">
                            <el-button type="text" @click="expand(comment)">
                              <p v-if="!comment.flag" style="color:#A9A9A9">回复</p>
                              <p v-if="comment.flag" style="color:#A9A9A9">关闭</p>
                            </el-button>
                            <el-button
                              v-if="userInfo.id === comment.userId"
                              type="text"
                              style="color:#A9A9A9"
                              @click="deleteComment(comment.commentId)"
                            >删除</el-button>
                          </a>

                          <div v-if="comment.flag === true">
                            <div
                              v-if="userInfo.id > 0"
                              style="float:left;left:5%;position:relative"
                            >
                              <el-avatar :size="30" :src="userInfo.avatar" />
                            </div>
                            <div>
                              <el-input
                                type="textarea"
                                autosize
                                :rows="2"
                                placeholder="请输入内容"
                                v-model="replyInfo.content"
                                style="width:70%;left:7%"
                              ></el-input>
                              <el-button
                                type="primary"
                                size="small"
                                style="left:8%;position:relative"
                                @click="replyComment(comment)"
                              >回复</el-button>
                            </div>
                            <br />
                          </div>

                          <!-- 评论回复区 -->
                          <div style="position:relative;left:5%;">
                            <div v-for="rep in commentInfo" :key="rep.id">
                              <div v-if="rep.parentId === comment.commentId">
                                <div style="width:85%">
                                  <el-divider />
                                </div>
                                <div style="position:relative">
                                  <div style="float:left">
                                    <div class="block">
                                      <el-avatar
                                        :size="30"
                                        :src="rep.userAvatar"
                                        style="top:3px;position:relative"
                                      />
                                    </div>
                                  </div>
                                  <div style="left:2%;position:relative">
                                    <p style="display:inline">{{ rep.username }}</p>
                                    <p v-if="rep.toUsername" style="display:inline;color:#A9A9A9">回复</p>
                                    <p
                                      v-if="rep.toUsername"
                                      style="display:inline"
                                    >{{ rep.toUsername }}</p>
                                    <p style="font-size:8px;color:#A9A9A9">{{ rep.createOn }}</p>
                                    <br />

                                    <div style="position:relative;left:5%">
                                      <p>{{ rep.content }}</p>
                                    </div>

                                    <a style="left:5%;position:relative;">
                                      <el-button type="text" @click="expand(rep)">
                                        <p v-if="!rep.flag" style="color:#A9A9A9">回复</p>
                                        <p v-if="rep.flag" style="color:#A9A9A9">关闭</p>
                                      </el-button>
                                      <el-button
                                        v-if="userInfo.id === rep.userId"
                                        type="text"
                                        style="color:#A9A9A9"
                                        @click="deleteComment(rep.commentId)"
                                      >删除</el-button>
                                    </a>
                                  </div>

                                  <div v-if="rep.flag === true">
                                    <div
                                      v-if="userInfo.id > 0"
                                      style="float:left;left:5%;position:relative"
                                    >
                                      <el-avatar :size="30" :src="userInfo.avatar" />
                                    </div>
                                    <div>
                                      <el-input
                                        type="textarea"
                                        autosize
                                        :rows="2"
                                        placeholder="请输入内容"
                                        v-model="replyInfo.content"
                                        style="width:70%;left:7%"
                                      ></el-input>
                                      <el-button
                                        type="primary"
                                        size="small"
                                        style="left:8%;position:relative"
                                        @click="replyTo(rep)"
                                      >回复</el-button>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <el-divider />
                    </div>
                  </div>
                </div>

                <div v-if="commentCount === 0">
                  <br />
                  <div style="height:60px;text-align:center;color:#A9A9A9">目前还没有评论</div>
                </div>
              </div>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import cookie from "js-cookie";
import blogApi from "@/api/blog";
import accountApi from "@/api/account";
import commentApi from "@/api/comment";
import likedApi from "@/api/liked";
import followApi from "@/api/follow";

export default {
  // params.id获取路径的id，注意这里params后面的属性名要和文件名一致
  asyncData({ params, error }) {
    return blogApi.getBlogInfoById(params.id).then(response => {
      return {
        blog: response.data.data.blog,
        commentCount: response.data.data.commentCount,
        author: response.data.data.authorInfo
      };
    });
  },
  data() {
    return {
      userInfo: {}, // 登录的账号信息
      otherBlogs: {}, // 作者的其他博客
      blogLikedCount: "", // 博客点赞数
      blogCollectCount: "", // 博客收藏数
      fansCount: "", // 作者粉丝数
      followCount: "", // 作者的关注数
      blogCount: "", // 作者的博客数
      likeInfo: {}, // 封装点赞的信息
      collectInfo: {}, // 封装收藏的信息
      followUser: {}, // 关注用户时封装用户id和对象id
      isFollow: "", // 作者是否已被关注，0为未关注，1为关注
      isLiked: "",
      isCollected: "",
      isUserLogin: "", // 用户是否登录
      commentForm: {
        // 提交评论时的数据
        blogId: "",
        userId: "",
        content: "",
        parentId: "",
        toId: ""
      },
      commentInfo: {}, // 评论显示的数据
      replyInfo: {
        // 评论回复的信息
        blogId: "",
        userId: "",
        content: "",
        parentId: "",
        toId: ""
      },
      markdownOption: {
        navigation: true, // 导航目录
        readmodel: true // 沉浸式阅读
      }
    };
  },
  watch: {
    $route(to, from) {
      //监听路由是否变化
      if (to.query.bid != from.query.bid) {
        this.$router.go(0);
      }
    }
  },
  mounted() {
    this.initPage(); // 初始化页面
    setTimeout(() => {}, 200);
  },
  methods: {
    // 根据作者id获取粉丝数
    getFansCountByUserId(authorId) {
      followApi.getFansByUserId(authorId).then(response => {
        this.fansCount = response.data.data.count;
      });
    },
    // 根据作者id获取关注数
    getFollowCountByUserId(authorId) {
      followApi.getFollowCountByUserId(authorId).then(response => {
        this.followCount = response.data.data.count;
      });
    },
    // 根据用户id和作者id查看该用户是否有关注这个作者
    isFollowed(userId, followId) {
      if (this.isUserLogin) {
        followApi.isFollowed(userId, followId).then(response => {
          this.isFollow = response.data.data.isFollowed;
        });
      }
    },
    // 关注
    follow() {
      if (!this.userInfo.id) {
        this.$message({
          message: "请先登录",
          type: "error"
        });
      } else if (this.userInfo.id === this.author.id) {
        this.$message({
          message: "不能关注自己",
          type: "error"
        });
      } else {
        if (this.isFollow === 1) {
          this.isFollow = 0;
          this.$message({
            message: "取消关注成功",
            type: "success"
          });
        } else {
          this.isFollow = 1;
          this.$message({
            message: "关注成功",
            type: "success"
          });
        }

        // 封装用户id和关注对象的id
        this.followUser.userId = this.userInfo.id;
        this.followUser.followId = this.author.id;
        followApi.followUser(this.followUser);
      }
    },
    // 查询该用户是否收藏这篇博客
    isUserCollected(blogId, userId) {
      if (this.isUserLogin) {
        likedApi.isUserCollected(blogId, userId).then(response => {
          if (response.data.data.collectInfo != null) {
            this.isCollected = response.data.data.collectInfo.status;
          } else {
            this.isCollected = 0;
          }
        });
      }
    },
    // 获取该博客的收藏数
    getBlogCollectListByBlogId(blogId) {
      likedApi.getBlogCollectListByBlogId(blogId).then(response => {
        this.blogCollectCount = response.data.data.count;
      });
    },
    // 收藏
    collect() {
      if (!this.userInfo.id) {
        // 没登录无法点赞
        this.$message({
          message: "请先登录",
          type: "error"
        });
      } else {
        // 收藏状态切换
        if (this.isCollected === 1) {
          this.isCollected = 0;
          this.blogCollectCount--;
        } else {
          this.isCollected = 1;
          this.blogCollectCount++;
        }

        this.collectInfo.userId = this.userInfo.id;
        this.collectInfo.blogId = this.blog.id;
        likedApi.collect(this.collectInfo);
      }
    },
    // 获取该博客的点赞数
    getBlogLikedCount(blogId) {
      likedApi.getBlogLikedListByBlogId(blogId).then(response => {
        this.blogLikedCount = response.data.data.count;
      });
    },
    // 查询该用户是否点赞
    getLikedInfo(blogId, userId) {
      if (this.isUserLogin) {
        likedApi.getLikedInfo(blogId, userId).then(response => {
          if (response.data.data.likedInfo) {
            this.isLiked = response.data.data.likedInfo.status;
          }
        });
      }
      
    },
    // 👍
    like() {
      if (!this.userInfo.id) {
        // 没登录无法点赞
        this.$message({
          message: "请先登录",
          type: "error"
        });
      } else {
        this.likeInfo.userId = this.userInfo.id; // 设置点赞的用户id
        this.likeInfo.blogId = this.blog.id; // 设置博客的id

        // 点赞状态切换
        if (this.isLiked === 1) {
          this.isLiked = 0;
          this.blogLikedCount--;
        } else {
          this.isLiked = 1;
          this.blogLikedCount++;
        }

        likedApi.liked(this.likeInfo);
      }
    },
    deleteComment(commentId) {
      commentApi.deleteCommentById(commentId).then(response => {
        this.$message({
          message: "删除成功",
          type: "success"
        });
        setTimeout(() => {
          // 延迟函数，1000毫秒后执行
          this.$router.go(0);
        }, 1000);
      });
    },
    // 回复其他用户的评论
    replyTo(comment) {
      this.replyInfo.blogId = comment.blogId;
      this.replyInfo.parentId = comment.parentId;
      this.replyInfo.userId = this.userInfo.id;
      this.replyInfo.toCommentId = comment.commentId;
      this.replyInfo.toUsername = comment.username;
      this.replyInfo.toUserId = comment.userId;
      console.log(this.replyInfo);
      commentApi.submitComment(this.replyInfo).then(response => {
        this.$message({
          message: "回复成功",
          type: "success"
        });
        setTimeout(() => {
          // 延迟函数，1000毫秒后执行
          this.$router.go(0);
        }, 1000);
      });
    },
    // 回复评论
    replyComment(comment) {
      this.replyInfo.blogId = comment.blogId;
      this.replyInfo.parentId = comment.commentId;
      this.replyInfo.userId = this.userInfo.id;
      commentApi.submitComment(this.replyInfo).then(response => {
        this.$message({
          message: "回复成功",
          type: "success"
        });
        setTimeout(() => {
          // 延迟函数，1000毫秒后执行
          this.$router.go(0);
        }, 1000);
      });
    },
    // 点击回复，展开输入框，再次点击消失
    expand(comment) {
      console.log(comment);
      if (comment.flag) {
        comment.flag = false;
      } else {
        comment.flag = true;
      }
      this.$forceUpdate(); // 强制刷新组件
    },
    // 根据博客id查找该博客下的全部评论
    getCommentsByBlogId(id) {
      commentApi.getCommentsByBlogId(id).then(response => {
        this.commentInfo = response.data.data.comments;
      });
    },
    // 发表评论
    submitComment() {
      if (!this.userInfo.id) {
        this.$message({
          message: "请先登录",
          type: "error"
        });
      } else {
        this.commentForm.blogId = this.blog.id;
        this.commentForm.userId = this.userInfo.id;
        commentApi.submitComment(this.commentForm).then(response => {
          this.$message({
            message: "评论成功",
            type: "success"
          });
          setTimeout(() => {
            // 延迟函数，1000毫秒后执行
            this.$router.go(0);
          }, 1000);
        });
      }
    },
    // 删除博客
    deleteBlog() {
      blogApi.deleteBlog(this.blog).then(response => {
        this.$message({
          message: "删除成功",
          type: "success"
        });
      });
    },
    // 去博客编辑页面
    goToEdit() {
      this.$router.push({ name: "write", query: { bid: this.blog.id } });
    },
    // 初始化页面数据
    initPage() {
      this.isLogin()
      this.getBlogsByAuthorId(this.blog); // 根据作者id查询该作者写的博客(取浏览量前5)
      this.getCommentsByBlogId(this.blog.id); // 根据博客id获取所有评论
      this.getBlogLikedCount(this.blog.id); // 根据博客id获取该博客的点赞数量
      this.getBlogCollectListByBlogId(this.blog.id); // 根据博客id获取该博客的收藏数
      if (cookie.get("user_info")) {
        var userInfoStr = cookie.get("user_info"); // 从cookie中获取登录信息的字符串
        this.userInfo = JSON.parse(userInfoStr); // 将字符串转化为JSON格式
        this.getAccountInfo(this.userInfo.id); // 根据用户id获取用户信息
      }
      this.getLikedInfo(this.blog.id, this.userInfo.id); // 根据博客id和用户信息获取该用户的点赞信息
      this.isUserCollected(this.blog.id, this.userInfo.id); // 用户是否收藏这篇博客
      this.isFollowed(this.userInfo.id, this.blog.authorId); // 用户是否关注作者
      this.getFansCountByUserId(this.blog.authorId); // 获取作者的粉丝数
      this.getFollowCountByUserId(this.blog.authorId); // 获取作者的关注数
    },
    // 查询当前登录用户的用户信息
    getAccountInfo(id) {
      if (this.isUserLogin) {
        accountApi.getAccountInfoById(id).then(response => {
          this.userInfo = response.data.data.account;
        });
      }
    },
    // 根据作者id查询作者所有博客
    getBlogsByAuthorId() {
      blogApi.getBlogsByAuthorId(this.blog).then(response => {
        this.otherBlogs = response.data.data.list; // 博客列表(取浏览量前5)
        this.blogCount = response.data.data.blogCount; // 总博客数
      });
    },
    // 点击查看更多，去该用户的主页
    gotoUserPage(id) {
      this.$router.push({ path: "/user/" + id });
    },
    // 账号是否登录
    isLogin() {
      var userInfo = cookie.get("user_info");
      if (userInfo) {
        this.isUserLogin = true
      } else {
        this.isUserLogin = false
      }
    }
  }
};
</script>

<style>
</style>