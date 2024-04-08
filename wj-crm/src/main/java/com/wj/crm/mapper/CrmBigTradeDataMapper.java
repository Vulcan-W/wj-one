package com.wj.crm.mapper;

import java.util.List;
import com.wj.crm.domain.CrmBigTradeData;

/**
 * 某通数据Mapper接口
 * 
 * @author Vulcan.W
 * @date 2024-01-25
 */
public interface CrmBigTradeDataMapper 
{
    /**
     * 查询某通数据
     * 
     * @param id 某通数据主键
     * @return 某通数据
     */
    public CrmBigTradeData selectCrmBigTradeDataById(Long id);

    /**
     * 查询某通数据列表
     * 
     * @param crmBigTradeData 某通数据
     * @return 某通数据集合
     */
    public List<CrmBigTradeData> selectCrmBigTradeDataList(CrmBigTradeData crmBigTradeData);

    /**
     * 新增某通数据
     * 
     * @param crmBigTradeData 某通数据
     * @return 结果
     */
    public int insertCrmBigTradeData(CrmBigTradeData crmBigTradeData);

    /**
     * 修改某通数据
     * 
     * @param crmBigTradeData 某通数据
     * @return 结果
     */
    public int updateCrmBigTradeData(CrmBigTradeData crmBigTradeData);

    /**
     * 删除某通数据
     * 
     * @param id 某通数据主键
     * @return 结果
     */
    public int deleteCrmBigTradeDataById(Long id);

    /**
     * 批量删除某通数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrmBigTradeDataByIds(Long[] ids);
}
