package com.dms.admin.domain.dto;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/2/24 14:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseModel {

    private String username;

    private List<RoleDTO> roles = Lists.newArrayList();

    public void addRole(RoleDTO role) {
        roles = roles == null ? Lists.newArrayList() : roles;
        roles.add(role);
    }
}
