package com.dms.admin.base;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author yangchao.
 * @date 2019/3/7 11:32
 */
@Data
public class BaseParam {

    protected Long id;

    protected String code;

    /**
     * 分页参数
     */
    protected Integer pageIndex = 0;
    protected Integer pageSize = 20;

    /**
     * 排序参数: key - 属性名； value - ASC|DESC
     */
    protected Map<String, String> sorts = Maps.newLinkedHashMap();
}
