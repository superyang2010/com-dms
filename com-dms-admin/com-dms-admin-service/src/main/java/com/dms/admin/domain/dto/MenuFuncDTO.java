package com.dms.admin.domain.dto;

import com.dms.admin.repo.jpa.base.BaseModel;
import com.dms.admin.repo.jpa.model.SysMenuFunction;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.util.List;
import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/2/24 14:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuFuncDTO extends BaseModel {

    private String name;

    private Long menuId;

    private Long parentId;

    private List<SysMenuFunction> children = Lists.newArrayList();;
}
