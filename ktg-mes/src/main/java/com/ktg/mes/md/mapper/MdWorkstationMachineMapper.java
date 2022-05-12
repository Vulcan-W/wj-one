package com.ktg.mes.md.mapper;

import java.util.List;
import com.ktg.mes.md.domain.MdWorkstationMachine;

/**
 * 设备资源Mapper接口
 * 
 * @author yinjinlu
 * @date 2022-05-12
 */
public interface MdWorkstationMachineMapper 
{
    /**
     * 查询设备资源
     * 
     * @param recordId 设备资源主键
     * @return 设备资源
     */
    public MdWorkstationMachine selectMdWorkstationMachineByRecordId(Long recordId);

    /**
     * 查询设备资源列表
     * 
     * @param mdWorkstationMachine 设备资源
     * @return 设备资源集合
     */
    public List<MdWorkstationMachine> selectMdWorkstationMachineList(MdWorkstationMachine mdWorkstationMachine);

    public  MdWorkstationMachine checkMachineryExists(MdWorkstationMachine mdWorkstationMachine);

    /**
     * 新增设备资源
     * 
     * @param mdWorkstationMachine 设备资源
     * @return 结果
     */
    public int insertMdWorkstationMachine(MdWorkstationMachine mdWorkstationMachine);

    /**
     * 修改设备资源
     * 
     * @param mdWorkstationMachine 设备资源
     * @return 结果
     */
    public int updateMdWorkstationMachine(MdWorkstationMachine mdWorkstationMachine);

    /**
     * 删除设备资源
     * 
     * @param recordId 设备资源主键
     * @return 结果
     */
    public int deleteMdWorkstationMachineByRecordId(Long recordId);

    /**
     * 批量删除设备资源
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMdWorkstationMachineByRecordIds(Long[] recordIds);
}
