import request from '@/utils/request'

// 查询单位列表
export function listUnitmeasure(query) {
  return request({
    url: '/mes/md/unitmeasure/list',
    method: 'get',
    params: query
  })
}

export function listPrimaryUnitmeasure(){
  return request({
    url: '/mes/md/unitmeasure/listprimary',
    method: 'get'
  })
}

export function listAllUnitmeasure(){
  return request({
    url: '/mes/md/unitmeasure/selectall',
    method: 'get'
  })
}

// 查询单位详细
export function getUnitmeasure(measureId) {
  return request({
    url: '/mes/md/unitmeasure/' + measureId,
    method: 'get'
  })
}

// 新增单位
export function addUnitmeasure(data) {
  return request({
    url: '/mes/md/unitmeasure',
    method: 'post',
    data: data
  })
}

// 修改单位
export function updateUnitmeasure(data) {
  return request({
    url: '/mes/md/unitmeasure',
    method: 'put',
    data: data
  })
}

// 删除单位
export function delUnitmeasure(measureId) {
  return request({
    url: '/mes/md/unitmeasure/' + measureId,
    method: 'delete'
  })
}
