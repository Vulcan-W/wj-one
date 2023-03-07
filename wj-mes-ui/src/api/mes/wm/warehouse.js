import request from '@/utils/request'

// 查询仓库设置列表
export function listWarehouse(query) {
  return request({
    url: '/mes/wm/warehouse/list',
    method: 'get',
    params: query
  })
}

export function getTreeList(){
  return request({
    url: '/mes/wm/warehouse/getTreeList',
    method: 'get'
  })
}

// 查询仓库设置详细
export function getWarehouse(warehouseId) {
  return request({
    url: '/mes/wm/warehouse/' + warehouseId,
    method: 'get'
  })
}

// 新增仓库设置
export function addWarehouse(data) {
  return request({
    url: '/mes/wm/warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库设置
export function updateWarehouse(data) {
  return request({
    url: '/mes/wm/warehouse',
    method: 'put',
    data: data
  })
}

// 删除仓库设置
export function delWarehouse(warehouseId) {
  return request({
    url: '/mes/wm/warehouse/' + warehouseId,
    method: 'delete'
  })
}
