<template>
  <div style="position:relative;top:60px">
    <div v-for="index in searchList" :key="index.id">
      <div style="width:50%;left:25%;position:relative;top:30px">
        <div style="float:left;text-align:center;">
          <div class="block"><el-avatar :size="40" :src="index.authorAvatar"/></div>
          <div>
            {{ index.authorName }}
          </div>
        </div>
        <div style="left:3%;position:relative">
          <router-link :to="{name: 'blog', query: {bid: index.blogId}}" style="text-decoration:none;color:black">
            <h2>{{ index.blogTitle }}</h2>
          </router-link>
          <p style="color:#808080;position:relative;top:10px;width:80%">{{ index.blogContent }}</p>
        </div>
        <div style="float:right;font-size:13px;">
          <p style="color:#A9A9A9">
            <i class="el-icon-view">{{ index.views }}</i>
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
import blogApi from "@/api/blog";

export default {
  // params.id获取路径的id，注意这里params后面的属性名要和文件名一致
  asyncData({ params, error }) {
    return blogApi.searchContent(params.input).then(response => {
      return {
        searchList: response.data.data.searchRes
      }
    })
  },
  data() {
    return {
      
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
  mounted() {},
  methods: {}
};
</script>

<style>
</style>