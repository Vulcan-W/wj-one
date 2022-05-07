import request from '@/utils/request'

// 查询车间列表
export function listWorkshop(query) {
  return request({
    url: '/md/workshop/list',
    method: 'get',
    params: query
  })
}

// 查询车间详细
export function getWorkshop(workshopId) {
  return request({
    url: '/md/workshop/' + workshopId,
    method: 'get'
  })
}

// 新增车间
export function addWorkshop(data) {
  return request({
    url: '/md/workshop',
    method: 'post',
    data: data
  })
}

// 修改车间
export function updateWorkshop(data) {
  return request({
    url: '/md/workshop',
    method: 'put',
    data: data
  })
}

// 删除车间
export function delWorkshop(workshopId) {
  return request({
    url: '/md/workshop/' + workshopId,
    method: 'delete'
  })
}
