package com.lj.demo.api;

import com.lj.spring.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Created by junli on 2019-07-18
 */
@Api(value = "redis示例", tags = "redis示例")
public interface RedisTestApi {

    @ApiOperation(value = "redis 存取")
    Result redisTemplateTest(@ApiParam("key") String key, @ApiParam("value") String value);

    @ApiOperation(value = "redis 锁测试")
    Result redisLockTest() throws InterruptedException;
}
