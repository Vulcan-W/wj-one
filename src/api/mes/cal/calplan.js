import request from '@/utils/request'

// 查询排班计划列表
export function listCalplan(query) {
  return request({
    url: '/mes/cal/calplan/list',
    method: 'get',
    params: query
  })
}

// 查询排班计划详细
export function getCalplan(planId) {
  return request({
    url: '/mes/cal/calplan/' + planId,
    method: 'get'
  })
}

// 新增排班计划
export function addCalplan(data) {
  return request({
    url: '/mes/cal/calplan',
    method: 'post',
    data: data
  })
}

// 修改排班计划
export function updateCalplan(data) {
  return request({
    url: '/mes/cal/calplan',
    method: 'put',
    data: data
  })
}

// 删除排班计划
export function delCalplan(planId) {
  return request({
    url: '/mes/cal/calplan/' + planId,
    method: 'delete'
  })
}
