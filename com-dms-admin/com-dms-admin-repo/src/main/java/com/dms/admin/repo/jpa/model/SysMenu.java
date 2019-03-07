package com.dms.admin.repo.jpa.model;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.dms.pub.enums.MenuTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
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
public class SysMenu extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    private String icon;

    private String url;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable=false)
    private MenuTypeEnum menuType = MenuTypeEnum.MENU;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ID", updatable=false)
    private SysMenu parentMenu;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="parentMenu", orphanRemoval = true)
    @OrderBy("id")
    private Set<SysMenu> children = Sets.newHashSet();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="menu")
    @OrderBy("id")
    private Set<SysRoleMenuRela> roleMenuRelas = Sets.newHashSet();

    public void addChild(SysMenu child) {
        children = children == null ? Sets.newHashSet() : children;
        child.setParentMenu(this);
        children.add(child);
    }

}
