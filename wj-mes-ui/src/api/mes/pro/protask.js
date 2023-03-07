import request from '@/utils/request'

// 查询生产任务列表
export function listProtask(query) {
  return request({
    url: '/mes/pro/protask/list',
    method: 'get',
    params: query
  })
}

// 查询生产任务列表
export function listGanttTaskList(query) {
  return request({
    url: '/mes/pro/protask/listGanttTaskList',
    method: 'get',
    params: query
  })
}


// 查询生产任务详细
export function getProtask(taskId) {
  return request({
    url: '/mes/pro/protask/' + taskId,
    method: 'get'
  })
}

// 新增生产任务
export function addProtask(data) {
  return request({
    url: '/mes/pro/protask',
    method: 'post',
    data: data
  })
}

// 修改生产任务
export function updateProtask(data) {
  return request({
    url: '/mes/pro/protask',
    method: 'put',
    data: data
  })
}

// 删除生产任务
export function delProtask(taskId) {
  return request({
    url: '/mes/pro/protask/' + taskId,
    method: 'delete'
  })
}
