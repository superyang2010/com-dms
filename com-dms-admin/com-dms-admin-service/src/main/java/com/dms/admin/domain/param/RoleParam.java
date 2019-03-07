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
public class RoleParam extends BaseParam {

    private String name;

    private Integer status;

    private String notes;

    /**
     * 菜单 id 列表
     */
    private Set<Long> menuIds = Sets.newHashSet();

}
