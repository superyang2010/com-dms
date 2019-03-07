package com.dms.admin.domain.param;

import com.dms.admin.base.BaseParam;
import com.dms.pub.enums.MenuTypeEnum;
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

    private Integer status;

    private String notes;

    private Long parentId;

    private String icon;

    private String url;

    private Integer menuType;

}
