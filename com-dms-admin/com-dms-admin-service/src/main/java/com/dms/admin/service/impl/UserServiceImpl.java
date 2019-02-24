package com.dms.admin.service.impl;

import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.repo.jpa.dao.ISysRoleDao;
import com.dms.admin.repo.jpa.dao.ISysUserDao;
import com.dms.admin.repo.jpa.model.SysRole;
import com.dms.admin.repo.jpa.model.SysUser;
import com.dms.admin.repo.jpa.model.SysUserRoleRela;
import com.dms.admin.service.IUserService;
import com.dms.pub.util.ObjectUtil;
import com.dms.pub.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yangchao.
 * @date 2019/2/23 16:31
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private ISysUserDao userDao;
    @Autowired
    private ISysRoleDao roleDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public UserDTO create(final SysUser userParam) {
        userParam.setUserpwd(SecurityUtil.md5Encrypt(userParam.getUsername() + "/dms/" + userParam.getUserpwd()));
        Set<Long> roleIds = userParam.getUserRoleRelas().stream().map(rela -> rela.getRole().getId()).collect(Collectors.toSet());
        userParam.getUserRoleRelas().clear();
        SysUser user = userDao.save(userParam);
        roleIds.stream().forEach(roleId -> {
            SysRole role = new SysRole();
            role.setId(roleId);
            user.addRole(role);
        });
        userDao.save(userParam);
        return ObjectUtil.shallowCopy(user, UserDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public UserDTO modify(SysUser userParam, Set<Long> roleIds) {
        SysUser user = userDao.findOne(userParam.getId());
        roleIds.stream().forEach(roleId -> {
            SysRole role = new SysRole();
            role.setId(roleId);
            user.addRole(role);
        });
        userDao.save(user);
        return ObjectUtil.shallowCopy(user, UserDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public void remove(Long userId) {
        userDao.delete(userId);
    }
}
