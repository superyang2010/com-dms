package com.dms.pub.enums;

import com.dms.pub.base.BaseEnum;
import com.dms.pub.exception.BusiException;

/**
 * @author yangchao.
 * @date 2019/2/20 15:19
 */
public enum StatusEnum implements BaseEnum<StatusEnum, String> {

    /**
     * 无效
     */
    N("N", "无效"),
    /**
     * 有效
     */
    Y("Y", "有效");

    /**
     * 枚举值
     */
    private String value;
    /**
     * 枚举名称
     */
    private String desc;

    StatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static StatusEnum getByValue(String value) {
        StatusEnum[] enums = StatusEnum.values();
        for(StatusEnum e : enums) {
            if(e.getValue().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new BusiException("未知的枚举值：" + value + ", 请核对 " + StatusEnum.class.getName());
    }
}