package com.lj.spring.dataSource.core.dynamic;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by lijun on 2019/5/8
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataSourceType {

    /**
     * 使用的数据源
     */
    @AliasFor("value")
    String name() default DynamicCommon.MASTER_NAME;

    @AliasFor("name")
    String value() default DynamicCommon.MASTER_NAME;
}
