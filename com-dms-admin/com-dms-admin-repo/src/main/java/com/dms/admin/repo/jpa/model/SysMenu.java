package com.dms.admin.repo.jpa.model;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/2/23 11:35
 */
@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    private String icon;

    private String url;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="PARENT_ID", updatable=false)
    private SysMenu parentMenu;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="parentMenu")
    @OrderBy("id")
    private Set<SysMenu> children = Sets.newHashSet();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="menu")
    @OrderBy("id")
    private Set<SysRoleMenuRela> roleMenuRelas = Sets.newHashSet();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="menu")
    @OrderBy("id")
    private Set<SysMenuFunction> menuFunctions = Sets.newHashSet();
}
