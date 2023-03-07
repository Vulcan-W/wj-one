import request from '@/utils/request'

// 查询人力资源列表
export function listWorkstationworker(query) {
  return request({
    url: '/mes/md/workstationworker/list',
    method: 'get',
    params: query
  })
}

// 查询人力资源详细
export function getWorkstationworker(recordId) {
  return request({
    url: '/mes/md/workstationworker/' + recordId,
    method: 'get'
  })
}

// 新增人力资源
export function addWorkstationworker(data) {
  return request({
    url: '/mes/md/workstationworker',
    method: 'post',
    data: data
  })
}

// 修改人力资源
export function updateWorkstationworker(data) {
  return request({
    url: '/mes/md/workstationworker',
    method: 'put',
    data: data
  })
}

// 删除人力资源
export function delWorkstationworker(recordId) {
  return request({
    url: '/mes/md/workstationworker/' + recordId,
    method: 'delete'
  })
}
