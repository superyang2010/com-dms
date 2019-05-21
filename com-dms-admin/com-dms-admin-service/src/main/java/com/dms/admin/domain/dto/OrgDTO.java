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
public class OrgDTO extends BaseModel {

    private String name;

    private Long parentId;

    private List<OrgDTO> children = Lists.newArrayList();

    public void addChild(OrgDTO child) {
        children = children == null ? Lists.newArrayList() : children;
        child.setParentId(this.getId());
        children.add(child);
    }

    public String getKey() {
        return String.valueOf(id);
    }

    public String getTitle() {
        return name;
    }
}
