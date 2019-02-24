package com.dms.admin.repo.jpa.model;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/2/23 11:35
 */
@Entity
@Table
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy="role")
    private Set<SysRoleMenuRela> roleMenuRelas = Sets.newHashSet();

    @OneToMany(mappedBy="role")
    private Set<SysUserRoleRela> userRoleRelas = Sets.newHashSet();
}
