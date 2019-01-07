import request from '@/utils/request'
// import { audit } from './project';
export function fetchList(query) {
  const data = query
  return request({
    url: '/admin/qingke/query',
    method: 'post',
    data
  })
}

export function fetchDetail(id) {
  return request({
    url: '/admin/qingke/query/' + id,
    method: 'get'
  })
}

export function update(entity) {
  const data = entity
  return request({
    url: '/admin/qingke/update',
    method: 'post',
    data
  })
}

export function audit(auditType, entity) {
  const data = entity
  return request({
    url: '/admin/qingke/audit/' + auditType,
    method: 'post',
    data
  })
}

export function exportExcel(query) {
  const data = query
  return request({
    url: '/admin/qingke/query/export',
    method: 'post',
    data
  })
}

export function getVideoUrl(id) {
  return request({
    url: '/api/qingke/videoImage?qkNumb=' + id,
    method: 'get'
  })
}

