package com.wj.crm.service.impl;

import com.wj.common.utils.DateUtils;
import com.wj.crm.domain.CrmCustomer;
import com.wj.crm.domain.enums.CustomerFolder;
import com.wj.crm.mapper.CrmCustomerMapper;
import com.wj.crm.service.ICrmCustomerService;
import com.wj.crm.vo.TodayUpdatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户Service业务层处理
 * 
 * @author Vulcan.W
 * @date 2022-01-12
 */
@Service
public class CrmCustomerServiceImpl implements ICrmCustomerService 
{
    @Autowired
    private CrmCustomerMapper crmCustomerMapper;

    /**
     * 查询客户
     * 
     * @param id 客户主键
     * @return 客户
     */
    @Override
    public CrmCustomer selectCrmCustomerById(Long id)
    {
        return crmCustomerMapper.selectCrmCustomerById(id);
    }

    /**
     * 查询客户列表
     * 
     * @param crmCustomer 客户
     * @return 客户
     */
    @Override
    public List<CrmCustomer> selectCrmCustomerList(CrmCustomer crmCustomer)
    {
        return crmCustomerMapper.selectCrmCustomerList(crmCustomer);
    }

    /**
     * 新增客户
     * 
     * @param crmCustomer 客户
     * @return 结果
     */
    @Override
    public int insertCrmCustomer(CrmCustomer crmCustomer)
    {
        crmCustomer.setCreateTime(DateUtils.getNowDate());
        return crmCustomerMapper.insertCrmCustomer(crmCustomer);
    }

    /**
     * 修改客户
     * 
     * @param crmCustomer 客户
     * @return 结果
     */
    @Override
    public int updateCrmCustomer(CrmCustomer crmCustomer)
    {
        crmCustomer.setUpdateTime(DateUtils.getNowDate());
        return crmCustomerMapper.updateCrmCustomer(crmCustomer);
    }

    /**
     * 批量删除客户
     * 
     * @param ids 需要删除的客户主键
     * @return 结果
     */
    @Override
    public int deleteCrmCustomerByIds(Long[] ids)
    {
        return crmCustomerMapper.deleteCrmCustomerByIds(ids);
    }

    /**
     * 转移
     * @param ids 需要转移的客户主键集合
     * @param newOwner 新负责人
     * @param oldOwner 旧负责人
     * @return
     */
    @Override
    @Transactional
    public int transferCrmCustomerByIds(Long[] ids, String newOwner, String oldOwner) {
        return crmCustomerMapper.transferCrmCustomerByIds(ids, newOwner, oldOwner);
    }

    /**
     * 移入公海
     * @param ids 需要转移的客户主键集合
     * @param poolType 公海类型
     * @return
     */
    @Override
    @Transactional
    public int customerToPoolByIds(Long[] ids, String poolType) {
        return crmCustomerMapper.customerToPoolByIds(ids, poolType, CustomerFolder.POOL.getCode());
    }

    /**
     * 删除客户信息
     * 
     * @param id 客户主键
     * @return 结果
     */
    @Override
    public int deleteCrmCustomerById(Long id)
    {
        return crmCustomerMapper.deleteCrmCustomerById(id);
    }

    /**
     * 领取客户
     * @param id 客户ID
     * @param owner 负责人
     * @return
     */
    @Override
    public int receiveCustomerById(Long id, String owner) {
        return crmCustomerMapper.receiveCustomerById(id, owner, CustomerFolder.CUSTOMER.getCode());
    }

    /**
     * 今日动态
     * @param owner
     * @return
     */
    @Override
    public List<TodayUpdatesVO> todayUpdates(String owner){
        return crmCustomerMapper.todayUpdates(owner);
    }
}
