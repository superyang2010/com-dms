package com.dms.admin.service;

import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.repo.jpa.model.SysUser;

import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/2/23 16:21
 */
public interface IUserService {

    UserDTO create(SysUser userParam);

    UserDTO modify(SysUser userParam, Set<Long> roleIds);

    void remove(Long userId);
}
