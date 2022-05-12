import request from '@/utils/request'

// 查询工艺路线列表
export function listProroute(query) {
  return request({
    url: '/mes/pro/proroute/list',
    method: 'get',
    params: query
  })
}

// 查询工艺路线详细
export function getProroute(routeId) {
  return request({
    url: '/mes/pro/proroute/' + routeId,
    method: 'get'
  })
}

// 新增工艺路线
export function addProroute(data) {
  return request({
    url: '/mes/pro/proroute',
    method: 'post',
    data: data
  })
}

// 修改工艺路线
export function updateProroute(data) {
  return request({
    url: '/mes/pro/proroute',
    method: 'put',
    data: data
  })
}

// 删除工艺路线
export function delProroute(routeId) {
  return request({
    url: '/mes/pro/proroute/' + routeId,
    method: 'delete'
  })
}
