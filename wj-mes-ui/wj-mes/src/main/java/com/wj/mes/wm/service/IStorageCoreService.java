package com.wj.mes.wm.service;

import com.wj.mes.wm.domain.tx.ItemRecptTxBean;
import com.wj.mes.wm.domain.tx.RtVendorTxBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IStorageCoreService {

    /**
     * 处理物料入库单
     * @param lines
     */
    public void processItemRecpt(List<ItemRecptTxBean> lines);

    /**
     * 处理供应商退货单
     * @param lines
     */
    public void processRtVendor(List<RtVendorTxBean> lines);


}
