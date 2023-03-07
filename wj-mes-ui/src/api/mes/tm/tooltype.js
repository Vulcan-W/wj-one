import request from '@/utils/request'

// 查询工装夹具类型列表
export function listTooltype(query) {
  return request({
    url: '/mes/tm/tooltype/list',
    method: 'get',
    params: query
  })
}

// 查询所有工装夹具类型
export function listAllTooltype() {
  return request({
    url: '/mes/tm/tooltype/listAll',
    method: 'get',
  })
}

// 查询工装夹具类型详细
export function getTooltype(toolTypeId) {
  return request({
    url: '/mes/tm/tooltype/' + toolTypeId,
    method: 'get'
  })
}

// 新增工装夹具类型
export function addTooltype(data) {
  return request({
    url: '/mes/tm/tooltype',
    method: 'post',
    data: data
  })
}

// 修改工装夹具类型
export function updateTooltype(data) {
  return request({
    url: '/mes/tm/tooltype',
    method: 'put',
    data: data
  })
}

// 删除工装夹具类型
export function delTooltype(toolTypeId) {
  return request({
    url: '/mes/tm/tooltype/' + toolTypeId,
    method: 'delete'
  })
}
