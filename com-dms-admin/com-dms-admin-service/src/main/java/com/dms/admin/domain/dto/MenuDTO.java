package com.dms.admin.domain.dto;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.dms.pub.enums.MenuTypeEnum;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yangchao.
 * @date 2019/2/24 14:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuDTO extends BaseModel {

    private String name;

    private String icon;

    private String url;

    private Long parentId;

    private MenuTypeEnum menuType;

    private List<MenuDTO> children = Lists.newArrayList();

    private List<RoleDTO> roles = Lists.newArrayList();

    public void addChild(MenuDTO child) {
        children = children == null ? Lists.newArrayList() : children;
        child.setParentId(this.getId());
        children.add(child);
    }

    public void addRole(RoleDTO role) {
        roles = roles == null ? Lists.newArrayList() : roles;
        roles.add(role);
    }

    public String getKey() {
        return String.valueOf(id);
    }

    public String getTitle() {
        return name;
    }
}
