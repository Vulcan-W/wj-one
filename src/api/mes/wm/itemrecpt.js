import request from '@/utils/request'

// 查询物料入库单列表
export function listItemrecpt(query) {
  return request({
    url: '/mes/wm/itemrecpt/list',
    method: 'get',
    params: query
  })
}

// 查询物料入库单详细
export function getItemrecpt(recptId) {
  return request({
    url: '/mes/wm/itemrecpt/' + recptId,
    method: 'get'
  })
}

// 新增物料入库单
export function addItemrecpt(data) {
  return request({
    url: '/mes/wm/itemrecpt',
    method: 'post',
    data: data
  })
}

// 修改物料入库单
export function updateItemrecpt(data) {
  return request({
    url: '/mes/wm/itemrecpt',
    method: 'put',
    data: data
  })
}

// 确认物料入库单
export function confirmItemrecpt(data) {
  return request({
    url: '/mes/wm/itemrecpt/confirm',
    method: 'put',
    data: data
  })
}

//执行入库
export function execute(recptId) {
  return request({
    url: '/mes/wm/itemrecpt/' + recptId,
    method: 'put'
  })
}


// 删除物料入库单
export function delItemrecpt(recptId) {
  return request({
    url: '/mes/wm/itemrecpt/' + recptId,
    method: 'delete'
  })
}
