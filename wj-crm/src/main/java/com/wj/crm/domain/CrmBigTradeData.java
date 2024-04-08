package com.wj.crm.domain;

import com.wj.common.annotation.Excel;
import com.wj.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 某通数据对象 crm_bigtradedata
 * 
 * @author Vulcan.W
 * @date 2024-01-25
 */
public class CrmBigTradeData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 海关编码 */
    @Excel(name = "海关编码")
    private String customsCode;

    /** 编码产品描述 */
    @Excel(name = "编码产品描述")
    private String customsCodeDes;

    /** 交易日期 */
    @Excel(name = "交易日期")
    private String tradeDate;

    /** 月份 */
    @Excel(name = "月份")
    private Long dataMonth;

    /** 进口商 */
    @Excel(name = "进口商")
    private String importer;

    /** 进口商所在国家 */
    @Excel(name = "进口商所在国家")
    private String importerCountry;

    /** 出口商 */
    @Excel(name = "出口商")
    private String exporter;

    /** 出口商所在国家 */
    @Excel(name = "出口商所在国家")
    private String exporterCountry;

    /** 重量单位 */
    @Excel(name = "重量单位")
    private String weightUnit;

    /** 数量单位 */
    @Excel(name = "数量单位")
    private String quantityUnit;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 毛重 */
    @Excel(name = "毛重")
    private Long weightGross;

    /** 净重 */
    @Excel(name = "净重")
    private Long weight;

    /** 公吨 */
    @Excel(name = "公吨")
    private Long tonne;

    /** 金额美元 */
    @Excel(name = "金额美元")
    private Long amount;

    /** 美元重量计单价 */
    @Excel(name = "美元重量计单价")
    private Long priceUnitWeight;

    /** 美元数量计单价 */
    @Excel(name = "美元数量计单价")
    private Long priceUnitQuantity;

    /** 本国币种金额 */
    @Excel(name = "本国币种金额")
    private Long amountLocal;

    /** 合同金额 */
    @Excel(name = "合同金额")
    private Long amountContract;

    /** 币种 */
    @Excel(name = "币种")
    private String currency;

    /** 成交方式 */
    @Excel(name = "成交方式")
    private String dealMethod;

    /** 详细产品名称 */
    @Excel(name = "详细产品名称")
    private String productNameDetail;

    /** 产品规格型号品牌 */
    @Excel(name = "产品规格型号品牌")
    private String productModel;

    /** 当地港口 */
    @Excel(name = "当地港口")
    private String portLocal;

    /** 国外港口 */
    @Excel(name = "国外港口")
    private String portAbroad;

    /** 运输方式 */
    @Excel(name = "运输方式")
    private String transportMethod;

    /** 贸易方式 */
    @Excel(name = "贸易方式")
    private String tradeMethod;

    /** 中转国 */
    @Excel(name = "中转国")
    private String countryTransit;

    /** 提单号 */
    @Excel(name = "提单号")
    private String billLadingNo;

    /** 编码产品描述本国语言 */
    @Excel(name = "编码产品描述本国语言")
    private String productDesLocal;

    /** 详细产品名称本国语言 */
    @Excel(name = "详细产品名称本国语言")
    private String productNameDetailLocal;

    /** 产品规格型号品牌本国语言 */
    @Excel(name = "产品规格型号品牌本国语言")
    private String productModelLocal;

    /** 进口商本地语言 */
    @Excel(name = "进口商本地语言")
    private String importerLocalLang;

    /** 数据库 */
    @Excel(name = "数据库")
    private String databaseName;

    /** 出口商本地语言 */
    @Excel(name = "出口商本地语言")
    private String exporterLocalLang;

    /** 关单号 */
    @Excel(name = "关单号")
    private String customsClearanceNo;

    /** 申报数量 */
    @Excel(name = "申报数量")
    private Long quantityDeclared;

    /** 进出口 */
    @Excel(name = "进出口")
    private String importExport;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomsCode(String customsCode) 
    {
        this.customsCode = customsCode;
    }

    public String getCustomsCode() 
    {
        return customsCode;
    }
    public void setCustomsCodeDes(String customsCodeDes) 
    {
        this.customsCodeDes = customsCodeDes;
    }

    public String getCustomsCodeDes() 
    {
        return customsCodeDes;
    }
    public void setTradeDate(String tradeDate) 
    {
        this.tradeDate = tradeDate;
    }

    public String getTradeDate() 
    {
        return tradeDate;
    }
    public void setDataMonth(Long dataMonth) 
    {
        this.dataMonth = dataMonth;
    }

    public Long getDataMonth() 
    {
        return dataMonth;
    }
    public void setImporter(String importer) 
    {
        this.importer = importer;
    }

    public String getImporter() 
    {
        return importer;
    }
    public void setImporterCountry(String importerCountry) 
    {
        this.importerCountry = importerCountry;
    }

    public String getImporterCountry() 
    {
        return importerCountry;
    }
    public void setExporter(String exporter) 
    {
        this.exporter = exporter;
    }

    public String getExporter() 
    {
        return exporter;
    }
    public void setExporterCountry(String exporterCountry) 
    {
        this.exporterCountry = exporterCountry;
    }

    public String getExporterCountry() 
    {
        return exporterCountry;
    }
    public void setWeightUnit(String weightUnit) 
    {
        this.weightUnit = weightUnit;
    }

    public String getWeightUnit() 
    {
        return weightUnit;
    }
    public void setQuantityUnit(String quantityUnit) 
    {
        this.quantityUnit = quantityUnit;
    }

    public String getQuantityUnit() 
    {
        return quantityUnit;
    }
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }
    public void setWeightGross(Long weightGross) 
    {
        this.weightGross = weightGross;
    }

    public Long getWeightGross() 
    {
        return weightGross;
    }
    public void setWeight(Long weight) 
    {
        this.weight = weight;
    }

    public Long getWeight() 
    {
        return weight;
    }
    public void setTonne(Long tonne) 
    {
        this.tonne = tonne;
    }

    public Long getTonne() 
    {
        return tonne;
    }
    public void setAmount(Long amount) 
    {
        this.amount = amount;
    }

    public Long getAmount() 
    {
        return amount;
    }
    public void setPriceUnitWeight(Long priceUnitWeight) 
    {
        this.priceUnitWeight = priceUnitWeight;
    }

    public Long getPriceUnitWeight() 
    {
        return priceUnitWeight;
    }
    public void setPriceUnitQuantity(Long priceUnitQuantity) 
    {
        this.priceUnitQuantity = priceUnitQuantity;
    }

    public Long getPriceUnitQuantity() 
    {
        return priceUnitQuantity;
    }
    public void setAmountLocal(Long amountLocal)
    {
        this.amountLocal = amountLocal;
    }

    public Long getAmountLocal()
    {
        return amountLocal;
    }
    public void setAmountContract(Long amountContract) 
    {
        this.amountContract = amountContract;
    }

    public Long getAmountContract() 
    {
        return amountContract;
    }
    public void setCurrency(String currency) 
    {
        this.currency = currency;
    }

    public String getCurrency() 
    {
        return currency;
    }
    public void setDealMethod(String dealMethod) 
    {
        this.dealMethod = dealMethod;
    }

    public String getDealMethod() 
    {
        return dealMethod;
    }
    public void setProductNameDetail(String productNameDetail) 
    {
        this.productNameDetail = productNameDetail;
    }

    public String getProductNameDetail() 
    {
        return productNameDetail;
    }
    public void setProductModel(String productModel) 
    {
        this.productModel = productModel;
    }

    public String getProductModel() 
    {
        return productModel;
    }
    public void setPortLocal(String portLocal) 
    {
        this.portLocal = portLocal;
    }

    public String getPortLocal() 
    {
        return portLocal;
    }
    public void setPortAbroad(String portAbroad) 
    {
        this.portAbroad = portAbroad;
    }

    public String getPortAbroad() 
    {
        return portAbroad;
    }
    public void setTransportMethod(String transportMethod) 
    {
        this.transportMethod = transportMethod;
    }

    public String getTransportMethod() 
    {
        return transportMethod;
    }
    public void setTradeMethod(String tradeMethod) 
    {
        this.tradeMethod = tradeMethod;
    }

    public String getTradeMethod() 
    {
        return tradeMethod;
    }
    public void setCountryTransit(String countryTransit) 
    {
        this.countryTransit = countryTransit;
    }

    public String getCountryTransit() 
    {
        return countryTransit;
    }
    public void setBillLadingNo(String billLadingNo) 
    {
        this.billLadingNo = billLadingNo;
    }

    public String getBillLadingNo() 
    {
        return billLadingNo;
    }
    public void setProductDesLocal(String productDesLocal) 
    {
        this.productDesLocal = productDesLocal;
    }

    public String getProductDesLocal() 
    {
        return productDesLocal;
    }
    public void setProductNameDetailLocal(String productNameDetailLocal) 
    {
        this.productNameDetailLocal = productNameDetailLocal;
    }

    public String getProductNameDetailLocal() 
    {
        return productNameDetailLocal;
    }
    public void setProductModelLocal(String productModelLocal) 
    {
        this.productModelLocal = productModelLocal;
    }

    public String getProductModelLocal() 
    {
        return productModelLocal;
    }
    public void setImporterLocalLang(String importerLocalLang) 
    {
        this.importerLocalLang = importerLocalLang;
    }

    public String getImporterLocalLang() 
    {
        return importerLocalLang;
    }
    public void setDatabaseName(String databaseName)
    {
        this.databaseName = databaseName;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }
    public void setExporterLocalLang(String exporterLocalLang) 
    {
        this.exporterLocalLang = exporterLocalLang;
    }

    public String getExporterLocalLang() 
    {
        return exporterLocalLang;
    }
    public void setCustomsClearanceNo(String customsClearanceNo) 
    {
        this.customsClearanceNo = customsClearanceNo;
    }

    public String getCustomsClearanceNo() 
    {
        return customsClearanceNo;
    }
    public void setQuantityDeclared(Long quantityDeclared) 
    {
        this.quantityDeclared = quantityDeclared;
    }

    public Long getQuantityDeclared() 
    {
        return quantityDeclared;
    }
    public void setImportExport(String importExport) 
    {
        this.importExport = importExport;
    }

    public String getImportExport() 
    {
        return importExport;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customsCode", getCustomsCode())
            .append("customsCodeDes", getCustomsCodeDes())
            .append("tradeDate", getTradeDate())
            .append("dataMonth", getDataMonth())
            .append("importer", getImporter())
            .append("importerCountry", getImporterCountry())
            .append("exporter", getExporter())
            .append("exporterCountry", getExporterCountry())
            .append("weightUnit", getWeightUnit())
            .append("quantityUnit", getQuantityUnit())
            .append("quantity", getQuantity())
            .append("weightGross", getWeightGross())
            .append("weight", getWeight())
            .append("tonne", getTonne())
            .append("amount", getAmount())
            .append("priceUnitWeight", getPriceUnitWeight())
            .append("priceUnitQuantity", getPriceUnitQuantity())
            .append("amountLocal", getAmountLocal())
            .append("amountContract", getAmountContract())
            .append("currency", getCurrency())
            .append("dealMethod", getDealMethod())
            .append("productNameDetail", getProductNameDetail())
            .append("productModel", getProductModel())
            .append("portLocal", getPortLocal())
            .append("portAbroad", getPortAbroad())
            .append("transportMethod", getTransportMethod())
            .append("tradeMethod", getTradeMethod())
            .append("countryTransit", getCountryTransit())
            .append("billLadingNo", getBillLadingNo())
            .append("productDesLocal", getProductDesLocal())
            .append("productNameDetailLocal", getProductNameDetailLocal())
            .append("productModelLocal", getProductModelLocal())
            .append("importerLocalLang", getImporterLocalLang())
            .append("databaseName", getDatabaseName())
            .append("exporterLocalLang", getExporterLocalLang())
            .append("customsClearanceNo", getCustomsClearanceNo())
            .append("quantityDeclared", getQuantityDeclared())
            .append("importExport", getImportExport())
            .toString();
    }
}
