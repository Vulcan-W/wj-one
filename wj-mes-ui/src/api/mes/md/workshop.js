import request from '@/utils/request'

// 查询车间列表
export function listWorkshop(query) {
  return request({
    url: '/mes/md/workshop/list',
    method: 'get',
    params: query
  })
}

export function listAllWorkshop(){
  return request({
    url: '/mes/md/workshop/listAll',
    method: 'get'
  })
}

// 查询车间详细
export function getWorkshop(workshopId) {
  return request({
    url: '/mes/md/workshop/' + workshopId,
    method: 'get'
  })
}

// 新增车间
export function addWorkshop(data) {
  return request({
    url: '/mes/md/workshop',
    method: 'post',
    data: data
  })
}

// 修改车间
export function updateWorkshop(data) {
  return request({
    url: '/mes/md/workshop',
    method: 'put',
    data: data
  })
}

// 删除车间
export function delWorkshop(workshopId) {
  return request({
    url: '/mes/md/workshop/' + workshopId,
    method: 'delete'
  })
}
