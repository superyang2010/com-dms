package com.dms.admin.controller;

import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.repo.jpa.model.SysUser;
import com.dms.admin.service.IUserService;
import com.dms.pub.base.BaseController;
import com.dms.pub.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 登录
 * @author yangchao.
 * @date 2018/11/23 10:43
 */
@RestController
@CrossOrigin
@RequestMapping("/dms-admin/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/add")
    public Result<UserDTO> add(@RequestBody SysUser userParam) {
        UserDTO user = userService.create(userParam);
        return Result.success("用户添加成功", user);
    }

    @PostMapping(value = "/modify")
    public Result<UserDTO> modify(@RequestBody SysUser userParam, @RequestParam Set<Long> roleIds) {
        UserDTO user = userService.modify(userParam, roleIds);
        return Result.success("用户修改成功", user);
    }

    @DeleteMapping(value = "/delete")
    public Result<UserDTO> delete(@RequestParam Long userId) {
        userService.remove(userId);
        return Result.success("用户删除成功", null);
    }

}
