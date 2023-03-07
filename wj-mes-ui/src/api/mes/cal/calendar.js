import request from '@/utils/request'
// 查询排班列表
export function listCalendars(query) {
    return request({
      url: '/mes/cal/calendar/list',
      method: 'get',
      params: query
    })
  }