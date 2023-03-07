package com.wj.mes.wm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import com.wj.common.constant.UserConstants;
import com.wj.common.utils.StringUtils;
import com.wj.mes.wm.domain.*;
import com.wj.mes.wm.domain.tx.ItemRecptTxBean;
import com.wj.mes.wm.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.wj.common.utils.poi.ExcelUtil;
import com.wj.common.core.page.TableDataInfo;

/**
 * 物料入库单Controller
 * 
 * @author yinjinlu
 * @date 2022-05-22
 */
@RestController
@RequestMapping("/mes/wm/itemrecpt")
public class WmItemRecptController extends BaseController
{
    @Autowired
    private IWmItemRecptService wmItemRecptService;

    @Autowired
    private IWmItemRecptLineService wmItemRecptLineService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private IStorageCoreService storageCoreService;

    /**
     * 查询物料入库单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmItemRecpt wmItemRecpt)
    {
        startPage();
        List<WmItemRecpt> list = wmItemRecptService.selectWmItemRecptList(wmItemRecpt);
        return getDataTable(list);
    }

    /**
     * 导出物料入库单列表
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:export')")
    @Log(title = "物料入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmItemRecpt wmItemRecpt)
    {
        List<WmItemRecpt> list = wmItemRecptService.selectWmItemRecptList(wmItemRecpt);
        ExcelUtil<WmItemRecpt> util = new ExcelUtil<WmItemRecpt>(WmItemRecpt.class);
        util.exportExcel(response, list, "物料入库单数据");
    }

    /**
     * 获取物料入库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:query')")
    @GetMapping(value = "/{recptId}")
    public AjaxResult getInfo(@PathVariable("recptId") Long recptId)
    {
        return AjaxResult.success(wmItemRecptService.selectWmItemRecptByRecptId(recptId));
    }

    /**
     * 新增物料入库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:add')")
    @Log(title = "物料入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmItemRecpt wmItemRecpt)
    {
        if(UserConstants.NOT_UNIQUE.equals(wmItemRecptService.checkRecptCodeUnique(wmItemRecpt))){
            return  AjaxResult.error("单据编号已存在！");
        }

        if(StringUtils.isNotNull(wmItemRecpt.getWarehouseId())){
           WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmItemRecpt.getWarehouseId());
           wmItemRecpt.setWarehouseCode(warehouse.getWarehouseCode());
           wmItemRecpt.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmItemRecpt.getLocationId())){
           WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmItemRecpt.getLocationId());
           wmItemRecpt.setLocationCode(location.getLocationCode());
           wmItemRecpt.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmItemRecpt.getAreaId())){
           WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmItemRecpt.getAreaId());
           wmItemRecpt.setAreaCode(area.getAreaCode());
           wmItemRecpt.setAreaName(area.getAreaName());
        }

        return toAjax(wmItemRecptService.insertWmItemRecpt(wmItemRecpt));
    }

    /**
     * 确认入库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:edit')")
    @Log(title = "物料入库单", businessType = BusinessType.UPDATE)
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestBody WmItemRecpt wmItemRecpt){
        //检查有没有入库单行
        WmItemRecptLine param = new WmItemRecptLine();
        param.setRecptId(wmItemRecpt.getRecptId());
        List<WmItemRecptLine> lines = wmItemRecptLineService.selectWmItemRecptLineList(param);
        if(CollUtil.isEmpty(lines)){
            return AjaxResult.error("请添加入库单行");
        }

        wmItemRecpt.setStatus(UserConstants.ORDER_STATUS_CONFIRMED);

        if(StringUtils.isNotNull(wmItemRecpt.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmItemRecpt.getWarehouseId());
            wmItemRecpt.setWarehouseCode(warehouse.getWarehouseCode());
            wmItemRecpt.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmItemRecpt.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmItemRecpt.getLocationId());
            wmItemRecpt.setLocationCode(location.getLocationCode());
            wmItemRecpt.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmItemRecpt.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmItemRecpt.getAreaId());
            wmItemRecpt.setAreaCode(area.getAreaCode());
            wmItemRecpt.setAreaName(area.getAreaName());
        }
        return toAjax(wmItemRecptService.updateWmItemRecpt(wmItemRecpt));
    }


    /**
     * 修改物料入库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:edit')")
    @Log(title = "物料入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmItemRecpt wmItemRecpt)
    {
        if(StringUtils.isNotNull(wmItemRecpt.getWarehouseId())){
            WmWarehouse warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(wmItemRecpt.getWarehouseId());
            wmItemRecpt.setWarehouseCode(warehouse.getWarehouseCode());
            wmItemRecpt.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StringUtils.isNotNull(wmItemRecpt.getLocationId())){
            WmStorageLocation location = wmStorageLocationService.selectWmStorageLocationByLocationId(wmItemRecpt.getLocationId());
            wmItemRecpt.setLocationCode(location.getLocationCode());
            wmItemRecpt.setLocationName(location.getLocationName());
        }
        if(StringUtils.isNotNull(wmItemRecpt.getAreaId())){
            WmStorageArea area = wmStorageAreaService.selectWmStorageAreaByAreaId(wmItemRecpt.getAreaId());
            wmItemRecpt.setAreaCode(area.getAreaCode());
            wmItemRecpt.setAreaName(area.getAreaName());
        }
        return toAjax(wmItemRecptService.updateWmItemRecpt(wmItemRecpt));
    }

    /**
     * 执行入库
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:edit')")
    @Log(title = "物料入库单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/{recptId}")
    public AjaxResult execute(@PathVariable Long recptId){

        WmItemRecpt recpt = wmItemRecptService.selectWmItemRecptByRecptId(recptId);

        if (!UserConstants.ORDER_STATUS_CONFIRMED.equals(recpt.getStatus())){
            return AjaxResult.error("请先确认单据");
        }

        //构造Transaction事务，并执行库存更新逻辑
        List<ItemRecptTxBean> beans = wmItemRecptService.getTxBeans(recptId);

        //调用库存核心
        storageCoreService.processItemRecpt(beans);

        //更新单据状态
        WmItemRecpt wmItemRecpt =wmItemRecptService.selectWmItemRecptByRecptId(recptId);
        wmItemRecpt.setStatus(UserConstants.ORDER_STATUS_FINISHED);
        wmItemRecptService.updateWmItemRecpt(wmItemRecpt);


        return AjaxResult.success();
    }


    /**
     * 删除物料入库单
     */
    @PreAuthorize("@ss.hasPermi('mes:wm:itemrecpt:remove')")
    @Log(title = "物料入库单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{recptIds}")
    public AjaxResult remove(@PathVariable Long[] recptIds)
    {
        for (Long id:
                recptIds
             ) {
            wmItemRecptLineService.deleteByRecptId(id);
        }

        return toAjax(wmItemRecptService.deleteWmItemRecptByRecptIds(recptIds));
    }
}
