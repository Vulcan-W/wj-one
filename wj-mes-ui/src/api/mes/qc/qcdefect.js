import request from '@/utils/request'

// 查询常见缺陷列表
export function listQcdefect(query) {
  return request({
    url: '/mes/qc/qcdefect/list',
    method: 'get',
    params: query
  })
}

// 查询常见缺陷详细
export function getQcdefect(defectId) {
  return request({
    url: '/mes/qc/qcdefect/' + defectId,
    method: 'get'
  })
}

// 新增常见缺陷
export function addQcdefect(data) {
  return request({
    url: '/mes/qc/qcdefect',
    method: 'post',
    data: data
  })
}

// 修改常见缺陷
export function updateQcdefect(data) {
  return request({
    url: '/mes/qc/qcdefect',
    method: 'put',
    data: data
  })
}

// 删除常见缺陷
export function delQcdefect(defectId) {
  return request({
    url: '/mes/qc/qcdefect/' + defectId,
    method: 'delete'
  })
}
