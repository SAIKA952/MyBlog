import request from '@/utils/request'

export default {
  // 发送验证码
  sendCode(phone) {
    return request({
      url: `/sendMsg/send/${phone}`,
      method: 'get'
    })
  },
  // 查看验证码是否正确
  validate(code, phone) {
    return request({
      url: `/sendMsg/validate/${code}/${phone}`,
      method: 'get'
    })
  }
}
