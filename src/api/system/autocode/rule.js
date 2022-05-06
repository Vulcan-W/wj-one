import request from '@/utils/request'

export function genCode(ruleCode){
  return request({
      url: '/system/autocode/get/'+ruleCode,
      method: 'get'
  })
}

// 查询字典类型列表
export function listRule(query) {
  return request({
    url: '/system/autocode/rule/list',
    method: 'get',
    params: query
  })
}

// 查询字典类型详细
export function getRule(ruleId) {
  return request({
    url: '/system/autocode/rule/' + ruleId,
    method: 'get'
  })
}

// 新增字典类型
export function addRule(data) {
  return request({
    url: '/system/autocode/rule',
    method: 'post',
    data: data
  })
}

// 修改字典类型
export function updateRule(data) {
  return request({
    url: '/system/autocode/rule',
    method: 'put',
    data: data
  })
}

// 删除字典类型
export function delRule(ruleId) {
  return request({
    url: '/system/autocode/rule/' + ruleId,
    method: 'delete'
  })
}

// 刷新字典缓存
export function refreshCache() {
  return request({
    url: '/system/autocode/rule/refreshCache',
    method: 'delete'
  })
}

// 获取字典选择框列表
export function optionselect() {
  return request({
    url: '/system/autocode/rule/optionselect',
    method: 'get'
  })
}