package com.dms.admin.repo.jpa.model;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/2/22 21:35
 */
@Entity
@Table
@Getter
@Setter
@ToString(callSuper = true)
public class SysUser extends BaseModel {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String userpwd;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private Set<SysUserRoleRela> userRoleRelas = Sets.newHashSet();

    public void addRole(SysRole role) {
        SysUserRoleRela rela = new SysUserRoleRela();
        rela.setUser(this);
        rela.setRole(role);
        this.getUserRoleRelas().add(rela);
    }
}
