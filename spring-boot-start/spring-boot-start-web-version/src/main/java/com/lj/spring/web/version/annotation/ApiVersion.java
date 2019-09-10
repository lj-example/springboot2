package com.lj.spring.web.version.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 用以定义基础的版本信息 - 该版本信息最终会拼接在请求路径上
 * Created by junli on 2019-09-05
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiVersion {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
