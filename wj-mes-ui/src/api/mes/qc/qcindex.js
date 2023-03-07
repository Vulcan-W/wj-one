import request from '@/utils/request'

// 查询检测项列表
export function listQcindex(query) {
  return request({
    url: '/mes/qc/qcindex/list',
    method: 'get',
    params: query
  })
}

// 查询检测项详细
export function getQcindex(indexId) {
  return request({
    url: '/mes/qc/qcindex/' + indexId,
    method: 'get'
  })
}

// 新增检测项
export function addQcindex(data) {
  return request({
    url: '/mes/qc/qcindex',
    method: 'post',
    data: data
  })
}

// 修改检测项
export function updateQcindex(data) {
  return request({
    url: '/mes/qc/qcindex',
    method: 'put',
    data: data
  })
}

// 删除检测项
export function delQcindex(indexId) {
  return request({
    url: '/mes/qc/qcindex/' + indexId,
    method: 'delete'
  })
}
