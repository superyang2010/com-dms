package com.dms.admin.service.impl;

import com.dms.admin.domain.dto.MenuDTO;
import com.dms.admin.domain.dto.MenuFuncDTO;
import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.LoginParam;
import com.dms.admin.repo.jpa.dao.ISysMenuDao;
import com.dms.admin.repo.jpa.dao.ISysMenuFuncDao;
import com.dms.admin.repo.jpa.dao.ISysRoleDao;
import com.dms.admin.repo.jpa.dao.ISysUserDao;
import com.dms.admin.repo.jpa.model.*;
import com.dms.admin.service.ILoginService;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.ObjectUtil;
import com.dms.pub.util.SecurityUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.security.tools.jarsigner.TimestampedSigner;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统登录相关服务
 * @author yangchao.
 * @date 2018/11/23 11:55
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private ISysUserDao userDao;
    @Autowired
    private ISysMenuFuncDao menuFuncDao;

    @Transactional(transactionManager = "transactionManager", readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserDTO login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        log.info("{} 开始登录...", username);
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setStatus(StatusEnum.VALID);
        user.setGmtCreated(null);
        Example<SysUser> query = Example.of(user);
        List<SysUser> users = this.userDao.findAll(query);
        if (CollectionUtils.isEmpty(users)) {
            ExceptionHandler.publish("DMS-LOGIN-00006", "用户名或密码错误");
        }
        user = users.get(0);
        String dbPwd = user.getUserpwd();
        String securityPwd = SecurityUtil.md5Encrypt(username + "/dms/" + password);
        if (StringUtils.isEmpty(dbPwd) || !dbPwd.equals(securityPwd)) {
            ExceptionHandler.publish("DMS-LOGIN-00006", "用户名或密码错误");
        }
        log.info("{} 登录...ok", username);
        // 回填用户权限信息
        UserDTO userDTO = this.buildUserPermission(user);
        return userDTO;
    }

    /**
     * 用户权限信息回填
     * @param user
     * @return
     */
    private UserDTO buildUserPermission(SysUser user) {
        UserDTO userDTO = ObjectUtil.shallowCopy(user, UserDTO.class);
        // 用户拥有的所有菜单功能点
        Map<Long, List<MenuFuncDTO>> menuFuncMap = user.getUserMenuFuncRelas().stream().map(userMenuFuncRela -> {
            SysMenuFunction menuFunction = userMenuFuncRela.getMenuFunction();
            MenuFuncDTO funcDTO = ObjectUtil.shallowCopy(menuFunction, MenuFuncDTO.class);
            funcDTO.setMenuId(menuFunction.getMenu().getId());
            return funcDTO;
        }).collect(Collectors.groupingBy(MenuFuncDTO::getMenuId, Collectors.toList()));
        // 通过懒加载获取用户拥有的角色
        Set<RoleDTO> roles = user.getUserRoleRelas().stream().map(userRoleRela -> {
            SysRole role = userRoleRela.getRole();
            RoleDTO roleDTO = ObjectUtil.shallowCopy(role, RoleDTO.class);
            // 角色对应的所有菜单
            List<MenuDTO> menus = role.getRoleMenuRelas().stream().map(roleMenuRela -> {
                SysMenu menu = roleMenuRela.getMenu();
                SysMenu parentMenu = menu.getParentMenu();
                Long parentId = parentMenu != null ? parentMenu.getId() : null;
                menu.setChildren(null);
                MenuDTO menuDTO = ObjectUtil.shallowCopy(menu, MenuDTO.class);
                menuDTO.setParentId(parentId);
                // 用户拥有的菜单功能点权限
                List<MenuFuncDTO> userMenuFuncs = menuFuncMap.get(menu.getId());
                if (!CollectionUtils.isEmpty(userMenuFuncs)) {
                    userMenuFuncs.sort((func1, func2) -> (int)(func1.getId() - func2.getId()));
                    menuDTO.setMenuFuncs(userMenuFuncs);
                }
                return menuDTO;
            }).collect(Collectors.toList());
            // 父菜单
            List<MenuDTO> parentMenus = menus.stream().filter(menu -> menu.getParentId() == null
                    && menu.getStatus().getValue().intValue() == StatusEnum.VALID.getValue())
                    .sorted((menu1, menu2) -> (int)(menu1.getId() - menu2.getId()))
                    .collect(Collectors.toList());
            // 所有子菜单
            List<MenuDTO> childrenMenus = menus.stream().filter(menu -> menu.getParentId() != null
                    && menu.getStatus().getValue().intValue() == StatusEnum.VALID.getValue())
                    .sorted((menu1, menu2) -> (int)(menu1.getId() - menu2.getId()))
                    .collect(Collectors.toList());
            menus = this.buildChildren(parentMenus, childrenMenus);
            roleDTO.setMenus(menus);
            return roleDTO;
        }).filter(role -> role.getStatus().getValue().intValue() == StatusEnum.VALID.getValue()).collect(Collectors.toSet());
        userDTO.setRoles(roles);
        return userDTO;
    }

    /**
     * 递归组装菜单的树状结构
     * @param parentMenus
     * @param menus
     * @return
     */
    private List<MenuDTO> buildChildren(List<MenuDTO> parentMenus, List<MenuDTO> menus) {
        List<MenuDTO> newChildren = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(parentMenus)) {
            parentMenus.forEach(parent -> {
                long id = parent.getId();
                if (!CollectionUtils.isEmpty(menus)) {
                    menus.forEach(child -> {
                        long parentId = child.getParentId();
                        if (id == parentId) {
                            parent.addChildMenu(child);
                        } else {
                            newChildren.add(child);
                        }
                    });
                }
                if (!CollectionUtils.isEmpty(newChildren)) {
                    buildChildren(parent.getChildren(), newChildren);
                }
            });
        }
        return parentMenus;
    }
}
