package com.dms.admin.domain.param;

import com.dms.admin.base.BaseParam;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/3/6 19:31
 */
@Data
public class MenuParam extends BaseParam {

    private String name;

    private String notes;

    private Long parentId;

    private String icon;

    private String url;

    private String menuType;

    /**
     * 角色 id 列表
     */
    private Set<Long> roleIds = Sets.newHashSet();

}
