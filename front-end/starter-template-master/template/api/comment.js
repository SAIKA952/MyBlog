import request from '@/utils/request'

export default {
  // 博客提交
  submitComment(comment) {
    return request({
      url: `/comment/submit`,
      method: 'post',
      data: comment
    })
  },
  // 根据博客id查找该博客下的全部评论
  getCommentsByBlogId(blogId) {
    return request({
      url: `/comment/getCommentsByBlogId/${blogId}`,
      method: 'get'
    })
  },
  // 根据评论id删除评论
  deleteCommentById(blogId) {
    return request({
      url: `/comment/deleteCommentById/${blogId}`,
      method: 'post'
    })
  }
}
