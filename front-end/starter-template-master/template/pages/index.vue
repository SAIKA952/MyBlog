<template>
  <div style="position:relative;top:60px">
    <el-backtop />
    <div v-for="index in indexList" :key="index.id">
      <div style="width:50%;left:25%;position:relative;top:30px">
        <div style="float:left;text-align:center;cursor:pointer" @click="gotoUserPage(index.authorId)">
          <div class="block"><el-avatar :size="40" :src="index.authorAvatar"/></div>
          <div>
            {{ index.authorName }}
          </div>
        </div>
        <div style="left:3%;position:relative">
          <!-- <router-link :to="{name: 'blog', query: {bid: index.blogId}}" style="text-decoration:none;color:black">
            <h2>{{ index.blogTitle }}</h2>
          </router-link> -->
          <a :href="'/blog/' + index.blogId" style="text-decoration:none;color:black">
            {{ index.blogTitle }}
          </a>
          <p style="color:#808080;position:relative;top:10px;width:98%">{{ index.blogContent }}</p>
        </div>
        <div style="float:right;font-size:13px;top:20px;position:relative">
          <p style="color:#A9A9A9">
            <i class="el-icon-caret-top">{{ index.likedCount }} 赞</i>
            <el-divider direction="vertical"/>
            <i class="el-icon-star-off"> {{ index.collectCount }}</i>
            <el-divider direction="vertical"/>
            <i class="el-icon-chat-dot-square"> {{ index.commentsCount }}</i>
            <el-divider direction="vertical"/>
            <i class="el-icon-view"> {{ index.views }}</i>
            <el-divider direction="vertical"/>
            {{ index.createOn }}
          </p>
        </div>
        <br/>
        <el-divider/>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account"
import cookie from 'js-cookie'
import blogApi from "@/api/blog"


export default {
  data() {
    return {
      indexList: [],
      userAvatarUrl: ''
    };
  },
  mounted() {
    this.searchAll()
  },
  methods: {
    searchAll() {
      blogApi.searchAll().then(response => {
        console.log(response)
        this.indexList = response.data.data.indexList
      })
    },
    // 点击作者头像，去该用户的主页
    gotoUserPage(id) {
      this.$router.push({ path: '/user/' + id })
    }
  }
}
</script>

<style>
h2:hover {
  text-decoration: underline;
  color: #000000;
}
</style>

