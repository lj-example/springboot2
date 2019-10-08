package com.lj.spring.common.apollo.namespace;

import lombok.experimental.UtilityClass;

/**
 * Apollo的公共命名空间
 * Created by junli on 2019-09-26
 */
@UtilityClass
public class Namespace {

    /**
     * 默认命名空间
     */
    public final String APPLICATION = "application";

    /**
     * 数据库
     */
    public final String STRING_DATA_SOURCE = "spring.datasource";

    /**
     * 动态数据源
     */
    public final String DYNAMIC_DATA_SOURCE = "dynamicDataSource";

    /**
     * redis 连接信息
     */
    public final String STRING_REDIS = "redis";

    /**
     * 邮件组件
     */
    public final String STRING_MAIL = "spring.mail";
}
