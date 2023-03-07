import request from '@/utils/request'

// 查询部门列表
export function listItemType(query) {
  return request({
    url: '/mes/md/itemtype/list',
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
export function listItemTypeExcludeChild(itemTypeId) {
  return request({
    url: '/mes/md/itemtype/list/exclude/' + itemTypeId,
    method: 'get'
  })
}

// 查询部门详细
export function getItemType(itemTypeId) {
  return request({
    url: '/mes/md/itemtype/' + itemTypeId,
    method: 'get'
  })
}

// 查询部门下拉树结构
export function treeselect() {
  return request({
    url: '/mes/md/itemtype/treeselect',
    method: 'get'
  })
}


// 新增部门
export function addItemType(data) {
  return request({
    url: '/mes/md/itemtype',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateItemType(data) {
  return request({
    url: '/mes/md/itemtype',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delItemType(itemTypeId) {
  return request({
    url: '/mes/md/itemtype/' + itemTypeId,
    method: 'delete'
  })
}