package com.dms.admin.service.impl;

import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.UserParam;
import com.dms.admin.repo.jpa.dao.ISysRoleDao;
import com.dms.admin.repo.jpa.dao.ISysUserDao;
import com.dms.admin.repo.jpa.model.SysRole;
import com.dms.admin.repo.jpa.model.SysUser;
import com.dms.admin.repo.jpa.model.SysUserRoleRela;
import com.dms.admin.service.IUserService;
import com.dms.pub.enums.StatusEnum;
import com.dms.pub.util.DateUtil;
import com.dms.pub.util.ObjectUtil;
import com.dms.pub.util.SecurityUtil;
import com.google.common.collect.Lists;
import com.sun.org.apache.bcel.internal.generic.DREM;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UserDTO> query(UserParam userParam) {
        SysUser user = new SysUser();
        user.setCode(userParam.getCode());
        user.setUsername(userParam.getUsername());
        user.setStatus(userParam.getStatus());
        Example<SysUser> param = Example.of(user);
        Map<String, String> sorts = userParam.getSorts();
        List<Sort.Order> orders = Lists.newArrayList();
        sorts.forEach((property, direc) -> {
            Sort.Direction direction = Sort.Direction.fromString(direc);
            Sort.Order order = new Sort.Order(direction, property);
            orders.add(order);
        });
        Sort sort = new Sort(orders);
        Pageable pageable = new PageRequest(userParam.getPageIndex(), userParam.getPageSize(), sort);
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

        //userDao.save(userParam);
        return ObjectUtil.shallowCopy(user1, UserDTO.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Throwable.class)
    public UserDTO modify(UserParam userParam) {
        Set<Long> roleIds = userParam.getRoleIds();
        SysUser user = userDao.findOne(userParam.getId());
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
        if (user != null) {
            user.setStatus(StatusEnum.INVALID);
            user.setGmtModified(DateUtil.getNow());
            userDao.save(user);
        }
    }
}
