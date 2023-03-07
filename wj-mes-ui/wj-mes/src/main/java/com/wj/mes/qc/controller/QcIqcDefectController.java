package com.wj.mes.qc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import com.wj.common.utils.StringUtils;
import com.wj.mes.qc.domain.ValidList;
import com.wj.mes.qc.service.IQcIqcLineService;
import com.wj.mes.qc.service.IQcIqcService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wj.common.annotation.Log;
import com.wj.common.core.controller.BaseController;
import com.wj.common.core.domain.AjaxResult;
import com.wj.common.enums.BusinessType;
import com.wj.mes.qc.domain.QcIqcDefect;
import com.wj.mes.qc.service.IQcIqcDefectService;
import com.wj.common.utils.poi.ExcelUtil;
import com.wj.common.core.page.TableDataInfo;

/**
 * 来料检验单缺陷记录Controller
 * 
 * @author yinjinlu
 * @date 2022-05-19
 */
@RestController
@RequestMapping("/mes/qc/iqcdefect")
public class QcIqcDefectController extends BaseController
{
    @Autowired
    private IQcIqcDefectService qcIqcDefectService;

    @Autowired
    private IQcIqcLineService qcIqcLineService;

    @Autowired
    private IQcIqcService qcIqcService;

    /**
     * 查询来料检验单缺陷记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqcdefect:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcIqcDefect qcIqcDefect)
    {
        startPage();
        List<QcIqcDefect> list = qcIqcDefectService.selectQcIqcDefectList(qcIqcDefect);
        return getDataTable(list);
    }

    /**
     * 导出来料检验单缺陷记录列表
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqcdefect:export')")
    @Log(title = "来料检验单缺陷记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QcIqcDefect qcIqcDefect)
    {
        List<QcIqcDefect> list = qcIqcDefectService.selectQcIqcDefectList(qcIqcDefect);
        ExcelUtil<QcIqcDefect> util = new ExcelUtil<QcIqcDefect>(QcIqcDefect.class);
        util.exportExcel(response, list, "来料检验单缺陷记录数据");
    }

    /**
     * 获取来料检验单缺陷记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqcdefect:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(qcIqcDefectService.selectQcIqcDefectByRecordId(recordId));
    }

    /**
     * 新增来料检验单缺陷记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqcdefect:add')")
    @Log(title = "来料检验单缺陷记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcIqcDefect qcIqcDefect)
    {
        return toAjax(qcIqcDefectService.insertQcIqcDefect(qcIqcDefect));
    }


    /**
     * 修改来料检验单缺陷记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqcdefect:edit')")
    @Log(title = "来料检验单缺陷记录", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult updateList(@Validated @RequestBody ValidList<QcIqcDefect> defects){
        Long iqcId = -1L;
        Long lineId = -1L;
        if(CollUtil.isNotEmpty(defects)){
            for (QcIqcDefect defect: defects
                 ) {
                if(StringUtils.isNotNull(defect.getRecordId())){
                    qcIqcDefectService.updateQcIqcDefect(defect);
                }else {
                    qcIqcDefectService.insertQcIqcDefect(defect);
                }
                iqcId = defect.getIqcId();
                lineId = defect.getLineId();
            }
            //更新行上的cr,maj,min数量
            qcIqcLineService.updateCrMajMinQuantity(iqcId,lineId);
            //更新头上的cr,maj,min数量和比例
            qcIqcService.updateCrMajMinQuaAndRate(iqcId);
        }
        return AjaxResult.success();
    }


    /**
     * 删除来料检验单缺陷记录
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:iqcdefect:remove')")
    @Log(title = "来料检验单缺陷记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        Long iqcId =-1L;
        Long lineId =-1L;
        if(recordIds!=null && recordIds.length>0){
            QcIqcDefect defect = qcIqcDefectService.selectQcIqcDefectByRecordId(recordIds[0]);
            iqcId = defect.getIqcId();
            lineId = defect.getLineId();

            //更新行上的cr,maj,min数量
            qcIqcLineService.updateCrMajMinQuantity(iqcId,lineId);
            //更新头上的cr,maj,min数量和比例
            qcIqcService.updateCrMajMinQuaAndRate(iqcId);
        }

        return toAjax(qcIqcDefectService.deleteQcIqcDefectByRecordIds(recordIds));
    }
}
