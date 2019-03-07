package com.dms.admin.service.impl;

import com.dms.admin.base.BaseService;
import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.UserParam;
import com.dms.admin.repo.jpa.dao.ISysRoleDao;
import com.dms.admin.repo.jpa.dao.ISysUserDao;
import com.dms.admin.repo.jpa.model.SysRole;
import com.dms.admin.repo.jpa.model.SysUser;
import com.dms.admin.service.IUserService;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.DateUtil;
import com.dms.pub.util.ObjectUtil;
import com.dms.pub.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/2/23 16:31
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseService implements IUserService {

    @Autowired
    private ISysUserDao userDao;
    @Autowired
    private ISysRoleDao roleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UserDTO> query(UserParam userParam) {
        SysUser user = new SysUser();
        user.setCode(userParam.getCode());
        user.setUsername(userParam.getUsername());
        StatusEnum status = userParam.getStatus() != null ? StatusEnum.getByValue(userParam.getStatus()) : null;
        user.setStatus(status);
        user.setGmtCreated(null);
        Example<SysUser> param = Example.of(user);
        Pageable pageable = this.buildPageParam(userParam);
        Page<UserDTO> users = this.userDao.findAll(param, pageable).map(u -> ObjectUtil.shallowCopy(u, UserDTO.class));
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public UserDTO create(final UserParam userParam) {
        Set<Long> roleIds = userParam.getRoleIds();
        SysUser user = new SysUser();
        user.setCode(userParam.getCode());
        user.setUsername(userParam.getUsername());
        user.setUserpwd(SecurityUtil.md5Encrypt(userParam.getUsername() + "/dms/" + userParam.getUserpwd()));
        user.setNotes(userParam.getNotes());
        roleIds.stream().forEach(roleId -> {
            SysRole role = new SysRole();
            role.setId(roleId);
            user.addRole(role);
        });
        SysUser user1 = userDao.save(user);
        return ObjectUtil.shallowCopy(user1, UserDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public UserDTO modify(UserParam userParam) {
        Set<Long> roleIds = userParam.getRoleIds();
        SysUser user = userDao.findOne(userParam.getId());
        if (user == null) {
            ExceptionHandler.publish("DMS-ADMIN-USER-0001", "非法参数");
        }
        user.setNotes(userParam.getNotes());
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
        SysUser user = userDao.findOne(userId);
        if (user == null) {
            ExceptionHandler.publish("DMS-ADMIN-USER-0001", "非法参数");
        }
        user.setStatus(StatusEnum.INVALID);
        user.setGmtModified(DateUtil.getNow());
        user.getUserRoleRelas().forEach(rela -> {
            rela.setGmtModified(DateUtil.getNow());
            rela.setStatus(StatusEnum.INVALID);
        });
        userDao.save(user);
    }
}
