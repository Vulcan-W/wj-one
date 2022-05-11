import request from '@/utils/request'

// 查询生产工序列表
export function listProcess(query) {
  return request({
    url: '/mes/pro/process/list',
    method: 'get',
    params: query
  })
}

// 查询所有有效生产工序
export function listAllProcess() {
  return request({
    url: '/mes/pro/process/listAll',
    method: 'get'
  })
}


// 查询生产工序详细
export function getProcess(processId) {
  return request({
    url: '/mes/pro/process/' + processId,
    method: 'get'
  })
}

// 新增生产工序
export function addProcess(data) {
  return request({
    url: '/mes/pro/process',
    method: 'post',
    data: data
  })
}

// 修改生产工序
export function updateProcess(data) {
  return request({
    url: '/mes/pro/process',
    method: 'put',
    data: data
  })
}

// 删除生产工序
export function delProcess(processId) {
  return request({
    url: '/mes/pro/process/' + processId,
    method: 'delete'
  })
}
