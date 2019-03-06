package com.dms.admin.domain.param;

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
public class UserParam {

    private Long id;

    private String code;

    private String username;

    private String userpwd;

    private StatusEnum status = StatusEnum.VALID;

    private String notes;

    /**
     * 角色 id 列表
     */
    private Set<Long> roleIds = Sets.newHashSet();

    /**
     * 菜单功能点 id 列表
     */
    private Set<Long> menuFuncIds = Sets.newHashSet();

    /**
     * 分页参数
     */
    private Integer pageIndex;
    private Integer pageSize;

    /**
     * 排序参数: key - 属性名； value - ASC|DESC
     */
    private Map<String, String> sorts = Maps.newLinkedHashMap();
}
