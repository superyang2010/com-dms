package com.dms.admin.repo.jpa.model;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.google.common.collect.Sets;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/2/23 11:35
 */
@Entity
@Table
@Getter
@Setter
@ToString(callSuper = true)
public class SysRole extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="role", orphanRemoval = true)
    private Set<SysRoleMenuRela> roleMenuRelas = Sets.newHashSet();

    @OneToMany(mappedBy="role")
    private Set<SysUserRoleRela> userRoleRelas = Sets.newHashSet();

    public void addMenu(SysMenu menu) {
        SysRoleMenuRela rela = new SysRoleMenuRela();
        rela.setMenu(menu);
        rela.setRole(this);
        this.getRoleMenuRelas().add(rela);
    }
}
