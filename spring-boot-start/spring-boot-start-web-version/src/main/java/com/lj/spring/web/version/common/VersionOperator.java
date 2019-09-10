package com.lj.spring.web.version.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by junli on 2019-09-05
 */
@AllArgsConstructor
@Getter
public enum VersionOperator {
    /**
     * 默认，无运算
     */
    NIL(""),

    /**
     * 小于
     */
    LT("<"),

    /**
     * 大于
     */
    GT(">"),

    /**
     * 小于等于
     */
    LTE("<="),

    /**
     * 大于等于
     */
    GTE(">="),

    /**
     * 不等于
     */
    NE("!="),

    /**
     * 等于
     */
    EQ("=="),

    /**
     * 存在,此时多个值 需要使用 ','分隔
     */
    IN("in"),

    /**
     * 不存在,此时存在多个值 需要使用 ','分隔
     */
    NIN("not in");

    /**
     * 运算符
     */
    private String code;

}
