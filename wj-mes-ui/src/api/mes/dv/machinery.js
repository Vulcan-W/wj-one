import request from '@/utils/request'

// 查询设备列表
export function listMachinery(query) {
  return request({
    url: '/mes/dv/machinery/list',
    method: 'get',
    params: query
  })
}

// 查询设备详细
export function getMachinery(machineryId) {
  return request({
    url: '/mes/dv/machinery/' + machineryId,
    method: 'get'
  })
}

// 新增设备
export function addMachinery(data) {
  return request({
    url: '/mes/dv/machinery',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateMachinery(data) {
  return request({
    url: '/mes/dv/machinery',
    method: 'put',
    data: data
  })
}

// 删除设备
export function delMachinery(machineryId) {
  return request({
    url: '/mes/dv/machinery/' + machineryId,
    method: 'delete'
  })
}
