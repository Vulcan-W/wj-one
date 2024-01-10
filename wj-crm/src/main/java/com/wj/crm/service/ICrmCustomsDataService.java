package com.wj.crm.service;

import java.util.List;
import com.wj.crm.domain.CrmCustomsData;

/**
 * 海关数据Service接口
 * 
 * @author Vulcan.W
 * @date 2024-01-10
 */
public interface ICrmCustomsDataService 
{
    /**
     * 查询海关数据
     * 
     * @param id 海关数据主键
     * @return 海关数据
     */
    public CrmCustomsData selectCrmCustomsDataById(Long id);

    /**
     * 查询海关数据列表
     * 
     * @param crmCustomsData 海关数据
     * @return 海关数据集合
     */
    public List<CrmCustomsData> selectCrmCustomsDataList(CrmCustomsData crmCustomsData);

    /**
     * 新增海关数据
     * 
     * @param crmCustomsData 海关数据
     * @return 结果
     */
    public int insertCrmCustomsData(CrmCustomsData crmCustomsData);

    /**
     * 修改海关数据
     * 
     * @param crmCustomsData 海关数据
     * @return 结果
     */
    public int updateCrmCustomsData(CrmCustomsData crmCustomsData);

    /**
     * 批量删除海关数据
     * 
     * @param ids 需要删除的海关数据主键集合
     * @return 结果
     */
    public int deleteCrmCustomsDataByIds(Long[] ids);

    /**
     * 删除海关数据信息
     * 
     * @param id 海关数据主键
     * @return 结果
     */
    public int deleteCrmCustomsDataById(Long id);
}
