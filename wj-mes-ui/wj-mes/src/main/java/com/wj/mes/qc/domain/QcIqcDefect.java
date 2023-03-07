package com.wj.mes.qc.domain;

import com.wj.common.annotation.Excel;
import com.wj.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 来料检验单缺陷记录对象 qc_iqc_defect
 * 
 * @author yinjinlu
 * @date 2022-05-19
 */
public class QcIqcDefect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 缺陷ID */
    private Long recordId;

    /** 来料检验单ID */
    @Excel(name = "来料检验单ID")
    private Long iqcId;

    /** 来料检验单行ID */
    @Excel(name = "来料检验单行ID")
    private Long lineId;

    /** 缺陷描述 */
    @NotBlank(message = "缺陷描述不能为空")
    @Excel(name = "缺陷描述")
    private String defectName;

    /** 缺陷等级 */
    @NotBlank(message = "请选择缺陷等级")
    @Excel(name = "缺陷等级")
    private String defectLevel;

    /** 缺陷数量 */
    @Excel(name = "缺陷数量")
    private Long defectQuantity;

    private String flag;

    /** 预留字段1 */
    private String attr1;

    /** 预留字段2 */
    private String attr2;

    /** 预留字段3 */
    private Long attr3;

    /** 预留字段4 */
    private Long attr4;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setIqcId(Long iqcId) 
    {
        this.iqcId = iqcId;
    }

    public Long getIqcId() 
    {
        return iqcId;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setDefectName(String defectName) 
    {
        this.defectName = defectName;
    }

    public String getDefectName() 
    {
        return defectName;
    }
    public void setDefectLevel(String defectLevel) 
    {
        this.defectLevel = defectLevel;
    }

    public String getDefectLevel() 
    {
        return defectLevel;
    }
    public void setDefectQuantity(Long defectQuantity) 
    {
        this.defectQuantity = defectQuantity;
    }

    public Long getDefectQuantity() 
    {
        return defectQuantity;
    }
    public void setAttr1(String attr1) 
    {
        this.attr1 = attr1;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAttr1()
    {
        return attr1;
    }
    public void setAttr2(String attr2) 
    {
        this.attr2 = attr2;
    }

    public String getAttr2() 
    {
        return attr2;
    }
    public void setAttr3(Long attr3) 
    {
        this.attr3 = attr3;
    }

    public Long getAttr3() 
    {
        return attr3;
    }
    public void setAttr4(Long attr4) 
    {
        this.attr4 = attr4;
    }

    public Long getAttr4() 
    {
        return attr4;
    }

    @Override
    public String toString() {
        return "QcIqcDefect{" +
                "recordId=" + recordId +
                ", iqcId=" + iqcId +
                ", lineId=" + lineId +
                ", defectName='" + defectName + '\'' +
                ", defectLevel='" + defectLevel + '\'' +
                ", defectQuantity=" + defectQuantity +
                ", flag='" + flag + '\'' +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3=" + attr3 +
                ", attr4=" + attr4 +
                '}';
    }
}
