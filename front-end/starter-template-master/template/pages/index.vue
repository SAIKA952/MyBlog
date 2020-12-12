<template>
  <div>
    <el-backtop />
    <div v-for="index in indexList" :key="index.id">
      <div style="position:relative;left:25%;top:20px;min-width:1000px;width:50%;">
        <div>
          <div
            style="text-align:center;cursor:pointer;width:120px;display:inline-block;"
            @click="gotoUserPage(index.authorId)"
          >
            <div style="width:100%">
              <div class="block">
                <el-avatar :size="50" :src="index.authorAvatar" />
              </div>
              <div>{{ index.authorName }}</div>
            </div>
          </div>
          <div style="left:20px;position:relative;display:inline-block;width:760px;">
            <div>
              <!-- <router-link :to="{name: 'blog', query: {bid: index.blogId}}" style="text-decoration:none;color:black">
                <h2>{{ index.blogTitle }}</h2>
              </router-link>-->
              <div
                style="position:absolute;top:-55px;cursor:pointer"
                @click="gotoBLogPage(index.blogId)"
              >
                <a style="text-decoration:none;color:black">{{ index.blogTitle }}</a>
                <p style="color:#808080;position:relative;top:10px;">{{ index.blogContent }}</p>
              </div>
            </div>
            <div style="float:right;font-size:13px;left:70px;top:20px;position:relative">
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
          <br />
          <el-divider />
        </div>
      </div>
    </div>

    <!-- 分页条 -->
    <!-- <div style="position:relative;height:50px">
      <div class="block">
        <el-pagination
          @current-change="searchAll"
          :page-size="indexPageInfo.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="indexPageInfo.total"
          hide-on-single-page
        ></el-pagination>
      </div>
    </div> -->
    <div style="position:relative;height:80px;left:50%;top:30px" v-if="this.indexPageInfo.hasNextPage">
      <el-button type="primary" v-if="!this.isLoading" round @click="readMore(page)">阅读更多</el-button>
      <el-button type="primary" v-if="this.isLoading" :loading="true" round>加载中...</el-button>  
    </div>

    <div style="position:relative;height:80px;left:50%;top:30px;color:#A9A9A9" v-if="this.indexPageInfo.isLastPage">
      没有更多内容了
    </div>

  </div>
</template>

<script>
import accountApi from "@/api/account";
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
      isLoading: false
    };
  },
  watch: {
    moreIndexList(val, oldVal) {
      this.isLoading = false
    }
  },
  mounted() {
    this.searchAll(this.page);
  },
  methods: {
    searchAll(page) {
      blogApi.searchAll(page).then(response => {
        this.indexPageInfo = response.data.data.pageInfo;
        console.log(this.indexPageInfo)
        this.indexList = this.indexPageInfo.list;
      });
    },
    // 点击阅读更多
    readMore(page) { // 传入page，让page+1作为参数查找页面
      this.isLoading = true
      this.page = page + 1
      blogApi.searchAll(this.page).then(response => {
        this.indexPageInfo = response.data.data.pageInfo
        
        this.moreIndexList = this.indexPageInfo.list
        // 将查出来的数据与原来的数据indexList相加
        // 两个数组相加用concat
        this.indexList = this.indexList.concat(this.moreIndexList)
      })
    },
    // 点击作者头像，去该用户的主页
    gotoUserPage(id) {
      this.$router.push({ path: "/user/" + id });
    },
    gotoBLogPage(bid) {
      this.$router.push({ path: "/blog/" + bid });
    }
  }
};
</script>

<style>
h2:hover {
  text-decoration: underline;
  color: #000000;
}
</style>

