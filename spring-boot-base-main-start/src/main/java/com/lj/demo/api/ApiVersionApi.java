package com.lj.demo.api;

import com.lj.spring.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by junli on 2019-09-06
 */
@Api(value = "版本管理", tags = "版本管理")
public interface ApiVersionApi {

    @ApiOperation(value = "类上版本控制")
    Result apiForType();

    @ApiOperation(value = "方法上的版本控制")
    Result apiForMethod();

    @ApiOperation(value = "类版本控制 + 方法版本控制")
    Result apiForTypeAndMethod();
}
