import request from '@/utils/request'

// 查询库存记录列表
export function listWmstock(query) {
  return request({
    url: '/mes/wm/wmstock/list',
    method: 'get',
    params: query
  })
}

// 查询库存记录详细
export function getWmstock(materialStockId) {
  return request({
    url: '/mes/wm/wmstock/' + materialStockId,
    method: 'get'
  })
}

// 新增库存记录
export function addWmstock(data) {
  return request({
    url: '/mes/wm/wmstock',
    method: 'post',
    data: data
  })
}

// 修改库存记录
export function updateWmstock(data) {
  return request({
    url: '/mes/wm/wmstock',
    method: 'put',
    data: data
  })
}

// 删除库存记录
export function delWmstock(materialStockId) {
  return request({
    url: '/mes/wm/wmstock/' + materialStockId,
    method: 'delete'
  })
}
