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
}
