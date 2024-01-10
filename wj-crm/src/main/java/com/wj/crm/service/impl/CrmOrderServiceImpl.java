package com.wj.crm.service.impl;

import com.wj.common.utils.DateUtils;
import com.wj.crm.domain.CrmCustomer;
import com.wj.crm.domain.CrmOrder;
import com.wj.crm.domain.enums.CustomerFolder;
import com.wj.crm.mapper.CrmOrderMapper;
import com.wj.crm.service.ICrmCustomerService;
import com.wj.crm.service.ICrmOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单Service业务层处理
 * 
 * @author Vulcan.W
 * @date 2024-01-19
 */
@Service
public class CrmOrderServiceImpl implements ICrmOrderService 
{
    @Autowired
    private CrmOrderMapper crmOrderMapper;

    @Autowired
    private ICrmCustomerService customerService;

    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public CrmOrder selectCrmOrderById(Long id)
    {
        return crmOrderMapper.selectCrmOrderById(id);
    }

    /**
     * 查询订单列表
     * 
     * @param crmOrder 订单
     * @return 订单
     */
    @Override
    public List<CrmOrder> selectCrmOrderList(CrmOrder crmOrder)
    {
        return crmOrderMapper.selectCrmOrderList(crmOrder);
    }

    /**
     * 新增订单
     * 
     * @param crmOrder 订单
     * @return 结果
     */
    @Override
    public int insertCrmOrder(CrmOrder crmOrder)
    {
        crmOrder.setCreateTime(DateUtils.getNowDate());
        return crmOrderMapper.insertCrmOrder(crmOrder);
    }

    /**
     * 修改订单
     * 
     * @param crmOrder 订单
     * @return 结果
     */
    @Override
    public int updateCrmOrder(CrmOrder crmOrder)
    {
        crmOrder.setUpdateTime(DateUtils.getNowDate());
        return crmOrderMapper.updateCrmOrder(crmOrder);
    }

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteCrmOrderByIds(Long[] ids)
    {
        return crmOrderMapper.deleteCrmOrderByIds(ids);
    }

    /**
     * 删除订单信息
     * 
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteCrmOrderById(Long id)
    {
        return crmOrderMapper.deleteCrmOrderById(id);
    }

    /** 审核订单 */
    @Transactional
    @Override
    public int approve(Long id) {
        CrmOrder crmOrder = crmOrderMapper.selectCrmOrderById(id);
        crmOrder.setStatus("1");

        CrmCustomer crmCustomer = customerService.selectCrmCustomerById(crmOrder.getCustomerId());
        crmCustomer.setStatus(CustomerFolder.CUSTOMER.getCode());
        crmCustomer.setDealStatus("1");
        customerService.updateCrmCustomer(crmCustomer);
        return crmOrderMapper.updateCrmOrder(crmOrder);
    }
}
