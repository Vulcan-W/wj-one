import request from '@/utils/request'

// 查询计划班次列表
export function listShift(query) {
  return request({
    url: '/mes/cal/shift/list',
    method: 'get',
    params: query
  })
}

// 查询计划班次详细
export function getShift(shiftId) {
  return request({
    url: '/mes/cal/shift/' + shiftId,
    method: 'get'
  })
}

// 新增计划班次
export function addShift(data) {
  return request({
    url: '/mes/cal/shift',
    method: 'post',
    data: data
  })
}

// 修改计划班次
export function updateShift(data) {
  return request({
    url: '/mes/cal/shift',
    method: 'put',
    data: data
  })
}

// 删除计划班次
export function delShift(shiftId) {
  return request({
    url: '/mes/cal/shift/' + shiftId,
    method: 'delete'
  })
}
