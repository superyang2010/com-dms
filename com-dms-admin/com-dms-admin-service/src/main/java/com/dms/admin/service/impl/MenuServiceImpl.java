package com.dms.admin.service.impl;

import com.dms.admin.base.BaseService;
import com.dms.admin.domain.dto.MenuDTO;
import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.domain.param.MenuParam;
import com.dms.admin.repo.jpa.dao.ISysMenuDao;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.dms.admin.repo.jpa.model.SysRole;
import com.dms.admin.repo.jpa.model.SysRoleMenuRela;
import com.dms.admin.repo.jpa.model.SysUserRoleRela;
import com.dms.admin.service.IMenuService;
import com.dms.pub.enums.MenuTypeEnum;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.DateUtil;
import com.dms.pub.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Set;

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
        menu.setGmtCreated(null);
        Example<SysMenu> param = Example.of(menu);
        Pageable pageable = this.buildPageParam(menuParam);
        Page<MenuDTO> menus = this.menuDao.findAll(param, pageable).map(u -> {
            MenuDTO menuDTO = ObjectUtil.shallowCopy(u, MenuDTO.class);
            menuDTO.setMenuType(u.getMenuType().getValue());
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
        menu.setStatus(StatusEnum.INVALID);
        menu.setGmtModified(DateUtil.getNow());
        menu.getRoleMenuRelas().forEach(rela -> {
            rela.setGmtModified(DateUtil.getNow());
            rela.setStatus(StatusEnum.INVALID);
        });
        menuDao.save(menu);
    }
}
