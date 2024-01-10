package com.wj.crm.domain;

import com.wj.common.annotation.Excel;
import com.wj.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 海关数据对象 crm_trade_data
 * 
 * @author Vulcan.W
 * @date 2024-01-10
 */
public class CrmCustomsData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private Long productCode;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 贸易伙伴编码 */
    @Excel(name = "贸易伙伴编码")
    private Long tradePartnerCode;

    /** 贸易伙伴名称 */
    @Excel(name = "贸易伙伴名称")
    private String tradePartnerName;

    /** 贸易方式编码
 */
    @Excel(name = "贸易方式编码 ")
    private Long tradeMethodCode;

    /** 贸易方式名称 */
    @Excel(name = "贸易方式名称")
    private String tradeMethodName;

    /** 注册地编码 */
    @Excel(name = "注册地编码")
    private Long locationCode;

    /** 注册地名称 */
    @Excel(name = "注册地名称")
    private String locationName;

    /** 第一数量 */
    @Excel(name = "第一数量")
    private Long firstQuantity;

    /** 第一计量单位
 */
    @Excel(name = "第一计量单位 ")
    private String firstMeasurementUnit;

    /** 第二数量 */
    @Excel(name = "第二数量")
    private Long secondQuantity;

    /** 第二计量单位 */
    @Excel(name = "第二计量单位")
    private String secondMeasurementUnit;

    /** 金额/人民币 */
    @Excel(name = "金额/人民币")
    private Long tradeAmount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductCode(Long productCode) 
    {
        this.productCode = productCode;
    }

    public Long getProductCode() 
    {
        return productCode;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setTradePartnerCode(Long tradePartnerCode) 
    {
        this.tradePartnerCode = tradePartnerCode;
    }

    public Long getTradePartnerCode() 
    {
        return tradePartnerCode;
    }
    public void setTradePartnerName(String tradePartnerName) 
    {
        this.tradePartnerName = tradePartnerName;
    }

    public String getTradePartnerName() 
    {
        return tradePartnerName;
    }
    public void setTradeMethodCode(Long tradeMethodCode) 
    {
        this.tradeMethodCode = tradeMethodCode;
    }

    public Long getTradeMethodCode() 
    {
        return tradeMethodCode;
    }
    public void setTradeMethodName(String tradeMethodName) 
    {
        this.tradeMethodName = tradeMethodName;
    }

    public String getTradeMethodName() 
    {
        return tradeMethodName;
    }
    public void setLocationCode(Long locationCode) 
    {
        this.locationCode = locationCode;
    }

    public Long getLocationCode() 
    {
        return locationCode;
    }
    public void setLocationName(String locationName) 
    {
        this.locationName = locationName;
    }

    public String getLocationName() 
    {
        return locationName;
    }
    public void setFirstQuantity(Long firstQuantity) 
    {
        this.firstQuantity = firstQuantity;
    }

    public Long getFirstQuantity() 
    {
        return firstQuantity;
    }
    public void setFirstMeasurementUnit(String firstMeasurementUnit) 
    {
        this.firstMeasurementUnit = firstMeasurementUnit;
    }

    public String getFirstMeasurementUnit() 
    {
        return firstMeasurementUnit;
    }
    public void setSecondQuantity(Long secondQuantity) 
    {
        this.secondQuantity = secondQuantity;
    }

    public Long getSecondQuantity() 
    {
        return secondQuantity;
    }
    public void setSecondMeasurementUnit(String secondMeasurementUnit) 
    {
        this.secondMeasurementUnit = secondMeasurementUnit;
    }

    public String getSecondMeasurementUnit() 
    {
        return secondMeasurementUnit;
    }
    public void setTradeAmount(Long tradeAmount) 
    {
        this.tradeAmount = tradeAmount;
    }

    public Long getTradeAmount() 
    {
        return tradeAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("tradePartnerCode", getTradePartnerCode())
            .append("tradePartnerName", getTradePartnerName())
            .append("tradeMethodCode", getTradeMethodCode())
            .append("tradeMethodName", getTradeMethodName())
            .append("locationCode", getLocationCode())
            .append("locationName", getLocationName())
            .append("firstQuantity", getFirstQuantity())
            .append("firstMeasurementUnit", getFirstMeasurementUnit())
            .append("secondQuantity", getSecondQuantity())
            .append("secondMeasurementUnit", getSecondMeasurementUnit())
            .append("tradeAmount", getTradeAmount())
            .toString();
    }
}
