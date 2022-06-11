import request from '@/utils/request'

// 查询班组列表
export function listTeam(query) {
  return request({
    url: '/mes/cal/team/list',
    method: 'get',
    params: query
  })
}

// 查询班组列表
export function listAllTeam() {
  return request({
    url: '/mes/cal/team/listAll',
    method: 'get'
  })
}

// 查询班组详细
export function getTeam(teamId) {
  return request({
    url: '/mes/cal/team/' + teamId,
    method: 'get'
  })
}

// 新增班组
export function addTeam(data) {
  return request({
    url: '/mes/cal/team',
    method: 'post',
    data: data
  })
}

// 修改班组
export function updateTeam(data) {
  return request({
    url: '/mes/cal/team',
    method: 'put',
    data: data
  })
}

// 删除班组
export function delTeam(teamId) {
  return request({
    url: '/mes/cal/team/' + teamId,
    method: 'delete'
  })
}
