package com.dms.admin.controller;

import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.LoginParam;
import com.dms.admin.repo.jpa.model.SysRole;
import com.dms.admin.repo.jpa.model.SysUser;
import com.dms.admin.service.IRoleService;
import com.dms.admin.service.IUserService;
import com.dms.pub.base.BaseController;
import com.dms.pub.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 登录
 * @author yangchao.
 * @date 2018/11/24 10:43
 */
@RestController
@CrossOrigin
@RequestMapping("/dms-admin/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @PostMapping(value = "/add")
    public Result<RoleDTO> add(@RequestBody SysRole roleParam) {
        RoleDTO role = roleService.create(roleParam);
        return Result.success("角色添加成功", role);
    }

    @DeleteMapping(value = "/delete")
    public Result<SysRole> delete(@RequestParam SysRole role) {
        roleService.remove(role);
        return Result.success("角色删除成功", role);
    }

}
