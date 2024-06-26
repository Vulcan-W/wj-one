package com.wj.mes.pro.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wj.common.annotation.Excel;
import com.wj.common.core.domain.BaseEntity;

/**
 * 工艺组成对象 pro_route_process
 * 
 * @author yinjinlu
 * @date 2022-05-13
 */
public class ProRouteProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 工艺路线ID */
    @Excel(name = "工艺路线ID")
    private Long routeId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 序号 */
    @Excel(name = "序号")
    private Integer orderNum;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long nextProcessId;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String nextProcessCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String nextProcessName;

    /** 与下一道工序关系 */
    @Excel(name = "与下一道工序关系")
    private String linkType;

    /** 准备时间 */
    @Excel(name = "准备时间")
    private Long defaultPreTime;

    /** 等待时间 */
    @Excel(name = "等待时间")
    private Long defaultSufTime;

    /** 甘特图显示颜色 */
    @Excel(name = "甘特图显示颜色")
    private String colorCode;

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
    public void setRouteId(Long routeId) 
    {
        this.routeId = routeId;
    }

    public Long getRouteId() 
    {
        return routeId;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setProcessCode(String processCode) 
    {
        this.processCode = processCode;
    }

    public String getProcessCode() 
    {
        return processCode;
    }
    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }
    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }
    public void setNextProcessId(Long nextProcessId) 
    {
        this.nextProcessId = nextProcessId;
    }

    public Long getNextProcessId() 
    {
        return nextProcessId;
    }
    public void setNextProcessCode(String nextProcessCode) 
    {
        this.nextProcessCode = nextProcessCode;
    }

    public String getNextProcessCode() 
    {
        return nextProcessCode;
    }
    public void setNextProcessName(String nextProcessName) 
    {
        this.nextProcessName = nextProcessName;
    }

    public String getNextProcessName() 
    {
        return nextProcessName;
    }
    public void setLinkType(String linkType) 
    {
        this.linkType = linkType;
    }

    public String getLinkType() 
    {
        return linkType;
    }
    public void setDefaultPreTime(Long defaultPreTime) 
    {
        this.defaultPreTime = defaultPreTime;
    }

    public Long getDefaultPreTime() 
    {
        return defaultPreTime;
    }
    public void setDefaultSufTime(Long defaultSufTime) 
    {
        this.defaultSufTime = defaultSufTime;
    }

    public Long getDefaultSufTime() 
    {
        return defaultSufTime;
    }
    public void setColorCode(String colorCode) 
    {
        this.colorCode = colorCode;
    }

    public String getColorCode() 
    {
        return colorCode;
    }
    public void setAttr1(String attr1) 
    {
        this.attr1 = attr1;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("routeId", getRouteId())
            .append("processId", getProcessId())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("orderNum", getOrderNum())
            .append("nextProcessId", getNextProcessId())
            .append("nextProcessCode", getNextProcessCode())
            .append("nextProcessName", getNextProcessName())
            .append("linkType", getLinkType())
            .append("defaultPreTime", getDefaultPreTime())
            .append("defaultSufTime", getDefaultSufTime())
            .append("colorCode", getColorCode())
            .append("remark", getRemark())
            .append("attr1", getAttr1())
            .append("attr2", getAttr2())
            .append("attr3", getAttr3())
            .append("attr4", getAttr4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
