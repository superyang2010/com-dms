package com.dms.pub.enums;

import com.dms.pub.base.BaseEnum;
import com.dms.pub.exception.BusiException;

/**
 * @author yangchao.
 * @date 2019/2/20 15:19
 */
public enum StatusEnum implements BaseEnum<StatusEnum, Integer> {

    /**
     * 无效
     */
    INVALID(0, "无效"),
    /**
     * 有效
     */
    VALID(1, "有效");

    /**
     * 枚举值
     */
    private Integer value;
    /**
     * 枚举名称
     */
    private String name;

    StatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public StatusEnum getByValue(Integer value) {
        StatusEnum[] enums = StatusEnum.values();
        for(StatusEnum e : enums) {
            if(String.valueOf(e.getValue()).equals(value)) {
                return e;
            }
        }
        throw new BusiException("未知的枚举值：" + value + ", 请核对 " + this.getDeclaringClass());
    }
}