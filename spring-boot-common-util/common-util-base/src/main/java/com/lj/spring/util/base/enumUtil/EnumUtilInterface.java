package com.lj.spring.util.base.enumUtil;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by junli on 2019-07-11
 */
public interface EnumUtilInterface {


    /**
     * key
     */
    int getKey();

    /**
     * value
     */
    String getValue();

    /**
     * 描述
     */
    default String desc() {
        return StringUtils.EMPTY;
    }

    /**
     * 判断 key 是否相等
     * 相等 返回 true
     */
    default boolean is(int key) {
        return getKey() == key;
    }

    /**
     * 判断 key 是否相等
     *
     * @param key 待比价值
     * @return 不相等 返回true
     */
    default boolean not(int key) {
        return !is(key);
    }
}
