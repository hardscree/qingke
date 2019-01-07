import request from '@/utils/request'
export function search(keyword) {
  return request({
    url: '/admin/enterprise/query?keyword='+keyword,
    method: 'get'
  })
}
