package com.lj.spring.web.version.annotation;


import com.lj.spring.web.version.common.Common;
import com.lj.spring.web.version.common.VersionOperator;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by junli on 2019-09-05
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiClientVersion {

    /**
     * 版本信息,多个值使用',' 分隔，支持 in,not in
     */
    @AliasFor("name")
    String value() default Common.DEFAULT_CROSS;

    @AliasFor("value")
    String name() default Common.DEFAULT_CROSS;

    /**
     * 运算符
     */
    VersionOperator operator() default VersionOperator.GTE;

}
