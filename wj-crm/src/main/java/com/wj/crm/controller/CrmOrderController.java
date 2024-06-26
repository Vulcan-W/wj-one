package com.wj.crm.controller;

import com.wj.common.annotation.Log;
import com.wj.common.core.controller.BaseController;
import com.wj.common.core.domain.AjaxResult;
import com.wj.common.core.page.TableDataInfo;
import com.wj.common.enums.BusinessType;
import com.wj.common.utils.poi.ExcelUtil;
import com.wj.crm.domain.CrmOrder;
import com.wj.crm.service.ICrmOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单Controller
 * 
 * @author Vulcan.W
 * @date 2024-01-19
 */
@RestController
@RequestMapping("/crm/order")
public class CrmOrderController extends BaseController
{
    @Autowired
    private ICrmOrderService crmOrderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('crm:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(CrmOrder crmOrder)
    {
        startPage();
        List<CrmOrder> list = crmOrderService.selectCrmOrderList(crmOrder);
        return getDataTable(list);
    }

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('crm:order:person:list')")
    @GetMapping("/person-list")
    public TableDataInfo personList(CrmOrder crmOrder)
    {
        startPage();
        crmOrder.setOwner(getUsername());
        List<CrmOrder> list = crmOrderService.selectCrmOrderList(crmOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('crm:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrmOrder crmOrder)
    {
        List<CrmOrder> list = crmOrderService.selectCrmOrderList(crmOrder);
        ExcelUtil<CrmOrder> util = new ExcelUtil<CrmOrder>(CrmOrder.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('crm:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(crmOrderService.selectCrmOrderById(id));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('crm:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrmOrder crmOrder)
    {
        crmOrder.setOwner(getUsername());
        crmOrder.setCreateBy(getUsername());
        return toAjax(crmOrderService.insertCrmOrder(crmOrder));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('crm:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrmOrder crmOrder)
    {
        crmOrder.setUpdateBy(getUsername());
        return toAjax(crmOrderService.updateCrmOrder(crmOrder));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('crm:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(crmOrderService.deleteCrmOrderByIds(ids));
    }

    /** 审批订单 */
    @PreAuthorize("@ss.hasPermi('crm:order:approve')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{id}")
    public AjaxResult approve(@PathVariable Long id){

        return toAjax(crmOrderService.approve(id));
    }
}
