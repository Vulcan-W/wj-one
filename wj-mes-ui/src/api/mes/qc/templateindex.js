import request from '@/utils/request'

// 查询检测模板-检测项列表
export function listTemplateindex(query) {
  return request({
    url: '/mes/qc/templateindex/list',
    method: 'get',
    params: query
  })
}

// 查询检测模板-检测项详细
export function getTemplateindex(recordId) {
  return request({
    url: '/mes/qc/templateindex/' + recordId,
    method: 'get'
  })
}

// 新增检测模板-检测项
export function addTemplateindex(data) {
  return request({
    url: '/mes/qc/templateindex',
    method: 'post',
    data: data
  })
}

// 修改检测模板-检测项
export function updateTemplateindex(data) {
  return request({
    url: '/mes/qc/templateindex',
    method: 'put',
    data: data
  })
}

// 删除检测模板-检测项
export function delTemplateindex(recordId) {
  return request({
    url: '/mes/qc/templateindex/' + recordId,
    method: 'delete'
  })
}
