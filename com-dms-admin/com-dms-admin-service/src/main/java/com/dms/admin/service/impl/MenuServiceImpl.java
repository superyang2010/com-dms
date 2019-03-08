package com.dms.admin.service.impl;

import com.dms.admin.base.BaseService;
import com.dms.admin.domain.dto.MenuDTO;
import com.dms.admin.domain.param.MenuParam;
import com.dms.admin.repo.jpa.dao.ISysMenuDao;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.dms.admin.repo.jpa.model.SysRoleMenuRela;
import com.dms.admin.service.IMenuService;
import com.dms.pub.enums.MenuTypeEnum;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.DateUtil;
import com.dms.pub.util.ObjectUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yangchao.
 * @date 2019/2/23 16:31
 */
@Service
@Slf4j
public class MenuServiceImpl extends BaseService implements IMenuService {

    @Autowired
    private ISysMenuDao menuDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<MenuDTO> query(MenuParam menuParam) {
        SysMenu menu = new SysMenu();
        menu.setCode(menuParam.getCode());
        menu.setName(menuParam.getName());
        StatusEnum status = menuParam.getStatus() != null ? StatusEnum.getByValue(menuParam.getStatus()) : null;
        menu.setStatus(status);
        MenuTypeEnum menuType = menuParam.getMenuType() != null ? MenuTypeEnum.getByValue(menuParam.getMenuType()) : null;
        menu.setMenuType(menuType);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains())
                .withIgnorePaths("gmtCreated");
        Example<SysMenu> param = Example.of(menu, matcher);
        Pageable pageable = this.buildPageParam(menuParam);
        Page<MenuDTO> menus = this.menuDao.findAll(param, pageable).map(u -> {
            MenuDTO menuDTO = ObjectUtil.shallowCopy(u, MenuDTO.class);
            return menuDTO;
        });
        return menus;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public MenuDTO create(MenuParam menuParam) {
        Long parentId = menuParam.getParentId();
        SysMenu parentMenu = null;
        if (parentId != null) {
            parentMenu = this.menuDao.findOne(parentId);
            if (parentMenu == null) {
                ExceptionHandler.publish("DMS-ADMIN-MENU-0001", "非法的父级节点参数");
            }
        }
        SysMenu menu = new SysMenu();
        menu.setCode(menuParam.getCode());
        menu.setName(menuParam.getName());
        menu.setIcon(menuParam.getIcon());
        menu.setUrl(menuParam.getUrl());
        MenuTypeEnum menuType = menuParam.getMenuType() != null ? MenuTypeEnum.getByValue(menuParam.getMenuType()) : null;
        menu.setMenuType(menuType);
        menu.setNotes(menuParam.getNotes());
        menu.setParentMenu(parentMenu);
        SysMenu menu1 = menuDao.save(menu);
        return ObjectUtil.shallowCopy(menu1, MenuDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public MenuDTO modify(MenuParam menuParam) {
        Long parentId = menuParam.getParentId();
        SysMenu parentMenu = null;
        if (parentId != null) {
            parentMenu = this.menuDao.findOne(parentId);
            if (parentMenu == null) {
                ExceptionHandler.publish("DMS-ADMIN-MENU-0001", "非法的父级节点参数");
            }
        }
        SysMenu menu = menuDao.findOne(menuParam.getId());
        if (menu == null) {
            ExceptionHandler.publish("DMS-ADMIN-MENU-0002", "非法参数");
        }
        menu.setName(menuParam.getName());
        menu.setIcon(menuParam.getIcon());
        menu.setUrl(menuParam.getUrl());
        MenuTypeEnum menuType = menuParam.getMenuType() != null ? MenuTypeEnum.getByValue(menuParam.getMenuType()) : null;
        menu.setMenuType(menuType);
        menu.setNotes(menuParam.getNotes());
        menu.setParentMenu(parentMenu);
        SysMenu menu1 = menuDao.save(menu);
        return ObjectUtil.shallowCopy(menu1, MenuDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public void remove(Long menuId) {
        SysMenu menu = menuDao.findOne(menuId);
        if (menu == null) {
            ExceptionHandler.publish("DMS-ADMIN-MENU-0002", "非法参数");
        }
        Set<SysRoleMenuRela> roleMenuRelas = menu.getRoleMenuRelas();
        if (!CollectionUtils.isEmpty(roleMenuRelas)) {
            ExceptionHandler.publish("DMS-ADMIN-MENU-0003", "请先解绑已经赋权的角色");
        }
        menu.setStatus(StatusEnum.N);
        menu.setGmtModified(DateUtil.getNow());
        menu.getRoleMenuRelas().forEach(rela -> {
            rela.setGmtModified(DateUtil.getNow());
            rela.setStatus(StatusEnum.N);
        });
        menuDao.save(menu);
    }

    @Override
    public List<MenuDTO> queryByParentId(Long parentId) {
        List<SysMenu> sysMenus = this.menuDao.findByParentId(parentId);
        List<MenuDTO> menus = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(sysMenus)) {
            menus = sysMenus.stream().map(menu -> buildMenuDTO(menu)).collect(Collectors.toList());
        }
        return menus;
    }

    private MenuDTO buildMenuDTO(SysMenu sysMenu) {
        MenuDTO menu = ObjectUtil.shallowCopy(sysMenu, MenuDTO.class);
        Set<SysMenu> children = sysMenu.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            children.forEach(m -> {
                MenuDTO child = buildMenuDTO(m);
                menu.addChild(child);
            });
        }
        return menu;
    }
}
