package com.dms.admin.repo.jpa.model;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.dms.pub.enums.MenuTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class SysMenu extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    private String icon;

    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length = 8)
    private MenuTypeEnum menuType = MenuTypeEnum.M;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ID", updatable=false)
    private SysMenu parentMenu;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="parentMenu", orphanRemoval = true)
    @OrderBy("id")
    private Set<SysMenu> children = Sets.newHashSet();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="menu", orphanRemoval = true)
    @OrderBy("id")
    private Set<SysRoleMenuRela> roleMenuRelas = Sets.newHashSet();

    public void addChild(SysMenu child) {
        children = children == null ? Sets.newHashSet() : children;
        child.setParentMenu(this);
        children.add(child);
    }

    public void addRole(SysRole role) {
        roleMenuRelas = roleMenuRelas == null ? Sets.newHashSet() : roleMenuRelas;
        SysRoleMenuRela rela = new SysRoleMenuRela();
        rela.setRole(role);
        rela.setMenu(this);
        roleMenuRelas.add(rela);
    }

}
