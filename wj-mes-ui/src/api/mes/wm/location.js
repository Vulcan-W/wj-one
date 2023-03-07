import request from '@/utils/request'

// 查询库区设置列表
export function listLocation(query) {
  return request({
    url: '/mes/wm/location/list',
    method: 'get',
    params: query
  })
}

// 查询库区设置详细
export function getLocation(locationId) {
  return request({
    url: '/mes/wm/location/' + locationId,
    method: 'get'
  })
}

// 新增库区设置
export function addLocation(data) {
  return request({
    url: '/mes/wm/location',
    method: 'post',
    data: data
  })
}

// 修改库区设置
export function updateLocation(data) {
  return request({
    url: '/mes/wm/location',
    method: 'put',
    data: data
  })
}

// 删除库区设置
export function delLocation(locationId) {
  return request({
    url: '/mes/wm/location/' + locationId,
    method: 'delete'
  })
}
