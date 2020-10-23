<template>
  <div style="width:90%;left:5%;position:relative;top:60px">
    <div style="position:relative;top:30px;">
      <div>
        <el-form label-width="40px" :model="form">
          <el-form-item label="标题">
            <el-input v-model="form.title"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <div>
        <div class="mavonEditor">
          <no-ssr>
            <mavon-editor 
              :toolbars="markdownOption" 
              ref=md
              v-model="form.content" 
              
              @imgAdd="$imgAdd"
            />
          </no-ssr>
        </div>
      </div>

      <div style="top:30px;position:relative">
        <div v-if="this.$route.query.bid">
          <el-button type="primary" plain @click="submit()">发表</el-button>
          <el-button v-if="this.$route.query.status === 0" type="info" plain @click="saveToDraft()">保存</el-button>
        </div>
        <div v-if="!this.$route.query.bid">
          <el-button type="primary" plain @click="submit()">提交</el-button>
          <el-button type="info" plain @click="saveToDraft()">保存</el-button>
        </div>
      </div>
    </div>

    <!-- 预览样式 -->
    <!-- <div class="mavonEditor">
      <no-ssr>
        <mavon-editor
          :toolbarsFlag="false"
          defaultOpen="preview"
          :boxShadow="false"
          :subfield="false"
          v-model="handbook"
        />
      </no-ssr>
    </div>-->
  </div>
</template>
<script>
import cookie from "js-cookie";
import blogApi from "@/api/blog";
import axios from 'axios';
axios.get();

export default {
  data() {
    return {
      // 表单数据
      form: {
        userId: "",
        username: "",
        title: "",
        type: "",
        content: ""
      },
      markdownOption: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true // 预览
      },
      handbook: "#### how to use mavonEditor in nuxt.js",
      idAndUsername: {},
      img_file: []
    };
  },
  created() {
    if (this.$route.query.bid) {
      this.editBlog(this.$route.query.bid);
    }
  },
  methods: {
    // 图片添加
    $imgAdd(pos, file) {
      const isLt10M = file.size / 1024 / 1024 < 10
      if (!isLt10M) {
        this.$message.error('图片大小不能超过 10MB!')
      } else {
        // 第一步.将图片上传到服务器.
        var formdata = new FormData();
        formdata.append('file', file);
        this.img_file[pos] = file;
        axios({
          url: 'http://localhost:8001/oss',
          method: 'post',
          data: formdata,
          headers: { 'Content-Type': 'multipart/form-data' },
        }).then(response => { //这里根据自己具体需求即可
          this.$refs.md.$img2Url(pos, response.data.data.url);
        })
      }
      
    },
    // 修改博客
    update() {
      console.log(this.form)
      blogApi.updateBlog(this.form).then(response => {
        this.$message({
          message: "修改成功",
          type: "success"
        });
      });
    },
    // 编辑博客，数据回显
    editBlog(id) {
      blogApi.editBlog(id).then(response => {
        this.form = response.data.data.blogInfo;
      });
    },
    // 博客提交
    submit() {
      var userInfo = cookie.get("user_info");
      if (userInfo) {
        this.idAndUsername = JSON.parse(userInfo);

        // 将用户的id和用户名一起发送到后端中
        this.form.userId = this.idAndUsername.id;
        this.form.username = this.idAndUsername.username;
        this.form.status = 1 // 设置状态为1，表示正常发布

        blogApi.submitBlog(this.form).then(response => {
          console.log(response);
          this.$message({
            message: "提交成功",
            type: "success"
          });
        });
      }
    },
    // 保存到草稿箱
    saveToDraft() {
      var userInfo = cookie.get("user_info");
      if (userInfo) {
        this.idAndUsername = JSON.parse(userInfo);

        // 将用户的id和用户名一起发送到后端中
        this.form.userId = this.idAndUsername.id;
        this.form.username = this.idAndUsername.username;
        this.form.status = 0 // 设置状态为0，表示未发布

        blogApi.submitBlog(this.form).then(response => {
          console.log(response);
          this.$message({
            message: "保存成功",
            type: "success"
          });
        });
      }
    }
  }
};
</script>

<style scoped>
.mavonEditor {
  width: 100%;
  height: 100%;
}
</style>