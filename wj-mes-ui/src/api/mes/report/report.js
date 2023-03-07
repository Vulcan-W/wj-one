import request from '@/utils/request'

//查看报表
export function getReport(reportName) {
    return request({
      url: '/mes/report/'+reportName,
      method: 'get',
      responseType: 'arraybuffer',
    })
  }

  //查看报表
export function getReport2(reportName) {
    return request({
      url: '/jasper-report/previewPrint/'+reportName,
      method: 'get',
      responseType: 'arraybuffer',
    })
  }
