import request from '@/utils/request'

// 查询海关数据列表
export function listCustomsData(query) {
  return request({
    url: '/crm/customs/customsData/list',
    method: 'get',
    params: query
  })
}

// 查询海关数据详细
export function getCustomsData(id) {
  return request({
    url: '/crm/customs/customsData/' + id,
    method: 'get'
  })
}

// 新增海关数据
export function addCustomsData(data) {
  return request({
    url: '/crm/customs/customsData',
    method: 'post',
    data: data
  })
}

// 修改海关数据
export function updateCustomsData(data) {
  return request({
    url: '/crm/customs/customsData',
    method: 'put',
    data: data
  })
}

// 删除海关数据
export function delCustomsData(id) {
  return request({
    url: '/crm/customs/customsData/' + id,
    method: 'delete'
  })
}
