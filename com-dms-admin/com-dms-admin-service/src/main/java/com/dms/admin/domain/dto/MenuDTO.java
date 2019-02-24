package com.dms.admin.domain.dto;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.dms.admin.repo.jpa.model.SysMenu;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    private Set<SysMenu> children = Sets.newHashSet();
}