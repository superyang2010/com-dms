package com.dms.admin.domain.dto;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

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

    private List<MenuDTO> children = Lists.newArrayList();

    private List<MenuFuncDTO> menuFuncs = Lists.newArrayList();

    public void addChildMenu(MenuDTO child) {
        children = children == null ? Lists.newArrayList() : children;
        children.add(child);
    }

    public void addMenuFunc(MenuDTO child) {
        children = children == null ? Lists.newArrayList() : children;
        children.add(child);
    }
}
