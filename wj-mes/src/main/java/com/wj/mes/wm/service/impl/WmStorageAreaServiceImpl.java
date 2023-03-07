package com.wj.mes.wm.service.impl;

import java.util.List;
import com.wj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wj.mes.wm.mapper.WmStorageAreaMapper;
import com.wj.mes.wm.domain.WmStorageArea;
import com.wj.mes.wm.service.IWmStorageAreaService;

/**
 * 库位设置Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-08
 */
@Service
public class WmStorageAreaServiceImpl implements IWmStorageAreaService 
{
    @Autowired
    private WmStorageAreaMapper wmStorageAreaMapper;

    /**
     * 查询库位设置
     * 
     * @param areaId 库位设置主键
     * @return 库位设置
     */
    @Override
    public WmStorageArea selectWmStorageAreaByAreaId(Long areaId)
    {
        return wmStorageAreaMapper.selectWmStorageAreaByAreaId(areaId);
    }

    /**
     * 查询库位设置列表
     * 
     * @param wmStorageArea 库位设置
     * @return 库位设置
     */
    @Override
    public List<WmStorageArea> selectWmStorageAreaList(WmStorageArea wmStorageArea)
    {
        return wmStorageAreaMapper.selectWmStorageAreaList(wmStorageArea);
    }

    /**
     * 新增库位设置
     * 
     * @param wmStorageArea 库位设置
     * @return 结果
     */
    @Override
    public int insertWmStorageArea(WmStorageArea wmStorageArea)
    {
        wmStorageArea.setCreateTime(DateUtils.getNowDate());
        return wmStorageAreaMapper.insertWmStorageArea(wmStorageArea);
    }

    /**
     * 修改库位设置
     * 
     * @param wmStorageArea 库位设置
     * @return 结果
     */
    @Override
    public int updateWmStorageArea(WmStorageArea wmStorageArea)
    {
        wmStorageArea.setUpdateTime(DateUtils.getNowDate());
        return wmStorageAreaMapper.updateWmStorageArea(wmStorageArea);
    }

    /**
     * 批量删除库位设置
     * 
     * @param areaIds 需要删除的库位设置主键
     * @return 结果
     */
    @Override
    public int deleteWmStorageAreaByAreaIds(Long[] areaIds)
    {
        return wmStorageAreaMapper.deleteWmStorageAreaByAreaIds(areaIds);
    }

    /**
     * 删除库位设置信息
     * 
     * @param areaId 库位设置主键
     * @return 结果
     */
    @Override
    public int deleteWmStorageAreaByAreaId(Long areaId)
    {
        return wmStorageAreaMapper.deleteWmStorageAreaByAreaId(areaId);
    }
}
