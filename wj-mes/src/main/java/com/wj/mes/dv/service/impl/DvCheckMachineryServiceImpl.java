package com.wj.mes.dv.service.impl;

import java.util.List;

import com.wj.common.constant.UserConstants;
import com.wj.common.utils.DateUtils;
import com.wj.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wj.mes.dv.mapper.DvCheckMachineryMapper;
import com.wj.mes.dv.domain.DvCheckMachinery;
import com.wj.mes.dv.service.IDvCheckMachineryService;

/**
 * 点检设备Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-06-17
 */
@Service
public class DvCheckMachineryServiceImpl implements IDvCheckMachineryService 
{
    @Autowired
    private DvCheckMachineryMapper dvCheckMachineryMapper;

    /**
     * 查询点检设备
     * 
     * @param recordId 点检设备主键
     * @return 点检设备
     */
    @Override
    public DvCheckMachinery selectDvCheckMachineryByRecordId(Long recordId)
    {
        return dvCheckMachineryMapper.selectDvCheckMachineryByRecordId(recordId);
    }

    /**
     * 查询点检设备列表
     * 
     * @param dvCheckMachinery 点检设备
     * @return 点检设备
     */
    @Override
    public List<DvCheckMachinery> selectDvCheckMachineryList(DvCheckMachinery dvCheckMachinery)
    {
        return dvCheckMachineryMapper.selectDvCheckMachineryList(dvCheckMachinery);
    }

    @Override
    public String checkMachineryUnique(DvCheckMachinery dvCheckMachinery) {
        DvCheckMachinery machinery = dvCheckMachineryMapper.checkMachineryUnique(dvCheckMachinery);
        Long recordId = dvCheckMachinery.getRecordId()==null?-1L:dvCheckMachinery.getRecordId();
        if(StringUtils.isNotNull(machinery) && machinery.getRecordId().longValue() != recordId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增点检设备
     * 
     * @param dvCheckMachinery 点检设备
     * @return 结果
     */
    @Override
    public int insertDvCheckMachinery(DvCheckMachinery dvCheckMachinery)
    {
        dvCheckMachinery.setCreateTime(DateUtils.getNowDate());
        return dvCheckMachineryMapper.insertDvCheckMachinery(dvCheckMachinery);
    }

    /**
     * 修改点检设备
     * 
     * @param dvCheckMachinery 点检设备
     * @return 结果
     */
    @Override
    public int updateDvCheckMachinery(DvCheckMachinery dvCheckMachinery)
    {
        dvCheckMachinery.setUpdateTime(DateUtils.getNowDate());
        return dvCheckMachineryMapper.updateDvCheckMachinery(dvCheckMachinery);
    }

    /**
     * 批量删除点检设备
     * 
     * @param recordIds 需要删除的点检设备主键
     * @return 结果
     */
    @Override
    public int deleteDvCheckMachineryByRecordIds(Long[] recordIds)
    {
        return dvCheckMachineryMapper.deleteDvCheckMachineryByRecordIds(recordIds);
    }

    /**
     * 删除点检设备信息
     * 
     * @param recordId 点检设备主键
     * @return 结果
     */
    @Override
    public int deleteDvCheckMachineryByRecordId(Long recordId)
    {
        return dvCheckMachineryMapper.deleteDvCheckMachineryByRecordId(recordId);
    }
}
