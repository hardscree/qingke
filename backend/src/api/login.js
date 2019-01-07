import request from '@/utils/request'

export function loginByUsername(username, password) {
  const data = {
    userName:username,
    userPwd:password
  }
  return request({
    url: '/admin/user/login',
    method: 'post',
    data
  })
}

export function logout() {
  // return request({
  //   url: 'http://localhost:9008/admin/user/logout',
  //   method: 'post'
  // })
  return new Promise(()=>{},()=>{});
}

export function getUserInfo(token) {
  // console.log("token=",token)
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

