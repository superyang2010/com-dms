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
public class SysMenuFunction extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="MENU_ID", nullable = false)
    private SysMenu menu;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="PARENT_ID", nullable = false)
    private SysMenuFunction parentFunc;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="parentFunc")
    @OrderBy("id")
    private Set<SysMenuFunction> children = Sets.newHashSet();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="menuFunction")
    @OrderBy("id")
    private Set<SysUserMenuFuncRela> userMenuFuncRelas = Sets.newHashSet();
}
