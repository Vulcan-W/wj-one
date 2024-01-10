package com.wj.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wj.crm.mapper.CrmCustomsDataMapper;
import com.wj.crm.domain.CrmCustomsData;
import com.wj.crm.service.ICrmCustomsDataService;

/**
 * 海关数据Service业务层处理
 * 
 * @author Vulcan.W
 * @date 2024-01-10
 */
@Service
public class CrmCustomsDataServiceImpl implements ICrmCustomsDataService 
{
    @Autowired
    private CrmCustomsDataMapper crmCustomsDataMapper;

    /**
     * 查询海关数据
     * 
     * @param id 海关数据主键
     * @return 海关数据
     */
    @Override
    public CrmCustomsData selectCrmCustomsDataById(Long id)
    {
        return crmCustomsDataMapper.selectCrmCustomsDataById(id);
    }

    /**
     * 查询海关数据列表
     * 
     * @param crmCustomsData 海关数据
     * @return 海关数据
     */
    @Override
    public List<CrmCustomsData> selectCrmCustomsDataList(CrmCustomsData crmCustomsData)
    {
        return crmCustomsDataMapper.selectCrmCustomsDataList(crmCustomsData);
    }

    /**
     * 新增海关数据
     * 
     * @param crmCustomsData 海关数据
     * @return 结果
     */
    @Override
    public int insertCrmCustomsData(CrmCustomsData crmCustomsData)
    {
        return crmCustomsDataMapper.insertCrmCustomsData(crmCustomsData);
    }

    /**
     * 修改海关数据
     * 
     * @param crmCustomsData 海关数据
     * @return 结果
     */
    @Override
    public int updateCrmCustomsData(CrmCustomsData crmCustomsData)
    {
        return crmCustomsDataMapper.updateCrmCustomsData(crmCustomsData);
    }

    /**
     * 批量删除海关数据
     * 
     * @param ids 需要删除的海关数据主键
     * @return 结果
     */
    @Override
    public int deleteCrmCustomsDataByIds(Long[] ids)
    {
        return crmCustomsDataMapper.deleteCrmCustomsDataByIds(ids);
    }

    /**
     * 删除海关数据信息
     * 
     * @param id 海关数据主键
     * @return 结果
     */
    @Override
    public int deleteCrmCustomsDataById(Long id)
    {
        return crmCustomsDataMapper.deleteCrmCustomsDataById(id);
    }
}
