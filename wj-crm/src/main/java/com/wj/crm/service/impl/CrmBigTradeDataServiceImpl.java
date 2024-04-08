package com.wj.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wj.crm.mapper.CrmBigTradeDataMapper;
import com.wj.crm.domain.CrmBigTradeData;
import com.wj.crm.service.ICrmBigTradeDataService;

/**
 * 某通数据Service业务层处理
 * 
 * @author Vulcan.W
 * @date 2024-01-25
 */
@Service
public class CrmBigTradeDataServiceImpl implements ICrmBigTradeDataService 
{
    @Autowired
    private CrmBigTradeDataMapper crmBigTradeDataMapper;

    /**
     * 查询某通数据
     * 
     * @param id 某通数据主键
     * @return 某通数据
     */
    @Override
    public CrmBigTradeData selectCrmBigTradeDataById(Long id)
    {
        return crmBigTradeDataMapper.selectCrmBigTradeDataById(id);
    }

    /**
     * 查询某通数据列表
     * 
     * @param crmBigTradeData 某通数据
     * @return 某通数据
     */
    @Override
    public List<CrmBigTradeData> selectCrmBigTradeDataList(CrmBigTradeData crmBigTradeData)
    {
        return crmBigTradeDataMapper.selectCrmBigTradeDataList(crmBigTradeData);
    }

    /**
     * 新增某通数据
     * 
     * @param crmBigTradeData 某通数据
     * @return 结果
     */
    @Override
    public int insertCrmBigTradeData(CrmBigTradeData crmBigTradeData)
    {
        return crmBigTradeDataMapper.insertCrmBigTradeData(crmBigTradeData);
    }

    /**
     * 修改某通数据
     * 
     * @param crmBigTradeData 某通数据
     * @return 结果
     */
    @Override
    public int updateCrmBigTradeData(CrmBigTradeData crmBigTradeData)
    {
        return crmBigTradeDataMapper.updateCrmBigTradeData(crmBigTradeData);
    }

    /**
     * 批量删除某通数据
     * 
     * @param ids 需要删除的某通数据主键
     * @return 结果
     */
    @Override
    public int deleteCrmBigTradeDataByIds(Long[] ids)
    {
        return crmBigTradeDataMapper.deleteCrmBigTradeDataByIds(ids);
    }

    /**
     * 删除某通数据信息
     * 
     * @param id 某通数据主键
     * @return 结果
     */
    @Override
    public int deleteCrmBigTradeDataById(Long id)
    {
        return crmBigTradeDataMapper.deleteCrmBigTradeDataById(id);
    }
}
