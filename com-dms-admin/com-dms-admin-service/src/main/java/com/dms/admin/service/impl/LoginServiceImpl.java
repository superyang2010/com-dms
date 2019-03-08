package com.dms.admin.service.impl;

import com.dms.admin.domain.dto.MenuDTO;
import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.LoginParam;
import com.dms.admin.repo.jpa.dao.ISysUserDao;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.dms.admin.repo.jpa.model.SysRole;
import com.dms.admin.repo.jpa.model.SysUser;
import com.dms.admin.service.ILoginService;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.ObjectUtil;
import com.dms.pub.util.SecurityUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
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

    @Transactional(transactionManager = "transactionManager", readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserDTO login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        log.info("{} 开始登录...", username);
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setStatus(StatusEnum.Y);
        user.setGmtCreated(null);
        Example<SysUser> query = Example.of(user);
        List<SysUser> users = this.userDao.findAll(query);
        if (CollectionUtils.isEmpty(users)) {
            ExceptionHandler.publish("SYS-LOGIN-00006", "账户或密码错误");
        }
        user = users.get(0);
        String dbPwd = user.getUserpwd();
        String securityPwd = SecurityUtil.md5Encrypt(username + "/dms/" + password);
        if (StringUtils.isEmpty(dbPwd) || !dbPwd.equals(securityPwd)) {
            ExceptionHandler.publish("SYS-LOGIN-00006", "账户或密码错误");
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
        // 通过懒加载获取用户拥有的角色
        List<RoleDTO> roles = user.getUserRoleRelas().stream()
                .filter(role -> role.getStatus().getValue().intValue() == StatusEnum.Y.getValue())
                .map(userRoleRela -> {
                    SysRole role = userRoleRela.getRole();
                    RoleDTO roleDTO = ObjectUtil.shallowCopy(role, RoleDTO.class);
                    // 角色对应的所有菜单
                    List<MenuDTO> menus = role.getRoleMenuRelas().stream()
                            .filter(roleMenuRela -> roleMenuRela.getMenu().getStatus().getValue().intValue() == StatusEnum.Y.getValue())
                            .map(roleMenuRela -> {
                                SysMenu menu = roleMenuRela.getMenu();
                                SysMenu parentMenu = menu.getParentMenu();
                                Long parentId = parentMenu != null ? parentMenu.getId() : null;
                                menu.setChildren(null);
                                MenuDTO menuDTO = ObjectUtil.shallowCopy(menu, MenuDTO.class);
                                menuDTO.setParentId(parentId);
                                return menuDTO;
                            }).collect(Collectors.toList());
                    // 父菜单
                    List<MenuDTO> parentMenus = menus.stream().filter(menu -> menu.getParentId() == null)
                            .sorted((menu1, menu2) -> (int)(menu1.getId() - menu2.getId()))
                            .collect(Collectors.toList());
                    // 所有子菜单
                    List<MenuDTO> childrenMenus = menus.stream().filter(menu -> menu.getParentId() != null)
                            .sorted((menu1, menu2) -> (int)(menu1.getId() - menu2.getId()))
                            .collect(Collectors.toList());
                    menus = this.buildChildren(parentMenus, childrenMenus);
                    roleDTO.setMenus(menus);
                    return roleDTO;
                }).collect(Collectors.toList());
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
                            parent.addChild(child);
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
