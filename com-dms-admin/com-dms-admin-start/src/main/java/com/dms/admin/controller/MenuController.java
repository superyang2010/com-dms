package com.dms.admin.controller;

import com.dms.admin.domain.dto.MenuDTO;
import com.dms.admin.domain.param.MenuParam;
import com.dms.admin.service.IMenuService;
import com.dms.pub.base.BaseController;
import com.dms.pub.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/3/8 00:53
 */
@RestController
@CrossOrigin
@RequestMapping("/dms-admin/menu")
public class MenuController extends BaseController {
    
    @Autowired
    private IMenuService menuService;

    @PostMapping(value = "/list")
    public Result<List<MenuDTO>> list(@RequestBody MenuParam menuParam) {
        Page<MenuDTO> menus = menuService.query(menuParam);
        return Result.success("菜单列表查询成功", menus);
    }

    @PostMapping(value = "/add")
    public Result<MenuDTO> add(@RequestBody MenuParam menuParam) {
        MenuDTO menu = menuService.create(menuParam);
        return Result.success("菜单添加成功", menu);
    }

    @PostMapping(value = "/modify")
    public Result<MenuDTO> modify(@RequestBody MenuParam menuParam) {
        MenuDTO menu = menuService.modify(menuParam);
        return Result.success("菜单修改成功", menu);
    }

    @DeleteMapping(value = "/delete")
    public Result<MenuDTO> delete(@RequestParam Long menuId) {
        menuService.remove(menuId);
        return Result.success("菜单删除成功");
    }

    @GetMapping(value = "/query")
    public Result<List<MenuDTO>> query(@RequestParam Long menuId) {
        List<MenuDTO> menus = menuService.queryByParentId(menuId);
        return Result.success("按层级查询菜单成功", menus);
    }
}
