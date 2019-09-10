package com.lj.spring.web.version.annotation;


import com.lj.spring.web.version.common.Common;
import com.lj.spring.web.version.common.VersionOperator;

import java.lang.annotation.*;

/**
 * Created by junli on 2019-09-05
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiClientInfo {

    /**
     * 版本信息
     */
    ApiClientVersion version() default @ApiClientVersion(value = Common.DEFAULT_CROSS, operator = VersionOperator.GTE);

    /**
     * 渠道信息
     */
    ApiClientChannel channel() default @ApiClientChannel(value = Common.DEFAULT_CROSS, operator = VersionOperator.EQ);

    /**
     * 来源信息
     */
    ApiClientPlatform platform() default @ApiClientPlatform(value = Common.DEFAULT_CROSS, operator = VersionOperator.EQ);
}
