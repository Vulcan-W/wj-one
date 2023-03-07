package com.wj.mes.qc.service.impl;

import java.util.List;

import com.wj.common.constant.UserConstants;
import com.wj.common.utils.DateUtils;
import com.wj.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wj.mes.qc.mapper.QcTemplateMapper;
import com.wj.mes.qc.domain.QcTemplate;
import com.wj.mes.qc.service.IQcTemplateService;

/**
 * 检测模板Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-17
 */
@Service
public class QcTemplateServiceImpl implements IQcTemplateService 
{
    @Autowired
    private QcTemplateMapper qcTemplateMapper;

    /**
     * 查询检测模板
     * 
     * @param templateId 检测模板主键
     * @return 检测模板
     */
    @Override
    public QcTemplate selectQcTemplateByTemplateId(Long templateId)
    {
        return qcTemplateMapper.selectQcTemplateByTemplateId(templateId);
    }

    /**
     * 查询检测模板列表
     * 
     * @param qcTemplate 检测模板
     * @return 检测模板
     */
    @Override
    public List<QcTemplate> selectQcTemplateList(QcTemplate qcTemplate)
    {
        return qcTemplateMapper.selectQcTemplateList(qcTemplate);
    }

    @Override
    public String checkTemplateCodeUnique(QcTemplate qcTemplate) {
        QcTemplate template = qcTemplateMapper.checkTemplateCodeUnique(qcTemplate);
        Long templateId = qcTemplate.getTemplateId()==null?-1L:qcTemplate.getTemplateId();
        if(StringUtils.isNotNull(template) && template.getTemplateId().longValue()!=templateId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增检测模板
     * 
     * @param qcTemplate 检测模板
     * @return 结果
     */
    @Override
    public int insertQcTemplate(QcTemplate qcTemplate)
    {
        qcTemplate.setCreateTime(DateUtils.getNowDate());
        return qcTemplateMapper.insertQcTemplate(qcTemplate);
    }

    /**
     * 修改检测模板
     * 
     * @param qcTemplate 检测模板
     * @return 结果
     */
    @Override
    public int updateQcTemplate(QcTemplate qcTemplate)
    {
        qcTemplate.setUpdateTime(DateUtils.getNowDate());
        return qcTemplateMapper.updateQcTemplate(qcTemplate);
    }

    /**
     * 批量删除检测模板
     * 
     * @param templateIds 需要删除的检测模板主键
     * @return 结果
     */
    @Override
    public int deleteQcTemplateByTemplateIds(Long[] templateIds)
    {
        return qcTemplateMapper.deleteQcTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除检测模板信息
     * 
     * @param templateId 检测模板主键
     * @return 结果
     */
    @Override
    public int deleteQcTemplateByTemplateId(Long templateId)
    {
        return qcTemplateMapper.deleteQcTemplateByTemplateId(templateId);
    }
}
