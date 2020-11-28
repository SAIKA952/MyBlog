<template>
  <div>
    <el-backtop />
    <div v-for="index in searchList" :key="index.id">
      <div style="position:relative;top:20px;">
        <div style="position:relative;left:25%;min-width:1000px;width:50%;">
          <div style="text-align:center;cursor:pointer;width:120px;display:inline-block;" @click="gotoUserPage(index.authorId)">
            <div style="width:100%">
              <div class="block"><el-avatar :size="50" :src="index.authorAvatar"/></div>
              <div>
                {{ index.authorName }}
              </div>
            </div>
          </div>
          <div style="left:20px;position:relative;display:inline-block;width:760px;">
            <div>
              <!-- <router-link :to="{name: 'blog', query: {bid: index.blogId}}" style="text-decoration:none;color:black">
                <h2>{{ index.blogTitle }}</h2>
              </router-link> -->
              <div style="position:absolute;top:-55px">
                <a :href="'/blog/' + index.blogId" style="text-decoration:none;color:black">
                  {{ index.blogTitle }}
                </a>
                <p style="color:#808080;position:relative;top:10px;">{{ index.blogContent }}</p>
              </div>
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
          </div>
          <br/>
          <el-divider/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import blogApi from "@/api/blog";

export default {
  // params.id获取路径的id，注意这里params后面的属性名要和文件名一致
  asyncData({ params, error }) {
    return blogApi.searchBlogByContent(params.input).then(response => {
      console.log(response)
      return {
        searchList: response.data.data.searchRes
      }
    })
  },
  data() {
    return {
      searchList: []
    };
  },
  watch: {
    '$route' (to, from) { //监听路由是否变化
      if(to.query.content != from.query.content){
        this.$router.go(0)
      }
    }
  },
  created() {},
  mounted() {
  },
  methods: {
    // 点击作者头像，去该用户的主页
    gotoUserPage(id) {
      this.$router.push({ path: '/user/' + id })
    }
  }
};
</script>

<style>
</style>