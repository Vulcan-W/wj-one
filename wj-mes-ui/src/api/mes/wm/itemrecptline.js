import request from '@/utils/request'

// 查询物料入库单行列表
export function listItemrecptline(query) {
  return request({
    url: '/mes/wm/itemrecptline/list',
    method: 'get',
    params: query
  })
}

// 查询物料入库单行详细
export function getItemrecptline(lineId) {
  return request({
    url: '/mes/wm/itemrecptline/' + lineId,
    method: 'get'
  })
}

// 新增物料入库单行
export function addItemrecptline(data) {
  return request({
    url: '/mes/wm/itemrecptline',
    method: 'post',
    data: data
  })
}

// 修改物料入库单行
export function updateItemrecptline(data) {
  return request({
    url: '/mes/wm/itemrecptline',
    method: 'put',
    data: data
  })
}

// 删除物料入库单行
export function delItemrecptline(lineId) {
  return request({
    url: '/mes/wm/itemrecptline/' + lineId,
    method: 'delete'
  })
}
