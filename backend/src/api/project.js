import request from '@/utils/request'
export function fetchList(query) {
  const data = {
    projectId:isNaN(query.projectId)?0:query.projectId,
    createDateEnd: query.createDateEnd,
    createDateStart: query.createDateStart,
    enterpriseName: query.enterpriseName,
    projectName: query.projectName,
    projectState: query.projectState === undefined || query.projectState === '' ? -1 : query.projectState
  }
  // console.log(data)
  return request({
    url: '/admin/project/query',
    method: 'post',
    data
  })
}

export function discard(id) {
  return request({
    url: '/admin/project/discard/' + id,
    method: 'post'
  })
}

export function fetchStatus() {
  return request({
    url: '/admin/project/status',
    method: 'get'
  })
}

export function fetchDetail(id) {
  return request({
    url: '/admin/project/query/' + id,
    method: 'get'
  })
}

export function addProject(data) {
  return request({
    url: '/admin/project/publish/',
    method: 'post',
    data
  })
}

export function getProjectQingkeList(id) {
  return request({
    url: '/admin/project/project_qingke/' + id,
    method: 'get'
  })
}

export function audit(data) {
  return request({
    url: '/admin/project/project_qingke/audit',
    method: 'post',
    data
  })
}
export function makeQrcode(id) {
  return request({
    url: '/api/weixin/qrcode?pNumb=' + id,
    method: 'post'
  })
}

