import request from '@/utils/request'

// 查询班组成员列表
export function listTeammember(query) {
  return request({
    url: '/mes/cal/teammember/list',
    method: 'get',
    params: query
  })
}

// 查询班组成员详细
export function getTeammember(memberId) {
  return request({
    url: '/mes/cal/teammember/' + memberId,
    method: 'get'
  })
}

// 新增班组成员
export function addTeammember(data) {
  return request({
    url: '/mes/cal/teammember',
    method: 'post',
    data: data
  })
}

// 删除班组成员
export function delTeammember(memberId) {
  return request({
    url: '/mes/cal/teammember/' + memberId,
    method: 'delete'
  })
}
