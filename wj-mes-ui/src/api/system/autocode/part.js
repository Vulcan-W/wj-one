import request from '@/utils/request'

// 查询规则组成
export function listPart(query) {
  return request({
    url: '/system/autocode/part/list',
    method: 'get',
    params: query
  })
}

// 查询规则组成详细
export function getPart(partId) {
  return request({
    url: '/system/autocode/part/' + partId,
    method: 'get'
  })
}


// 新增规则组成
export function addPart(data) {
  return request({
    url: '/system/autocode/part',
    method: 'post',
    data: data
  })
}

// 修改规则组成
export function updatePart(data) {
  return request({
    url: '/system/autocode/part',
    method: 'put',
    data: data
  })
}

// 删除规则组成
export function delPart(partIds) {
  return request({
    url: '/system/autocode/part/' + partIds,
    method: 'delete'
  })
}
