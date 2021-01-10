<template>
  <div>
    <div style="width:1300px;left:15%;position:relative;top:80px">
      <div style="text-align:center;position:relative;">
        <h1 style="cursor:pointer;color:black" @click="searchAllAccount(1)">后台管理系统</h1>
      </div>
      <br />
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="博客审核" name="first">
          <el-table :data="blogCheckList" border style="width: 100%">
            <el-table-column
              fixed
              prop="id"
              label="博客id"
              width="150">
            </el-table-column>
            <el-table-column
              prop="authorId"
              label="作者id"
              width="120">
            </el-table-column>
            <el-table-column
              prop="title"
              label="博客标题"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="content"
              label="博客内容"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="createOn"
              label="发布时间"
              width="140">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="100">
              <template slot-scope="scope">
                <el-button @click="check(scope.row)" type="text" size="small">审核</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="博客管理" name="second">
          <div>
            <div>
              <el-input style="width:20%;position:relative;" v-model="searchBlogContent" placeholder="请输入内容" />
              <el-button style="position:relative" type="primary" icon="el-icon-search" plain circle @click="searchBlog()"></el-button>
            </div>
            <br />
            <div>
              <el-table :data="blogList" stripe style="width: 100%" border fit>
                <el-table-column prop="id" label="id" width="70"></el-table-column>
                <el-table-column prop="authorId" label="作者id" width="70"></el-table-column>
                <el-table-column prop="title" label="标题" show-overflow-tooltip></el-table-column>
                <el-table-column prop="content" label="内容" show-overflow-tooltip></el-table-column>
                <el-table-column prop="views" label="浏览量" width="70"></el-table-column>
                <el-table-column prop="status" label="博客状态" width="80"></el-table-column>
                <el-table-column prop="createOn" label="博客发表时间" width="150"></el-table-column>
                <el-table-column label="操作" width="160">
                  <template slot-scope="scope">
                    <el-button size="mini" @click="handleBlogEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-popconfirm title="确认删除这条记录？此操作不可复原" @onConfirm="handleBlogDelete(scope.$index, scope.row)">
                      <el-button size="mini" type="danger" slot="reference">删除</el-button>
                    </el-popconfirm>
                  </template>
                </el-table-column>
              </el-table>
            </div>

            <!-- 分页条 -->
            <div style="position:relative;height:50px;top:20px;left:30%">
              <div class="block">
                <el-pagination
                  @current-change="gotoPageBlog"
                  :page-size="blogPageInfo.pageSize"
                  layout="total, prev, pager, next, jumper"
                  :total="blogPageInfo.total"
                  hide-on-single-page
                ></el-pagination>
              </div>
            </div>

          </div>
        </el-tab-pane>

        <el-tab-pane label="用户管理" name="third">
          <div>
            <div>
              <el-input style="width:20%;position:relative;" v-model="searchAccountContent" placeholder="请输入内容" />
              <el-button style="position:relative" type="primary" icon="el-icon-search" plain circle @click="searchAccount()"></el-button>
            </div>
            <br />
            <div>
              <el-table :data="accountList" stripe style="width: 100%" border fit>
                <el-table-column prop="id" label="id" width="50"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="password" label="密码" width="120"></el-table-column>
                <el-table-column prop="phone" label="手机" width="120"></el-table-column>
                <el-table-column prop="sex" label="性别" width="50"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
                <el-table-column prop="birthday" label="生日" width="120"></el-table-column>
                <el-table-column prop="status" label="账号状态" width="80"></el-table-column>
                <el-table-column prop="permission" label="账号权限" width="80"></el-table-column>
                <el-table-column prop="lastLogin" label="最后登录时间"></el-table-column>
                <el-table-column prop="createOn" label="账号注册时间"></el-table-column>
                <el-table-column label="操作" width="160">
                  <template slot-scope="scope">
                    <el-button size="mini" @click="handleAccountEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-popconfirm title="确认删除这条记录？此操作不可复原" @onConfirm="handleAccountDelete(scope.$index, scope.row)">
                      <el-button size="mini" type="danger" slot="reference">删除</el-button>
                    </el-popconfirm>
                  </template>
                </el-table-column>
              </el-table>
            </div>

            <!-- 分页条 -->
            <div style="position:relative;height:50px;top:20px;left:30%"  >
              <div class="block">
                <el-pagination
                  @current-change="gotoPageAccount"
                  :page-size="accountPageInfo.pageSize"
                  layout="total, prev, pager, next, jumper"
                  :total="accountPageInfo.total"
                  hide-on-single-page
                ></el-pagination>
              </div>
            </div>

          </div>
        </el-tab-pane>
      </el-tabs>
    </div>

    <el-dialog title="账号数据" :visible.sync="dialogAccountFormVisible">
      <el-form :model="editAccountForm">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="editAccountForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="editAccountForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="editAccountForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-radio-group v-model="editAccountForm.sex">
            <el-radio :label="1">
              <i class="el-icon-male">男</i>
            </el-radio>
            <el-radio :label="0">
              <i class="el-icon-female">女</i>
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="editAccountForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="生日" :label-width="formLabelWidth">
          <el-date-picker v-model="editAccountForm.birthday" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="editAccountForm.status" placeholder="请选择">
            <el-option
              v-for="status in statusOption"
              :key="status.value"
              :label="status.label"
              :value="status.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限" :label-width="formLabelWidth">
          <el-select v-model="editAccountForm.permission" placeholder="请选择">
            <el-option
              v-for="permission in permissionOption"
              :key="permission.value"
              :label="permission.label"
              :value="permission.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false, updateAccount()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="博客数据" :visible.sync="dialogBlogFormVisible">
      <el-form :model="editBlogForm">
        <el-form-item label="作者id" :label-width="formLabelWidth">
          <el-input v-model="editBlogForm.authorId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="editBlogForm.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="editBlogForm.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="浏览量" :label-width="formLabelWidth">
          <el-input v-model="editBlogForm.views" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="博客状态" :label-width="formLabelWidth">
          <el-select v-model="editBlogForm.status" placeholder="请选择">
            <el-option
              v-for="status in blogStatusOption"
              :key="status.value"
              :label="status.label"
              :value="status.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false, updateBlog()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="审核博客" :visible.sync="dialogCheckingBlogFormVisible">
      <el-form :model="editCheckingForm">
        <el-form-item label="作者id" :label-width="formLabelWidth">
          <el-input v-model="editCheckingForm.authorId" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="editCheckingForm.title" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" :label-width="formLabelWidth">
          <no-ssr>
              <mavon-editor
                :toolbarsFlag="false"
                defaultOpen="edit"
                :boxShadow="false"
                :subfield="false"
                v-model="editCheckingForm.content"
                previewBackground="#FFFFFF"
                />
            </no-ssr>
        </el-form-item>
        <el-form-item label="是否通过" :label-width="formLabelWidth">
          <el-radio v-model="isPass" label="-3">否</el-radio>
          <el-radio v-model="isPass" label="1">是</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCheckingBlogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogCheckingBlogFormVisible = false, checked(isPass)">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import accountApi from "@/api/account";
import blogApi from "@/api/blog";
import cookie from 'js-cookie'

export default {
  layout: 'blank',
  data() {
    return {
      accountList: [],
      blogList: [],
      accountPageInfo: {},
      blogPageInfo: {},
      accountPage: 1,
      blogPage: 1,
      dialogAccountFormVisible: false,
      dialogBlogFormVisible: false,
      dialogCheckingBlogFormVisible: false,
      editAccountForm: {},
      editBlogForm: {},
      editCheckingForm: {},
      isPass: "1", // 是否通过
      blogCheckList: [], // 博客审核的list
      formLabelWidth: "120px",
      blogStatusOption: [{
        value: '0',
        label: '0'
      },{
        value: '1',
        label: '1'
      },{
        value: '-1',
        label: '-1'
      },{
        value: '-2',
        label: '-2'
      }],
      statusOption: [{
        value: '0',
        label: '0'
      },{
        value: '1',
        label: '1'
      },{
        value: '-1',
        label: '-1'
      }],
      permissionOption: [{
        value: '0',
        label: '0'
      },{
        value: '1',
        label: '1'
      },{
        value: '2',
        label: '2'
      }],
      searchAccountContent: '', // 账号管理的搜索框内容
      searchBlogContent: '', // 博客管理的搜索框内容
      // searchedAccountList: [],
      activeName: 'first'
    };
  },
  created() {},
  mounted() {
    this.initPage()
  },
  destroyed() {
    cookie.remove('admin_info', { domain: '115.29.209.156' })
  },
  methods: {
    // 初始化界面数据
    initPage() {
      if (cookie.get('admin_info')) {
        this.searchAllAccount(this.accountPage);
        this.searchAllBlogs(this.blogPage);
        this.searchCheckingBlog()
      } else {
        this.$message({
          message: "请先登录",
          type: "error"
        });
        this.$router.push({ path: '/mlogin' })
      }
      
    },
    // 确认审核
    checked(isPass) {
      this.editCheckingForm.status = isPass
      blogApi.updateBlog(this.editCheckingForm).then(response => {
        this.$message({
          message: "审核成功",
          type: "success"
        });
        this.dialogBlogFormVisible = false
      })
    },
    // 审核文章
    check(scope, row) {
      this.dialogCheckingBlogFormVisible = true
      this.editCheckingForm = scope
      console.log(this.editCheckingForm.content)
    },
    // 查找正在审核的文章
    searchCheckingBlog() {
      blogApi.searchCheckingBlog().then(response => {
        this.blogCheckList = response.data.data.checkingBlog
      })
    },
    // 更新博客
    updateBlog() {
      blogApi.updateBlog(this.editBlogForm).then(response => {
          this.$message({
          message: "修改成功",
          type: "success"
        });
        this.dialogBlogFormVisible = false
      })
    },
    // 查找博客
    searchBlog() {
      if (this.searchBlogContent.trim() === '') { // 如果输入的内容是空格或是空的，就查找所有数据
        this.searchAllBlogs(1)
      } else {
        blogApi.searchContent(this.searchBlogContent).then(response => {
          this.blogPageInfo = response.data.data.pageInfo
          this.blogList = response.data.data.pageInfo.list
        })
      }
    },
    // 博客管理跳转页面
    gotoPageBlog(res) {
      blogApi.searchAllBlogs(res).then(response => {
        this.blogPageInfo = response.data.data.pageInfo;
        this.blogList = response.data.data.pageInfo.list;
      });
    },
    searchAllBlogs(page) {
      blogApi.searchAllBlogs(page).then(response => {
        this.blogPageInfo = response.data.data.pageInfo
        this.blogList = response.data.data.pageInfo.list
      })
    },
    handleBlogEdit(index, row) {
      this.dialogBlogFormVisible = true
      this.editBlogForm = row
    },
    handleBlogDelete(index, row) {
      blogApi.deleteBlogByBlogId(row.id).then(response => {
        this.$message({
          message: "删除成功",
          type: "success"
        });
        this.router.go(0)
      })
    },
    // 查找账号
    searchAccount() {
      if (this.searchAccountContent.trim() === '') { // 如果输入的内容是空格或是空的，就查找所有数据
        this.searchAllAccount(1)
      } else {
        accountApi.searchContent(this.searchAccountContent).then(response => {
          this.accountPageInfo = response.data.data.pageInfo
          this.accountList = response.data.data.pageInfo.list
        })
      }
    },
    // 更新账号信息
    updateAccount() {
      accountApi.updateAccount(this.editAccountForm).then(response => {
        this.$message({
          message: "修改成功",
          type: "success"
        });
        this.dialogAccountFormVisible = false
      })
    },
    // 用户管理跳转页面
    gotoPageAccount(res) {
      accountApi.searchAllAccount(res).then(response => {
        this.accountPageInfo = response.data.data.pageInfo;
        this.accountList = response.data.data.pageInfo.list;
      });
    },
    // 分页查询所有账号
    searchAllAccount(page) {
      accountApi.searchAllAccount(page).then(response => {
        this.accountPageInfo = response.data.data.pageInfo;
        this.accountList = response.data.data.pageInfo.list;
      });
    },
    handleAccountEdit(index, row) {
      this.dialogAccountFormVisible = true;
      this.editAccountForm = row;
    },
    handleAccountDelete(index, row) {
      accountApi.deleteAccount(row.id).then(response => {
        this.$message({
          message: "删除成功",
          type: "success"
        });
        this.$router.go(0)
      })
    }
  }
};
</script>

<style>
</style>