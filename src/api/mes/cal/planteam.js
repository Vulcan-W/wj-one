import request from '@/utils/request'

// 查询计划班组列表
export function listPlanteam(query) {
  return request({
    url: '/mes/cal/planteam/list',
    method: 'get',
    params: query
  })
}

// 新增计划班组
export function addPlanteam(data) {
  return request({
    url: '/mes/cal/planteam',
    method: 'post',
    data: data
  })
}

// 删除计划班组
export function delPlanteam(recordId) {
  return request({
    url: '/mes/cal/planteam/' + recordId,
    method: 'delete'
  })
}
