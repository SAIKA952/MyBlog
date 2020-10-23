import request from '@/utils/request'

export default {
  // 👍
  liked(likeInfo) {
    return request({
      url: `/liked/like`,
      method: 'post',
      data: likeInfo
    })
  },
  // 查询点赞信息
  getLikedInfo(blogId, userId) {
    return request({
      url: `/liked/getLikedInfo/${blogId}/${userId}`,
      method: 'get'
    })
  },
  // 根据博客id查询点赞博客的信息
  getBlogLikedListByBlogId(blogId) {
    return request({
      url: `/liked/getBlogLikedListByBlogId/${blogId}`,
      method: 'get'
    })
  },
  // 收藏
  collect(collectInfo) {
    return request({
      url: `/liked/collect`,
      method: 'post',
      data: collectInfo
    })
  },
  getBlogCollectListByBlogId(blogId) {
    return request({
      url: `/liked/getBlogCollectListByBlogId/${blogId}`,
      method: 'get'
    })
  },
  // 该用户是否收藏这篇博客
  isUserCollected(blogId, userId) {
    return request({
      url: `/liked/isUserCollected/${blogId}/${userId}`,
      method: 'get'
    })
  },  
  // 根据用户id查询该用户点赞的文章
  getLikedListByUserId(userId) {
    return request({
      url: `/liked/getLikedListByUserId/${userId}`,
      method: 'get'
    })
  },
  // 根据用户id查找该用户收藏的文章
  getCollectListByUserId(userId) {
    return request({
      url: `/liked/getCollectListByUserId/${userId}`,
      method: 'get'
    })
  }
}
