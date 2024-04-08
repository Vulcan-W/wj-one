package com.wj.crm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.wj.crm.domain.CrmBigTradeData;
import com.wj.crm.service.ICrmBigTradeDataService;
import com.wj.common.utils.poi.ExcelUtil;
import com.wj.common.core.page.TableDataInfo;

/**
 * 某通数据Controller
 * 
 * @author Vulcan.W
 * @date 2024-01-25
 */
@RestController
@RequestMapping("crm/customs/bigTradeData")
public class CrmBigTradeDataController extends BaseController
{
    @Autowired
    private ICrmBigTradeDataService crmBigTradeDataService;

    /**
     * 查询某通数据列表
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:bigTradeData:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrmBigTradeData crmBigTradeData)
    {
        startPage();
        List<CrmBigTradeData> list = crmBigTradeDataService.selectCrmBigTradeDataList(crmBigTradeData);
        return getDataTable(list);
    }

    /**
     * 导出某通数据列表
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:bigTradeData:export')")
    @Log(title = "某通数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmBigTradeData crmBigTradeData)
    {
        List<CrmBigTradeData> list = crmBigTradeDataService.selectCrmBigTradeDataList(crmBigTradeData);
        ExcelUtil<CrmBigTradeData> util = new ExcelUtil<CrmBigTradeData>(CrmBigTradeData.class);
        util.exportExcel(response, list, "某通数据数据");
    }

    /**
     * 获取某通数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:bigTradeData:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(crmBigTradeDataService.selectCrmBigTradeDataById(id));
    }

    /**
     * 新增某通数据
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:bigTradeData:add')")
    @Log(title = "某通数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmBigTradeData crmBigTradeData)
    {
        return toAjax(crmBigTradeDataService.insertCrmBigTradeData(crmBigTradeData));
    }

    /**
     * 修改某通数据
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:bigTradeData:edit')")
    @Log(title = "某通数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmBigTradeData crmBigTradeData)
    {
        return toAjax(crmBigTradeDataService.updateCrmBigTradeData(crmBigTradeData));
    }

    /**
     * 删除某通数据
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:bigTradeData:remove')")
    @Log(title = "某通数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(crmBigTradeDataService.deleteCrmBigTradeDataByIds(ids));
    }
}
