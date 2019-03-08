package com.dms.admin.service.impl;

import com.dms.admin.base.BaseService;
import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.domain.param.RoleParam;
import com.dms.admin.repo.jpa.dao.ISysRoleDao;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.dms.admin.repo.jpa.model.SysRole;
import com.dms.admin.repo.jpa.model.SysUserRoleRela;
import com.dms.admin.service.IRoleService;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.DateUtil;
import com.dms.pub.util.ObjectUtil;
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

import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/2/23 16:31
 */
@Service
@Slf4j
public class RoleServiceImpl extends BaseService implements IRoleService {

    @Autowired
    private ISysRoleDao roleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<RoleDTO> query(RoleParam roleParam) {
        SysRole role = new SysRole();
        role.setCode(roleParam.getCode());
        role.setName(roleParam.getName());
        StatusEnum status = roleParam.getStatus() != null ? StatusEnum.getByValue(roleParam.getStatus()) : null;
        role.setStatus(status);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("name", match -> match.contains())
                .withIgnorePaths("gmtCreated");
        Example<SysRole> param = Example.of(role, matcher);
        Pageable pageable = this.buildPageParam(roleParam);
        Page<RoleDTO> roles = this.roleDao.findAll(param, pageable).map(u -> ObjectUtil.shallowCopy(u, RoleDTO.class));
        return roles;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public RoleDTO create(RoleParam roleParam) {
        Set<Long> menuIds = roleParam.getMenuIds();
        SysRole role = new SysRole();
        role.setCode(roleParam.getCode());
        role.setName(roleParam.getName());
        role.setNotes(roleParam.getNotes());
        menuIds.stream().forEach(menuId -> {
            SysMenu menu = new SysMenu();
            role.setId(menuId);
            role.addMenu(menu);
        });
        SysRole role1 = roleDao.save(role);
        return ObjectUtil.shallowCopy(role1, RoleDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public RoleDTO modify(RoleParam roleParam) {
        Set<Long> menuIds = roleParam.getMenuIds();
        SysRole role = roleDao.findOne(roleParam.getId());
        if (role == null) {
            ExceptionHandler.publish("DMS-ADMIN-ROLE-0001", "非法参数");
        }
        role.setName(roleParam.getName());
        role.setNotes(roleParam.getNotes());
        menuIds.stream().forEach(menuId -> {
            SysMenu menu = new SysMenu();
            role.setId(menuId);
            role.addMenu(menu);
        });
        SysRole role1 = roleDao.save(role);
        return ObjectUtil.shallowCopy(role1, RoleDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public void remove(Long roleId) {
        SysRole role = roleDao.findOne(roleId);
        if (role == null) {
            ExceptionHandler.publish("DMS-ADMIN-ROLE-0001", "非法参数");
        }
        Set<SysUserRoleRela> userRoleRelas = role.getUserRoleRelas();
        if (!CollectionUtils.isEmpty(userRoleRelas)) {
            ExceptionHandler.publish("DMS-ADMIN-ROLE-0002", "请先解绑已经赋权的用户");
        }
        role.setStatus(StatusEnum.N);
        role.setGmtModified(DateUtil.getNow());
        role.getRoleMenuRelas().forEach(rela -> {
            rela.setGmtModified(DateUtil.getNow());
            rela.setStatus(StatusEnum.N);
        });
        roleDao.save(role);
    }
}
