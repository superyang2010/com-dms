package com.dms.admin.controller;

import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.LoginParam;
import com.dms.admin.domain.param.UserParam;
import com.dms.admin.repo.jpa.model.SysUser;
import com.dms.admin.service.IUserService;
import com.dms.pub.base.BaseController;
import com.dms.pub.common.PageParam;
import com.dms.pub.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
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

    @GetMapping(value = "/current")
    public Result<UserDTO> currentUser(HttpSession session) {
        UserDTO userDTO = (UserDTO)session.getAttribute("LOGIN_USER_INFO");
        if (userDTO == null) {
            return Result.fail("SYS-USER-0001","登录已失效，请重新登录");
        }
        return Result.success("", userDTO);
    }

    @GetMapping(value = "/list")
    public Result<List<UserDTO>> list(@RequestParam UserParam userParam) {
        Page<UserDTO> users = userService.query(userParam);
        return Result.success("用户列表查询成功", users);
    }

    @PostMapping(value = "/add")
    public Result<UserDTO> add(@RequestBody UserParam userParam) {
        UserDTO user = userService.create(userParam);
        return Result.success("用户添加成功", user);
    }

    @PostMapping(value = "/modify")
    public Result<UserDTO> modify(@RequestBody UserParam userParam) {
        UserDTO user = userService.modify(userParam);
        return Result.success("用户修改成功", user);
    }

    @DeleteMapping(value = "/delete")
    public Result<UserDTO> delete(@RequestParam Long userId) {
        userService.remove(userId);
        return Result.success("用户删除成功");
    }

}
