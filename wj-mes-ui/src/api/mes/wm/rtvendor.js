import request from '@/utils/request'

// 查询供应商退货列表
export function listRtvendor(query) {
  return request({
    url: '/mes/wm/rtvendor/list',
    method: 'get',
    params: query
  })
}

// 查询供应商退货详细
export function getRtvendor(rtId) {
  return request({
    url: '/mes/wm/rtvendor/' + rtId,
    method: 'get'
  })
}

// 新增供应商退货
export function addRtvendor(data) {
  return request({
    url: '/mes/wm/rtvendor',
    method: 'post',
    data: data
  })
}

// 修改供应商退货
export function updateRtvendor(data) {
  return request({
    url: '/mes/wm/rtvendor',
    method: 'put',
    data: data
  })
}

// 删除供应商退货
export function delRtvendor(rtId) {
  return request({
    url: '/mes/wm/rtvendor/' + rtId,
    method: 'delete'
  })
}

//执行入库
export function execute(rtId) {
  return request({
    url: '/mes/wm/rtvendor/' + rtId,
    method: 'put'
  })
}