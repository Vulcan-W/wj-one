import request from '@/utils/request'

// 查询供应商列表
export function listVendor(query) {
  return request({
    url: '/mes/md/vendor/list',
    method: 'get',
    params: query
  })
}

// 查询供应商详细
export function getVendor(vendorId) {
  return request({
    url: '/mes/md/vendor/' + vendorId,
    method: 'get'
  })
}

// 新增供应商
export function addVendor(data) {
  return request({
    url: '/mes/md/vendor',
    method: 'post',
    data: data
  })
}

// 修改供应商
export function updateVendor(data) {
  return request({
    url: '/mes/md/vendor',
    method: 'put',
    data: data
  })
}

// 删除供应商
export function delVendor(vendorId) {
  return request({
    url: '/mes/md/vendor/' + vendorId,
    method: 'delete'
  })
}
