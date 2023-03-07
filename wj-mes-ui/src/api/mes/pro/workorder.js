import request from '@/utils/request'

// 查询生产工单列表
export function listWorkorder(query) {
  return request({
    url: '/mes/pro/workorder/list',
    method: 'get',
    params: query
  })
}

// 查询生产工单详细
export function getWorkorder(workorderId) {
  return request({
    url: '/mes/pro/workorder/' + workorderId,
    method: 'get'
  })
}

// 新增生产工单
export function addWorkorder(data) {
  return request({
    url: '/mes/pro/workorder',
    method: 'post',
    data: data
  })
}

// 修改生产工单
export function updateWorkorder(data) {
  return request({
    url: '/mes/pro/workorder',
    method: 'put',
    data: data
  })
}

// 删除生产工单
export function delWorkorder(workorderId) {
  return request({
    url: '/mes/pro/workorder/' + workorderId,
    method: 'delete'
  })
}
