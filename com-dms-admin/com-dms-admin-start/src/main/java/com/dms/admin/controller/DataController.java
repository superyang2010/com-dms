package com.dms.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dms.admin.domain.dto.MenuDTO;
import com.dms.admin.domain.param.MenuParam;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.dms.admin.service.IMenuService;
import com.dms.pub.base.BaseController;
import com.dms.pub.common.Result;
import com.dms.pub.enums.MenuTypeEnum;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/3/8 00:53
 */
@RestController
@CrossOrigin
@RequestMapping("/dms-admin/data")
public class DataController extends BaseController {
    
    @Autowired
    private IMenuService menuService;

    @PostMapping(value = "/save")
    public Result<List<SysMenu>> list(@RequestBody String originMenuData) {
        JSONArray menus = JSON.parseObject(originMenuData).getJSONArray("routes");
        List<SysMenu> menuList = Lists.newArrayList();
        SysMenu parentMenu = new SysMenu();
        parentMenu.setId(11L);
        for (int i = 0; i < menus.size(); i++) {
            JSONObject menuObj = menus.getJSONObject(i);
            SysMenu menu = this.parseMenu(menuObj, parentMenu);
            menuList.add(menu);
        }
        menuService.save(menuList);
        return Result.success("菜单列表查询成功", menuList);
    }

    private SysMenu parseMenu(JSONObject menuObj, SysMenu parentMenu) {
        SysMenu menu = new SysMenu();
        String name = menuObj.getString("name");
        String code = name;
        menu.setCode(code);
        menu.setGmtCreated(DateUtil.getNow());
        menu.setIcon(menuObj.getString("icon"));
        menu.setName(name);
        menu.setUrl(menuObj.getString("path"));
        menu.setParentMenu(parentMenu);
        menu.setMenuType(MenuTypeEnum.M);
        menu.setStatus(StatusEnum.Y);
        List<SysMenu> children = Lists.newArrayList();
        if (menuObj.get("routes") != null) {
            JSONArray menus = menuObj.getJSONArray("routes");
            for (int i = 0; i < menus.size(); i++) {
                JSONObject obj = menus.getJSONObject(i);
                SysMenu menu1 = parseMenu(obj, menu);
                children.add(menu1);
            }
        }
        menu.setChildren(Sets.newHashSet(children));
        return menu;
    }

}
