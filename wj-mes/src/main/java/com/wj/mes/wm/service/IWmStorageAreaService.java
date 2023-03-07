package com.wj.mes.wm.service;

import java.util.List;
import com.wj.mes.wm.domain.WmStorageArea;

/**
 * 库位设置Service接口
 * 
 * @author yinjinlu
 * @date 2022-05-08
 */
public interface IWmStorageAreaService 
{
    /**
     * 查询库位设置
     * 
     * @param areaId 库位设置主键
     * @return 库位设置
     */
    public WmStorageArea selectWmStorageAreaByAreaId(Long areaId);

    /**
     * 查询库位设置列表
     * 
     * @param wmStorageArea 库位设置
     * @return 库位设置集合
     */
    public List<WmStorageArea> selectWmStorageAreaList(WmStorageArea wmStorageArea);

    /**
     * 新增库位设置
     * 
     * @param wmStorageArea 库位设置
     * @return 结果
     */
    public int insertWmStorageArea(WmStorageArea wmStorageArea);

    /**
     * 修改库位设置
     * 
     * @param wmStorageArea 库位设置
     * @return 结果
     */
    public int updateWmStorageArea(WmStorageArea wmStorageArea);

    /**
     * 批量删除库位设置
     * 
     * @param areaIds 需要删除的库位设置主键集合
     * @return 结果
     */
    public int deleteWmStorageAreaByAreaIds(Long[] areaIds);

    /**
     * 删除库位设置信息
     * 
     * @param areaId 库位设置主键
     * @return 结果
     */
    public int deleteWmStorageAreaByAreaId(Long areaId);
}
