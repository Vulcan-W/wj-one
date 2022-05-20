import request from '@/utils/request'

// 查询来料检验单缺陷记录列表
export function listIqcdefect(query) {
  return request({
    url: '/mes/qc/iqcdefect/list',
    method: 'get',
    params: query
  })
}

// 查询来料检验单缺陷记录详细
export function getIqcdefect(recordId) {
  return request({
    url: '/mes/qc/iqcdefect/' + recordId,
    method: 'get'
  })
}

// 新增来料检验单缺陷记录
export function addIqcdefect(data) {
  return request({
    url: '/mes/qc/iqcdefect',
    method: 'post',
    data: data
  })
}

// 修改来料检验单缺陷记录
export function updateIqcdefect(data) {
  return request({
    url: '/mes/qc/iqcdefect',
    method: 'put',
    data: data
  })
}

// 删除来料检验单缺陷记录
export function delIqcdefect(recordId) {
  return request({
    url: '/mes/qc/iqcdefect/' + recordId,
    method: 'delete'
  })
}
