package com.dms.pub.enums;

import com.dms.pub.base.BaseEnum;
import com.dms.pub.exception.BusiException;

/**
 * @author yangchao.
 * @date 2019/2/20 15:19
 */
public enum MenuTypeEnum implements BaseEnum<MenuTypeEnum, String> {

    /**
     * 普通菜单
     */
    M("M", "普通菜单"),
    /**
     * 菜单功能点
     */
    F("F", "菜单功能点");

    /**
     * 枚举值
     */
    private String value;
    /**
     * 枚举名称
     */
    private String desc;

    MenuTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static MenuTypeEnum getByValue(String value) {
        MenuTypeEnum[] enums = MenuTypeEnum.values();
        for(MenuTypeEnum e : enums) {
            if(e.getValue().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new BusiException("未知的枚举值：" + value + ", 请核对 " + MenuTypeEnum.class.getName());
    }
}