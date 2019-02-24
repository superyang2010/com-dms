package com.dms.admin.service;

import com.dms.admin.domain.dto.RoleDTO;
import com.dms.admin.repo.jpa.model.SysRole;

/**
 * @author yangchao.
 * @date 2019/2/23 16:21
 */
public interface IRoleService {

    RoleDTO create(SysRole role);

    void remove(SysRole role);
}
