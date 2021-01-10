import request from '@/utils/request'

export default {
  // 关注
  followUser(follow) {
    return request({
      url: `/follow/followUser`,
      method: 'post',
      data: follow
    })
  },
  // 根据用户id和作者id查看该用户是否有关注这个作者
  isFollowed(userId, followId) {
    return request({
      url: `/follow/isFollowed/${userId}/${followId}`,
      method: 'get'
    })
  },
  // 根据用户id获取粉丝
  getFansByUserId(userId) {
    return request({
      url: `/follow/getFansByUserId/${userId}`,
      method: 'get'
    })
  },
  // 根据用户id查询该用户的全部粉丝信息
  getFansListByUserId(followId) {
    return request({
      url: `/follow/getFansListByUserId/${followId}`,
      method: 'get'
    })
  },
  // 根据用户id获取该用户关注的所有用户信息
  getFollowListByUserId(userId) {
    return request({
      url: `/follow/getFollowListByUserId/${userId}`,
      method: 'get'
    })
  },
  // 根据用户id获取该用户的关注数
  getFollowCountByUserId(userId) {
    return request({
      url: `/follow/getFollowCountByUserId/${userId}`,
      method: 'get'
    })
  },
  // 根据用户id获取该用户的粉丝数
  getFansCountByUserId(userId) {
    return request({
      url: `/follow/getFansCountByUserId/${userId}`,
      method: 'get'
    })
  }
}
