package com.dms.pub.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author yang.chao.
 * @date 2019/2/19
 */
@Slf4j
public class ObjectUtil {

	/**
	 * 对象深拷贝
	 * @param source
	 * @param target
	 * @return
	 */
	public static <T> T deepCopy(Object source, Class<T> target) {
		return JSON.parseObject(JSON.toJSONString(source), target);
	}

	/**
	 * 对象List深拷贝
	 * @param source
	 * @param target
	 * @return
	 */
	public static <T> List<T> deepCopyList(Object source, Class<T> target) {
		return JSON.parseArray(JSON.toJSONString(source), target);
	}

	/**
	 * 对象浅拷贝
	 * @param source
	 * @param target
	 */
	public static <T> T shallowCopy(Object source, Class<T> target) {
		T t = null;
		try {
			t = target.newInstance();
			BeanUtils.copyProperties(source, t);
		} catch (InstantiationException e) {
			log.error("shallow copy InstantiationException", e);
		} catch (IllegalAccessException e) {
			log.error("shallow copy IllegalAccessException", e);
		}
		return t;
	}
}
