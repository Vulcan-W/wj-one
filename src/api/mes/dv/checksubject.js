import request from '@/utils/request'

// 查询点检项目列表
export function listChecksubject(query) {
  return request({
    url: '/mes/dv/checksubject/list',
    method: 'get',
    params: query
  })
}

// 查询点检项目详细
export function getChecksubject(recordId) {
  return request({
    url: '/mes/dv/checksubject/' + recordId,
    method: 'get'
  })
}

// 新增点检项目
export function addChecksubject(data) {
  return request({
    url: '/mes/dv/checksubject',
    method: 'post',
    data: data
  })
}

// 修改点检项目
export function updateChecksubject(data) {
  return request({
    url: '/mes/dv/checksubject',
    method: 'put',
    data: data
  })
}

// 删除点检项目
export function delChecksubject(recordId) {
  return request({
    url: '/mes/dv/checksubject/' + recordId,
    method: 'delete'
  })
}
