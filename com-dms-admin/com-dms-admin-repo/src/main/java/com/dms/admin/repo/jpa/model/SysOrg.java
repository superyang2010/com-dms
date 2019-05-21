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
 * @date 2019/5/23 11:35
 */
@Entity
@Table
@Getter
@Setter
@ToString(callSuper = true)
public class SysOrg extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ManyToOne(cascade= CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ID", updatable=false)
    private SysOrg parentOrg;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="parentOrg", orphanRemoval = true)
    @OrderBy("id")
    private Set<SysOrg> children = Sets.newHashSet();

    public void addChild(SysOrg child) {
        children = children == null ? Sets.newHashSet() : children;
        child.setParentOrg(this);
        children.add(child);
    }

}
