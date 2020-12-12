import request from '@/utils/request'

export default {
  // 博客提交
  submitBlog(form) {
    return request({
      url: `/blog/submit`,
      method: 'post',
      data: form
    })
  },
  // 查找所有博客
  searchAll(page) {
    return request({
      url: `/blog/searchAll/${page}`,
      method: 'get'
    })
  },
  // 查找所有博客（后台管理部分）
  searchAllBlogs(page) {
    return request({
      url: `/blog/searchAllBlogs/${page}`,
      method: 'get'
    })
  },
  // 根据博客id查询博客信息
  getBlogInfoById(blogId) {
    return request({
      url: `/blog/getBlogInfoById/${blogId}`,
      method: 'get'
    })
  },
  // 根据内容查询博客
  searchBlogByContent(content) {
    return request({
      url: `/blog/search/${content}`,
      method: 'get'
    })
  },
  // 根据作者id查询所有博客
  getBlogsByAuthorId(blog) {
    return request({
      url: `/blog/getBlogsByAuthorId`,
      method: 'post',
      data: blog
    })
  },
  // 编辑博客数据回显
  editBlog(id) {
    return request({
      url: `/blog/editBlog/${id}`,
      method: 'get'
    })
  },
  // 更新博客
  updateBlog(form) {
    return request({
      url: `/blog/updateBlog`,
      method: 'post',
      data: form
    })
  },
  // 删除博客
  deleteBlog(form) {
    return request({
      url: `/blog/deleteBlog`,
      method: 'post',
      data: form
    })
  },
  // 根据用户id查询该用户的所有博客
  getAllBlogsByUserId(userId, isWithIdentify) {
    return request({
      url: `/blog/getAllBlogsByUserId/${userId}/${isWithIdentify}`,
      method: 'get'
    })
  },
  // 根据用户id查询该用户草稿箱里的博客
  getDraftByUserId(id) {
    return request({
      url: `/blog/getDraftByUserId/${id}`,
      method: 'get'
    })
  },
  // 通过内容查询博客
  searchContent(content) {
    return request({
      url: `/blog/searchContent/${content}`,
      method: 'get'
    })
  },
  // 删除数据库里的博客信息
  deleteBlogByBlogId(blogId) {
    return request({
      url: `/blog/deleteBlogByBlogId/${blogId}`,
      method: 'get'
    })
  },
  // 查找正在审核的文章
  searchCheckingBlog() {
    return request({
      url: `/blog/searchCheckingBlog`,
      method: 'get'
    })
  }

}
