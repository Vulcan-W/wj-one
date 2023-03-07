package com.wj.mes.qc.service;

import java.util.List;
import com.wj.mes.qc.domain.QcTemplate;

/**
 * 检测模板Service接口
 * 
 * @author yinjinlu
 * @date 2022-05-17
 */
public interface IQcTemplateService 
{
    /**
     * 查询检测模板
     * 
     * @param templateId 检测模板主键
     * @return 检测模板
     */
    public QcTemplate selectQcTemplateByTemplateId(Long templateId);

    /**
     * 查询检测模板列表
     * 
     * @param qcTemplate 检测模板
     * @return 检测模板集合
     */
    public List<QcTemplate> selectQcTemplateList(QcTemplate qcTemplate);

    /**
     * 检测模板编号是否唯一
     * @param qcTemplate
     * @return
     */
    public String checkTemplateCodeUnique(QcTemplate qcTemplate);

    /**
     * 新增检测模板
     * 
     * @param qcTemplate 检测模板
     * @return 结果
     */
    public int insertQcTemplate(QcTemplate qcTemplate);

    /**
     * 修改检测模板
     * 
     * @param qcTemplate 检测模板
     * @return 结果
     */
    public int updateQcTemplate(QcTemplate qcTemplate);

    /**
     * 批量删除检测模板
     * 
     * @param templateIds 需要删除的检测模板主键集合
     * @return 结果
     */
    public int deleteQcTemplateByTemplateIds(Long[] templateIds);

    /**
     * 删除检测模板信息
     * 
     * @param templateId 检测模板主键
     * @return 结果
     */
    public int deleteQcTemplateByTemplateId(Long templateId);
}
