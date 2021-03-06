package com.dms.admin.service.impl;

import com.dms.admin.base.BaseService;
import com.dms.admin.domain.dto.RoleDTO;
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
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("code", match -> match.contains())
                .withMatcher("username", match -> match.contains())
                .withIgnorePaths("gmtCreated");
        Example<SysUser> param = Example.of(user, matcher);
        Pageable pageable = this.buildPageParam(userParam);
        Page<UserDTO> users = this.userDao.findAll(param, pageable).map(u -> {
            UserDTO dto = ObjectUtil.shallowCopy(u, UserDTO.class);
            List<RoleDTO> roles = u.getUserRoleRelas().stream().map(rela -> ObjectUtil.shallowCopy(rela.getRole(), RoleDTO.class)).collect(Collectors.toList());
            dto.setRoles(roles);
            return dto;
        });
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
        user.setGmtModified(DateUtil.getNow());
        user.setNotes(userParam.getNotes());
        user.getUserRoleRelas().clear();
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
    public void remove(UserParam userParam) {
        SysUser user = userDao.findOne(userParam.getId());
        if (user == null) {
            ExceptionHandler.publish("DMS-ADMIN-USER-0001", "非法参数");
        }
        user.setStatus(userParam.isEnabled() ? StatusEnum.Y : StatusEnum.N);
        user.setGmtModified(DateUtil.getNow());
        user.getUserRoleRelas().forEach(rela -> {
            rela.setGmtModified(DateUtil.getNow());
            rela.setStatus(StatusEnum.N);
        });
        userDao.save(user);
    }
}
