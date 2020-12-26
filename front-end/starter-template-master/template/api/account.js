import request from '@/utils/request'

export default {
  // 用户注册
  userRegist(registForm) {
    return request({
      url: `/account/regist`,
      method: 'post',
      data: registForm
    })
  },
  // 用户登录
  userLogin(loginForm) {
    return request({
      url: `/account/login`,
      method: 'post',
      data: loginForm
    })
  },
  // 根据token获取账号信息
  getAccountInfoByToken() {
    return request({
      url: `/account/getAccountInfoByToken`,
      method: 'get'
    })
  },
  // 根据id查询用户信息
  getAccountInfoById(id) {
    return request({
      url: `/account/getAccountInfoById/${id}`,
      method: 'get'
    })
  },
  // 修改账号信息
  updateAccount(account) {
    return request({
      url: `/account/updateAccount/`,
      method: 'post',
      data: account
    })
  },
  // 修改账号密码
  updateAccountPassword(submitPasswordForm) {
    return request({
      url: `/account/updatePassword`,
      method: 'post',
      data: submitPasswordForm
    })
  },
  // 头像上传
  avatarSubmit(submitAvatarForm) {
    return request({
      url: `/account/updateAvatar`,
      method: 'post',
      data: submitAvatarForm
    })
  },
  // 查找所有账号
  searchAllAccount(page) {
    return request({
      url: `/account/searchAllAccount/${page}`,
      method: 'get'
    })
  },
  // 根据输入的内容进行查找（分页）
  searchContent(content) {
    return request({
      url: `/account/searchContent/${content}`,
      method: 'get'
    })
  },
  // 根据用户id删除数据库中的记录
  deleteAccount(userId) {
    return request({
      url: `/account/deleteAccount/${userId}`,
      method: 'get'
    })
  },
  // 管理员登录
  adminLogin(loginForm) {
    return request({
      url: `/account/adminLogin`,
      method: 'post',
      data: loginForm
    })
  } 
}
