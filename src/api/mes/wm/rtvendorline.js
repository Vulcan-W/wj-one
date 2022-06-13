import request from '@/utils/request'

// 查询供应商退货行列表
export function listRtvendorline(query) {
  return request({
    url: '/mes/wm/rtvendorline/list',
    method: 'get',
    params: query
  })
}

// 查询供应商退货行详细
export function getRtvendorline(lineId) {
  return request({
    url: '/mes/wm/rtvendorline/' + lineId,
    method: 'get'
  })
}

// 新增供应商退货行
export function addRtvendorline(data) {
  return request({
    url: '/mes/wm/rtvendorline',
    method: 'post',
    data: data
  })
}

// 修改供应商退货行
export function updateRtvendorline(data) {
  return request({
    url: '/mes/wm/rtvendorline',
    method: 'put',
    data: data
  })
}

// 删除供应商退货行
export function delRtvendorline(lineId) {
  return request({
    url: '/mes/wm/rtvendorline/' + lineId,
    method: 'delete'
  })
}
