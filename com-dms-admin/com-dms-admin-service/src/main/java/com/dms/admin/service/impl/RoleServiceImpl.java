package com.dms.admin.service.impl;

import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.repo.jpa.dao.ISysRoleDao;
import com.dms.admin.repo.jpa.model.SysRole;
import com.dms.admin.repo.jpa.model.SysRoleMenuRela;
import com.dms.admin.repo.jpa.model.SysUserRoleRela;
import com.dms.admin.service.IRoleService;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private ISysRoleDao roleDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public RoleDTO create(SysRole role) {
        role = roleDao.save(role);
        return ObjectUtil.shallowCopy(role, RoleDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public void remove(SysRole role) {
        role = roleDao.findOne(role.getId());
        Set<SysUserRoleRela> userRoleRelas = role.getUserRoleRelas();
        Set<SysRoleMenuRela> roleMenuRelas = role.getRoleMenuRelas();
        if (!CollectionUtils.isEmpty(userRoleRelas)) {
            ExceptionHandler.publish("DMS-ADMIN-ROLE-0001", "请先解绑相关用户");
        }
        roleDao.delete(role);
    }
}
