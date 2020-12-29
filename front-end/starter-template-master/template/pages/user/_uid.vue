<template>
  <div style="position:relative;">
    <el-backtop />
    <div style="z-index:1">
      <div style="position:relative;left:20%;width:930px;float:left">
        <br />
        <div style="min-height:865px">
          <br />
          <div v-if="isTargetExist" style="left:110px;position:relative">
            <div>
              <div style="float:left">
                <el-avatar :size="70" :src="userInfo.avatar" />
              </div>
              <div style="position:relative;left:20px">
                <div>
                  <div style="display:inline-block">
                    <h4 style="font-size:30px;">{{ userInfo.username }}</h4>
                  </div>
                  <div v-if="userInfo.id !== loginInfo.id" style="display:inline-block">
                    <el-button
                      v-if="!isUserFollowed"
                      style="left:20px;position:relative;top:-5px"
                      type="primary"
                      round
                      size="mini"
                      @click="followUser()"
                    >关注</el-button>
                    <el-button
                      v-if="isUserFollowed"
                      style="left:20px;position:relative;top:-5px"
                      type="primary"
                      round
                      plain
                      size="mini"
                      @click="followUser()"
                    >取消关注</el-button>
                  </div>
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
                <div v-if="this.showIndex === 1">
                  <el-tabs v-model="blogActiveName">
                    <el-tab-pane name="first">
                      <span slot="label">最新</span>
                      <div>
                        <div v-for="blog in blogList" :key="blog.id">
                          <el-card shadow="hover">
                            <router-link
                              :to="{ path: '/blog/' + blog.blogId }"
                              style="text-decoration:none;color:black"
                            >
                              <div>
                                <h3>{{ blog.blogTitle }}</h3>
                                <!-- <el-tag
                                  v-if="blog.blogStatus === -2"
                                  type="info"
                                  style="float:right;"
                                  class="el-icon-warning-outline"
                                >审核中</el-tag>
                                <el-tag
                                  v-if="blog.blogStatus === -3"
                                  type="danger"
                                  style="float:right;"
                                  class="el-icon-circle-close"
                                >审核未通过</el-tag> -->
                                <br />
                                <div style="color:#A9A9A9">{{ blog.blogContent }}</div>
                              </div>
                            </router-link>

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
                          </el-card>
                          <br />
                        </div>
                      </div>
                      <div v-if="!this.blogPageInfo">
                        <div style="text-align:center;height:90px;top:40px;position:relative">
                          <i class="el-icon-loading"></i>
                        </div>
                      </div>
                      <div
                        v-if="this.blogPageInfo.hasNextPage"
                        style="position:relative;left:40%;height:80px;top:10px"
                      >
                        <el-button
                          type="primary"
                          round
                          v-if="!this.isBlogLoading"
                          @click="readMoreBlogs(blogPage)"
                        >查看更多</el-button>
                        <el-button
                          type="primary"
                          round
                          v-if="this.isBlogLoading"
                          :loading="true"
                        >加载中...</el-button>
                      </div>
                      <div
                        v-if="this.blogPageInfo.isLastPage"
                        style="position:relative;left:40%;height:80px;top:10px;color:#A9A9A9"
                      >没有更多博客了</div>
                    </el-tab-pane>
                    <el-tab-pane name="second">
                      <span slot="label">热门</span>
                      <div>
                        <div v-for="blog in hotBlogList" :key="blog.id">
                          <el-card shadow="hover">
                            <router-link
                              :to="{ path: '/blog/' + blog.blogId }"
                              style="text-decoration:none;color:black"
                            >
                              <div>
                                <h3>{{ blog.blogTitle }}</h3>
                                <!-- <el-tag
                                  v-if="blog.blogStatus === -2"
                                  type="info"
                                  style="float:right;"
                                  class="el-icon-warning-outline"
                                >审核中</el-tag>
                                <el-tag
                                  v-if="blog.blogStatus === -3"
                                  type="danger"
                                  style="float:right;"
                                  class="el-icon-circle-close"
                                >审核未通过</el-tag> -->
                                <br />
                                <div style="color:#A9A9A9">{{ blog.blogContent }}</div>
                              </div>
                            </router-link>

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
                          </el-card>
                          <br />
                        </div>
                      </div>
                      <div v-if="!this.hotBlogPageInfo">
                        <div style="text-align:center;height:90px;top:40px;position:relative">
                          <i class="el-icon-loading"></i>
                        </div>
                      </div>
                      <div
                        v-if="this.hotBlogPageInfo.hasNextPage"
                        style="position:relative;left:40%;height:80px;top:10px"
                      >
                        <el-button
                          type="primary"
                          round
                          v-if="!this.isHotBlogLoading"
                          @click="readMoreHotBlogs(hotBlogPage)"
                        >查看更多</el-button>
                        <el-button
                          type="primary"
                          round
                          v-if="this.isHotBlogLoading"
                          :loading="true"
                        >加载中...</el-button>
                      </div>
                      <div
                        v-if="this.hotBlogPageInfo.isLastPage"
                        style="position:relative;left:40%;height:80px;top:10px;color:#A9A9A9"
                      >没有更多博客了</div>
                    </el-tab-pane>
                  </el-tabs>
                </div>
                <div v-if="this.showIndex === 2">
                  <el-tabs v-model="likeActiveName">
                    <el-tab-pane name="first">
                      <span slot="label">喜欢</span>
                      <div v-for="liked in likedList" :key="liked.id">
                        <el-card shadow="hover">
                          <router-link
                            :to="{ path: '/blog/' + liked.blogId }"
                            style="text-decoration:none;color:black"
                          >
                            <div>
                              <h3>{{ liked.blogTitle }}</h3>
                              <br />
                              <div style="color:#A9A9A9">{{ liked.blogContent }}</div>
                            </div>
                          </router-link>
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
                        </el-card>
                        <br />
                      </div>
                      <div v-if="isBlogLoading">
                        <div style="text-align:center;height:90px;top:40px;position:relative">
                          <i class="el-icon-loading"></i>
                        </div>
                      </div>
                    </el-tab-pane>
                    <el-tab-pane name="second">
                      <span slot="label">收藏</span>
                      <div v-for="collect in collectList" :key="collect.id">
                        <el-card shadow="hover">
                          <router-link
                            :to="{ path: '/blog/' + collect.blogId }"
                            style="text-decoration:none;color:black"
                          >
                            <div>
                              <h3>{{ collect.blogTitle }}</h3>
                              <br />
                              <div style="color:#A9A9A9">{{ collect.blogContent }}</div>
                            </div>
                          </router-link>
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
                        </el-card>
                        <br />
                      </div>
                      <div v-if="isBlogLoading">
                        <div style="text-align:center;height:90px;top:40px;position:relative">
                          <i class="el-icon-loading"></i>
                        </div>
                      </div>
                    </el-tab-pane>
                  </el-tabs>
                </div>

                <div v-if="showIndex === 3">
                  <el-tabs v-model="followActiveName">
                    <el-tab-pane name="first">
                      <span slot="label">关注</span>
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
                              粉丝：{{ follow.fansCount }}
                            </div>
                          </div>
                        </div>
                        <el-divider />
                      </div>
                      <div v-if="isBlogLoading">
                        <div style="text-align:center;height:90px;top:40px;position:relative">
                          <i class="el-icon-loading"></i>
                        </div>
                      </div>
                    </el-tab-pane>
                    <el-tab-pane name="second">
                      <span slot="label">粉丝</span>
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
                              粉丝：{{ fans.fansCount }}
                            </div>
                          </div>
                        </div>
                        <el-divider />
                      </div>
                      <div v-if="isBlogLoading">
                        <div style="text-align:center;height:90px;top:40px;position:relative">
                          <i class="el-icon-loading"></i>
                        </div>
                      </div>
                    </el-tab-pane>
                  </el-tabs>
                </div>
                <div v-if="this.showIndex === 4">
                  <br />
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
                </div>
                <!-- <el-tabs v-model="activeName" stretch>
                  <el-tab-pane name="first">
                    <span slot="label">文章 {{ blogCount }}</span>
                    <div>
                      <div v-for="blog in blogList" :key="blog.id">
                        <el-card shadow="hover">
                          <router-link
                            :to="{ path: '/blog/' + blog.blogId }"
                            style="text-decoration:none;color:black"
                          >
                            <div>
                              <h3>{{ blog.blogTitle }}</h3>
                              <el-tag
                                v-if="blog.blogStatus === -2"
                                type="info"
                                style="float:right;"
                                class="el-icon-warning-outline"
                              >审核中</el-tag>
                              <el-tag
                                v-if="blog.blogStatus === -3"
                                type="danger"
                                style="float:right;"
                                class="el-icon-circle-close"
                              >审核未通过</el-tag>
                              <br />
                              <div style="color:#A9A9A9">{{ blog.blogContent }}</div>
                            </div>
                          </router-link>

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
                        </el-card>
                        <br />
                      </div>
                    </div>
                    <div v-if="!this.blogPageInfo">
                      <div style="text-align:center;height:90px;top:40px;position:relative">
                        <i class="el-icon-loading"></i>
                      </div>
                    </div>
                    <div
                      v-if="this.blogPageInfo.hasNextPage"
                      style="position:relative;left:40%;height:80px;top:10px"
                    >
                      <el-button
                        type="primary"
                        round
                        v-if="!this.isBlogLoading"
                        @click="readMoreBlogs(blogPage)"
                      >查看更多</el-button>
                      <el-button
                        type="primary"
                        round
                        v-if="this.isBlogLoading"
                        :loading="true"
                        @click="readMoreBlogs(blogPage)"
                      >加载中...</el-button>
                    </div>
                    <div
                      v-if="this.blogPageInfo.isLastPage"
                      style="position:relative;left:40%;height:80px;top:10px;color:#A9A9A9"
                    >没有更多博客了</div>
                  </el-tab-pane>
                  <el-tab-pane name="second">
                    <span slot="label">喜欢 {{ likedBlogCount }}</span>
                    <div v-for="liked in likedList" :key="liked.id">
                      <el-card shadow="hover">
                        <router-link
                          :to="{ path: '/blog/' + liked.blogId }"
                          style="text-decoration:none;color:black"
                        >
                          <div>
                            <h3>{{ liked.blogTitle }}</h3>
                            <br />
                            <div style="color:#A9A9A9">{{ liked.blogContent }}</div>
                          </div>
                        </router-link>
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
                      </el-card>
                      <br />
                    </div>
                    <div v-if="isBlogLoading">
                      <div style="text-align:center;height:90px;top:40px;position:relative">
                        <i class="el-icon-loading"></i>
                      </div>
                    </div>
                  </el-tab-pane>
                  <el-tab-pane name="third">
                    <span slot="label">收藏 {{ collectCount }}</span>
                    <div v-for="collect in collectList" :key="collect.id">
                      <el-card shadow="hover">
                        <router-link
                          :to="{ path: '/blog/' + collect.blogId }"
                          style="text-decoration:none;color:black"
                        >
                          <div>
                            <h3>{{ collect.blogTitle }}</h3>
                            <br />
                            <div style="color:#A9A9A9">{{ collect.blogContent }}</div>
                          </div>
                        </router-link>
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
                      </el-card>
                      <br />
                    </div>
                    <div v-if="isBlogLoading">
                      <div style="text-align:center;height:90px;top:40px;position:relative">
                        <i class="el-icon-loading"></i>
                      </div>
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
                            粉丝：{{ follow.fansCount }}
                          </div>
                        </div>
                      </div>
                      <el-divider />
                    </div>
                    <div v-if="isBlogLoading">
                      <div style="text-align:center;height:90px;top:40px;position:relative">
                        <i class="el-icon-loading"></i>
                      </div>
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
                            粉丝：{{ fans.fansCount }}
                          </div>
                        </div>
                      </div>
                      <el-divider />
                    </div>
                    <div v-if="isBlogLoading">
                      <div style="text-align:center;height:90px;top:40px;position:relative">
                        <i class="el-icon-loading"></i>
                      </div>
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
                    <div v-if="!isBlogLoading">
                      <div style="text-align:center;height:90px;top:40px;position:relative">
                        <i class="el-icon-loading"></i>
                      </div>
                    </div>
                  </el-tab-pane>
                </el-tabs>-->
              </div>
            </div>
          </div>

          <div v-if="!isTargetExist">
            <div>
              <el-card>
                <div style="height:100px;text-align:center;top:35px;position:relative">
                  <i class="el-icon-circle-close">该用户不存在</i>
                  <div @click="goBack()">
                    <h4 id="goBack">返回上一页</h4>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </div>
      </div>

      <div v-if="isTargetExist" style="left:15%;width:200px;top:50px;float:left;position:relative">
        <div>
          <el-card shadow="hover">
            <div id="show1" @click="show(1)">
              <p style="float:left" v-if="userInfo.id === loginInfo.id">我</p>
              <p style="float:left" v-if="userInfo.id !== loginInfo.id">Ta</p>的文章
            </div>
            <br />
            <div id="show2" @click="show(2)">
              <p style="float:left" v-if="userInfo.id === loginInfo.id">我</p>
              <p style="float:left" v-if="userInfo.id !== loginInfo.id">Ta</p>
              <p>的喜欢/收藏</p>
            </div>
            <br />
            <div id="show3" @click="show(3)">
              <p style="float:left" v-if="userInfo.id === loginInfo.id">我</p>
              <p style="float:left" v-if="userInfo.id !== loginInfo.id">Ta</p>
              <p>的关注/粉丝</p>
            </div>
            <div id="show4" @click="show(4)" v-if="userInfo.id === loginInfo.id">
              <el-divider />
              <p>草稿箱</p>
            </div>
          </el-card>
        </div>
        <br />

        <div>
          <el-card shadow="hover">
            <p>入站时间：{{ userInfo.createOn.substring(0,10) }}</p>
            <br />
            <p>最近上线：{{ userInfo.lastLogin.substring(0,10) }}</p>
          </el-card>
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
      if (response.data.data.account) {
        return {
          userInfo: response.data.data.account, // 查看的用户信息
          isTargetExist: true
        };
      } else {
        return {
          isTargetExist: false
        };
      }
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
      hotBlogList: "",
      blogCount: "", // 该用户写的博客数量
      collectCount: "", // 收藏文章的数量
      likedBlogCount: "", // 点赞文章的数量
      blogActiveName: "first",
      likeActiveName: "first",
      followActiveName: "first",
      draftList: "", // 草稿
      isUserLogin: "", // 是否登录
      menu: "",
      showIndex: "",
      follow: {}, // 封装关注信息
      isUserFollowed: "", // 用户是否关注对象
      blogPage: 1, // 博客分页的页数
      hotBlogPage: 1,
      blogPageInfo: "", // 文章的分页信息
      hotBlogPageInfo: "",
      isBlogLoading: false, // 是否正在加载博客
      isHotBlogLoading: false

    };
  },
  watch: {
    $route() {
      this.menu = this.$route.query.menu;
    },
    menu() {
      if (this.menu == "draft") {
        // 页头点击草稿箱，跳转到第五个标签
        this.showIndex = 4;
      } else if (this.menu == "like"){
        this.showIndex = 2;
      } else if (this.menu == "collect") {
        this.showIndex = 2;
      } else if (this.menu == "blog") {
        this.showIndex = 1;
      }
    },
    blogList(val, oldVal) {
      // 监听博客列表，如果发生变化，isBlogLoading变为false
      this.isBlogLoading = false;
    },
    hotBlogList(val, oldVal) {
      // 监听博客列表，如果发生变化，isBlogLoading变为false
      this.isHotBlogLoading = false;
    }
  },
  mounted() {
    if (this.isTargetExist) {
      
      this.init();
    }
  },
  methods: {
    init() {

      if (this.$route.query.menu) {
        this.menu = this.$route.query.menu;
        if (this.$route.query.menu == "draft") {
          // 页头点击草稿箱，跳转到第五个标签
          this.showIndex = 4;
        } else if(this.$route.query.menu == 'like') {
          this.showIndex = 2
        } else if (this.$route.query.menu == "collect") {
          this.showIndex = 2;
          this.likeActiveName = "second"
        } else if (this.$route.query.menu == "blog") {
          this.showIndex = 1;
        }
      }
      this.showIndex = 1;

      this.getLoginInfo();

      this.getAllBlogsByUserId(this.userInfo.id, this.blogPage);
      this.getHotBlogsByUserId(this.userInfo.id, this.hotBlogPage);

      this.getLikedListByUserId(this.userInfo.id);
      this.getCollectListByUserId(this.userInfo.id);
      this.getFansListByUserId(this.userInfo.id);
      this.getFollowListByUserId(this.userInfo.id);
      this.getBlogCountByUserId(this.userInfo.id);

      if (this.userInfo.id === this.loginInfo.id) {
        // 如果当前用户页面是登录用户的用户页面，就查找草稿箱
        this.getDraftByUserId(this.loginInfo.id);
      }
      this.isFollow(this.loginInfo.id, this.userInfo.id); // 登录用户是否关注这个用户
    },
    // 当前登录用户是否关注当前显示用户
    isFollow(userId, followId) {
      followApi.isFollowed(userId, followId).then(response => {
        this.isUserFollowed = response.data.data.isFollowed;
      });
    },
    // 关注/取关用户
    followUser() {
      if (this.isUserLogin) {
        this.follow.userId = this.loginInfo.id;
        this.follow.followId = this.userInfo.id;
        followApi.followUser(this.follow).then(response => {
          if (this.isUserFollowed) {
            this.isUserFollowed = 0;
            this.fansCount--;
            this.$message({
              message: "取关成功",
              type: "success"
            });
          } else {
            this.$message({
              message: "关注成功",
              type: "success"
            });
            this.fansCount++;
            this.isUserFollowed = 1;
          }
        });
      } else {
        this.$message({
          message: "请先登录",
          type: "error"
        });
      }
    },
    // 根据用户id获取该用户关注的所有用户信息
    getFollowListByUserId(userId) {
      followApi.getFollowListByUserId(userId).then(response => {
        this.followInfo = response.data.data.followInfoList;
        this.followCount = response.data.data.followCount;
      });
    },
    // 根据用户id获取该用户的所有粉丝信息
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
      });
    },
    // 获取用户博客数
    getBlogCountByUserId(id) {
      blogApi.getBlogCountByUserId(id).then(response => {
        this.blogCount = response.data.data.blogCount;
      });
    },
    // 从cookie中获取当前登录用户的信息
    getLoginInfo() {
      if (cookie.get("user_info")) {
        var loginInfoStr = cookie.get("user_info"); // 从cookie中获取登录信息的字符串
        this.loginInfo = JSON.parse(loginInfoStr); // 将字符串转化为JSON格式
        this.isUserLogin = true;
      } else {
        this.isUserLogin = false;
      }
    },
    // 根据用户id查询用户的所有博客
    getAllBlogsByUserId(id, blogPage) {
      blogApi.getAllBlogsByUserId(id, blogPage).then(response => {
          this.isBlogLoading = true;
          // console.log(response.data);
          this.blogPageInfo = response.data.data.pageInfo;
          this.blogList = response.data.data.pageInfo.list; // 博客列表
        });
    },
    // 根据用户id查询用户热门文章（按照浏览量降序）
    getHotBlogsByUserId(id, hotBlogPage) {
      blogApi.getHotBlogsByUserId(id, hotBlogPage).then(response => {
          this.isHotBlogLoading = true;
          // console.log(response.data);
          this.hotBlogPageInfo = response.data.data.pageInfo;
          this.hotBlogList = response.data.data.pageInfo.list; // 博客列表
        });
    },
    // 加载更多博客
    readMoreBlogs(blogPage) {
      this.isBlogLoading = true;
      this.blogPage = this.blogPage + 1;
      blogApi
        .getAllBlogsByUserId(
          this.userInfo.id,
          this.blogPage
        )
        .then(response => {
          this.blogPageInfo = response.data.data.pageInfo;
          this.blogList = this.blogList.concat(
            response.data.data.pageInfo.list
          );
        });
    },
    // 加载更多热门博客
    readMoreHotBlogs(hotBlogPage) {
      this.isHotBlogLoading = true;
      this.hotBlogPage = this.hotBlogPage + 1;
      blogApi
        .getHotBlogsByUserId(
          this.userInfo.id,
          this.hotBlogPage
        )
        .then(response => {
          this.hotBlogPageInfo = response.data.data.pageInfo;
          this.hotBlogList = this.hotBlogList.concat(
            response.data.data.pageInfo.list
          );
        });
    },
    goBack() {
      this.$router.go(-1);
    },
    show(index) { // 切换显示
      if (index === 1) {
        this.showIndex = 1;
      } else if (index === 2) {
        this.showIndex = 2;
      } else if (index === 3) {
        this.showIndex = 3;
      } else if (index === 4) {
        this.showIndex = 4;
      }
    }
  }
};
</script>

<style>
#goBack:hover {
  text-decoration: underline;
  color: #2f44ff;
  cursor: pointer;
}
#show1,
#show2,
#show3,
#show4 {
  position: relative;
  cursor: pointer;
  font-size: 18px;
  color: #A9A9A9
}
#show1:hover,
#show2:hover,
#show3:hover,
#show4:hover {
  color: black
}
</style>