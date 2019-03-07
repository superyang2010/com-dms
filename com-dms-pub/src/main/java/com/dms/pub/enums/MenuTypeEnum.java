package com.dms.pub.enums;

import com.dms.pub.base.BaseEnum;
import com.dms.pub.exception.BusiException;

/**
 * @author yangchao.
 * @date 2019/2/20 15:19
 */
public enum MenuTypeEnum implements BaseEnum<MenuTypeEnum, Integer> {

    /**
     * 普通菜单
     */
    MENU(1, "普通菜单"),
    /**
     * 有效
     */
    FUNCTION(2, "功能点");

    /**
     * 枚举值
     */
    private Integer value;
    /**
     * 枚举名称
     */
    private String name;

    MenuTypeEnum(int value, String name) {
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

    public static MenuTypeEnum getByValue(Integer value) {
        MenuTypeEnum[] enums = MenuTypeEnum.values();
        for(MenuTypeEnum e : enums) {
            if(e.getValue().intValue() == value) {
                return e;
            }
        }
        throw new BusiException("未知的枚举值：" + value + ", 请核对 " + MenuTypeEnum.class.getName());
    }
}