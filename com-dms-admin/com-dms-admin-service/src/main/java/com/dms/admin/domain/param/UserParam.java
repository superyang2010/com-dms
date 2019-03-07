package com.dms.admin.domain.param;

import com.dms.admin.base.BaseParam;
import com.dms.pub.common.PageParam;
import com.dms.pub.enums.StatusEnum;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * @author yangchao.
 * @date 2019/3/6 19:31
 */
@Data
public class UserParam extends BaseParam {

    private String username;

    private String userpwd;

    private Integer status;

    private String notes;

    /**
     * 角色 id 列表
     */
    private Set<Long> roleIds = Sets.newHashSet();

    /**
     * 菜单功能点 id 列表
     */
    private Set<Long> menuFuncIds = Sets.newHashSet();

}
