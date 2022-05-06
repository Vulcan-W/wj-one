import request from '@/utils/request'

// 查询产品BOM关系列表
export function listBom(query) {
  return request({
    url: '/mes/md/bom/list',
    method: 'get',
    params: query
  })
}

// 查询产品BOM关系详细
export function getBom(bomId) {
  return request({
    url: '/mes/md/bom/' + bomId,
    method: 'get'
  })
}

// 新增产品BOM关系
export function addBom(data) {
  return request({
    url: '/mes/md/bom',
    method: 'post',
    data: data
  })
}

// 修改产品BOM关系
export function updateBom(data) {
  return request({
    url: '/mes/md/bom',
    method: 'put',
    data: data
  })
}

// 删除产品BOM关系
export function delBom(bomId) {
  return request({
    url: '/mes/md/bom/' + bomId,
    method: 'delete'
  })
}
