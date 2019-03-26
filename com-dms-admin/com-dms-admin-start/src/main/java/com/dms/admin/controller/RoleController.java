package com.dms.admin.controller;

import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.domain.param.RoleParam;
import com.dms.admin.service.IRoleService;
import com.dms.pub.base.BaseController;
import com.dms.pub.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(value = "/list")
    public Result<List<RoleDTO>> list(@RequestBody RoleParam roleParam) {
        Page<RoleDTO> roles = roleService.query(roleParam);
        return Result.success("角色列表查询成功", roles);
    }

    @PostMapping(value = "/add")
    public Result<RoleDTO> add(@RequestBody RoleParam roleParam) {
        RoleDTO role = roleService.create(roleParam);
        return Result.success("角色添加成功", role);
    }

    @PostMapping(value = "/modify")
    public Result<RoleDTO> modify(@RequestBody RoleParam roleParam) {
        RoleDTO role = roleService.modify(roleParam);
        return Result.success("角色修改成功", role);
    }

    @DeleteMapping(value = "/delete")
    public Result<RoleDTO> delete(@RequestBody RoleParam roleParam) {
        roleService.remove(roleParam);
        return Result.success("角色删除成功");
    }

}
