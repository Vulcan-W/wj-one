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
import com.wj.crm.domain.CrmCustomsData;
import com.wj.crm.service.ICrmCustomsDataService;
import com.wj.common.utils.poi.ExcelUtil;
import com.wj.common.core.page.TableDataInfo;

/**
 * 海关数据Controller
 * 
 * @author Vulcan.W
 * @date 2024-01-10
 */
@RestController
@RequestMapping("/crm/customs/customsData")
public class CrmCustomsDataController extends BaseController
{
    @Autowired
    private ICrmCustomsDataService crmCustomsDataService;

    /**
     * 查询海关数据列表
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:customsData:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrmCustomsData crmCustomsData)
    {
        startPage();
        List<CrmCustomsData> list = crmCustomsDataService.selectCrmCustomsDataList(crmCustomsData);
        return getDataTable(list);
    }

    /**
     * 导出海关数据列表
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:customsData:export')")
    @Log(title = "海关数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmCustomsData crmCustomsData)
    {
        List<CrmCustomsData> list = crmCustomsDataService.selectCrmCustomsDataList(crmCustomsData);
        ExcelUtil<CrmCustomsData> util = new ExcelUtil<CrmCustomsData>(CrmCustomsData.class);
        util.exportExcel(response, list, "海关数据数据");
    }

    /**
     * 获取海关数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:customsData:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(crmCustomsDataService.selectCrmCustomsDataById(id));
    }

    /**
     * 新增海关数据
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:customsData:add')")
    @Log(title = "海关数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmCustomsData crmCustomsData)
    {
        return toAjax(crmCustomsDataService.insertCrmCustomsData(crmCustomsData));
    }

    /**
     * 修改海关数据
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:customsData:edit')")
    @Log(title = "海关数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmCustomsData crmCustomsData)
    {
        return toAjax(crmCustomsDataService.updateCrmCustomsData(crmCustomsData));
    }

    /**
     * 删除海关数据
     */
    @PreAuthorize("@ss.hasPermi('crm:customs:customsData:remove')")
    @Log(title = "海关数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(crmCustomsDataService.deleteCrmCustomsDataByIds(ids));
    }
}
