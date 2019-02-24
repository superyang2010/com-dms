package com.dms.pub.base;

/**
 * @author yangchao.
 * @date 2019/2/20 15:37
 */
public interface BaseEnum<E extends Enum<?>, T> {
    /**
     * 获取实际值
     * @return
     */
    T getValue();

    /**
     * 获取显示值
     * @return
     */
    String getName();

    /**
     * 根据 value 获取对应的枚举对象
     * @param value
     * @return
     */
    E getByValue(T value);
}
