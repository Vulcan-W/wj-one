import request from '@/utils/request'

// 查询库位设置列表
export function listArea(query) {
  return request({
    url: '/mes/wm/area/list',
    method: 'get',
    params: query
  })
}

// 查询库位设置详细
export function getArea(areaId) {
  return request({
    url: '/mes/wm/area/' + areaId,
    method: 'get'
  })
}

// 新增库位设置
export function addArea(data) {
  return request({
    url: '/mes/wm/area',
    method: 'post',
    data: data
  })
}

// 修改库位设置
export function updateArea(data) {
  return request({
    url: '/mes/wm/area',
    method: 'put',
    data: data
  })
}

// 删除库位设置
export function delArea(areaId) {
  return request({
    url: '/mes/wm/area/' + areaId,
    method: 'delete'
  })
}
