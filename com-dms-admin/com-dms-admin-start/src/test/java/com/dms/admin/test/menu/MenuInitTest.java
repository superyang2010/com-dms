package com.dms.admin.test.menu;

import com.alibaba.fastjson.JSON;
import com.dms.admin.repo.jpa.dao.ISysMenuDao;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.dms.admin.service.IMenuService;
import com.dms.admin.test.BaseTest;
import com.dms.pub.enums.MenuTypeEnum;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.util.DateUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/5/18 15:57
 */
@Data
@Slf4j
public class MenuInitTest extends BaseTest {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private ISysMenuDao menuDao;

    @Test
    @Rollback(false)
    public void saveMenu() {
        try {
            SysMenu menu = new SysMenu();
            menu.setCode("codecode");
            menu.setGmtCreated(DateUtil.getNow());
            menu.setIcon("icon");
            menu.setName("namename");
            menu.setUrl("");
            menu.setMenuType(MenuTypeEnum.M);
            menu.setStatus(StatusEnum.Y);
            log.info(JSON.toJSONString(menu));
            menu = menuDao.save(menu);
            log.info(JSON.toJSONString(menu));
            //List<SysMenu> menus = menuService.save(Lists.newArrayList(menu));
            //log.info(JSON.toJSONString(menus));
        } catch (Exception e) {
            log.error("test exception", e);
        }

    }
}
