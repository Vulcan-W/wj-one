import request from '@/utils/request'

// 查询某通数据列表
export function listBigTradeData(query) {
  return request({
    url: '/customs/bigTradeData/list',
    method: 'get',
    params: query
  })
}

// 查询某通数据详细
export function getBigTradeData(id) {
  return request({
    url: '/customs/bigTradeData/' + id,
    method: 'get'
  })
}

// 新增某通数据
export function addBigTradeData(data) {
  return request({
    url: '/customs/bigTradeData',
    method: 'post',
    data: data
  })
}

// 修改某通数据
export function updateBigTradeData(data) {
  return request({
    url: '/customs/bigTradeData',
    method: 'put',
    data: data
  })
}

// 删除某通数据
export function delBigTradeData(id) {
  return request({
    url: '/customs/bigTradeData/' + id,
    method: 'delete'
  })
}
