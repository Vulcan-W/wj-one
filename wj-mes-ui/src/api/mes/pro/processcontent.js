import request from '@/utils/request'

// 查询生产工序内容列表
export function listProcesscontent(query) {
  return request({
    url: '/mes/pro/processcontent/list',
    method: 'get',
    params: query
  })
}

// 查询生产工序内容详细
export function getProcesscontent(contentId) {
  return request({
    url: '/mes/pro/processcontent/' + contentId,
    method: 'get'
  })
}

// 新增生产工序内容
export function addProcesscontent(data) {
  return request({
    url: '/mes/pro/processcontent',
    method: 'post',
    data: data
  })
}

// 修改生产工序内容
export function updateProcesscontent(data) {
  return request({
    url: '/mes/pro/processcontent',
    method: 'put',
    data: data
  })
}

// 删除生产工序内容
export function delProcesscontent(contentId) {
  return request({
    url: '/mes/pro/processcontent/' + contentId,
    method: 'delete'
  })
}
