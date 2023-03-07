import request from '@/utils/request'

// 查询来料检验单列表
export function listIqc(query) {
  return request({
    url: '/mes/qc/iqc/list',
    method: 'get',
    params: query
  })
}

// 查询来料检验单详细
export function getIqc(iqcId) {
  return request({
    url: '/mes/qc/iqc/' + iqcId,
    method: 'get'
  })
}

// 新增来料检验单
export function addIqc(data) {
  return request({
    url: '/mes/qc/iqc',
    method: 'post',
    data: data
  })
}

// 修改来料检验单
export function updateIqc(data) {
  return request({
    url: '/mes/qc/iqc',
    method: 'put',
    data: data
  })
}

// 删除来料检验单
export function delIqc(iqcId) {
  return request({
    url: '/mes/qc/iqc/' + iqcId,
    method: 'delete'
  })
}
