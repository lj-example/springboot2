package com.lj.spring.web.user.annotation;

import com.lj.spring.common.head.HeadCommon;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by junli on 2019-07-01
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UserToken {

    @AliasFor("name")
    String value() default HeadCommon.USER_TOKEN;

    @AliasFor("value")
    String name() default HeadCommon.USER_TOKEN;

    /**
     * 是否必传
     */
    boolean required() default true;

    /**
     * 默认值
     */
    String defaultValue() default "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n";

    /**
     * 验证信息是否有效
     */
    boolean check() default true;
}

