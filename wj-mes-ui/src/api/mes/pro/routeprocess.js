import request from '@/utils/request'

// 查询工艺组成列表
export function listRouteprocess(query) {
  return request({
    url: '/mes/pro/routeprocess/list',
    method: 'get',
    params: query
  })
}


// 查询产品的工艺组成列表
export function listProductprocess(productId) {
  return request({
    url: '/mes/pro/routeprocess/listProductProcess/'+productId,
    method: 'get'
  })
}


// 查询工艺组成详细
export function getRouteprocess(recordId) {
  return request({
    url: '/mes/pro/routeprocess/' + recordId,
    method: 'get'
  })
}

// 新增工艺组成
export function addRouteprocess(data) {
  return request({
    url: '/mes/pro/routeprocess',
    method: 'post',
    data: data
  })
}

// 修改工艺组成
export function updateRouteprocess(data) {
  return request({
    url: '/mes/pro/routeprocess',
    method: 'put',
    data: data
  })
}

// 删除工艺组成
export function delRouteprocess(recordId) {
  return request({
    url: '/mes/pro/routeprocess/' + recordId,
    method: 'delete'
  })
}
