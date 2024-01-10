package com.wj.crm.controller;

import com.wj.common.core.controller.BaseController;
import com.wj.common.core.domain.AjaxResult;
import com.wj.common.core.domain.entity.SysUser;
import com.wj.crm.vo.OwnerVO;
import com.wj.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: devjd
 * @Date: 2022/1/13
 * @Description: 负责人控制器
 */
@RestController
@RequestMapping("/crm/owner")
public class OwnerController extends BaseController {
    @Autowired
    private ISysUserService userService;

    /**
     * 查询负责人列表
     */
    @GetMapping("/list")
    public AjaxResult list(){
        List<SysUser> list = userService.selectUserList(new SysUser());
        List<OwnerVO> map = list.stream().map(owner->new OwnerVO(owner.getUserName(), owner.getNickName())).collect(Collectors.toList());

        return AjaxResult.success(map);
    }
}
