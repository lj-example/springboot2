package com.lj.demo.controller;

import com.lj.demo.api.ApiVersionApi;
import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultSuccess;
import com.lj.spring.web.version.annotation.*;
import com.lj.spring.web.version.common.VersionOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by junli on 2019-09-06
 */
@Slf4j
@RestController
@RequestMapping("apiVersion")
@RequiredArgsConstructor
@ApiVersion("v2")
public class ApiVersionController implements ApiVersionApi {

    /**
     * 类版本控制
     */
    @GetMapping("apiForType")
    @Override
    public Result apiForType() {
        return ResultSuccess.defaultResultSuccess();
    }

    /**
     * 方法版本控制
     */
    @GetMapping("apiForMethod")
    @Override
    @ApiClientInfo(channel = @ApiClientChannel(value = "1,3", operator = VersionOperator.IN))
    public Result apiForMethod() {
        return ResultSuccess.defaultResultSuccess();
    }

    /**
     * 类与方法版本控制
     */
    @GetMapping("apiForTypeAndMethod")
    @Override
    @ApiClientInfo(
            version = @ApiClientVersion(value = "2.2", operator = VersionOperator.GT),
            channel = @ApiClientChannel(value = "1001,1002", operator = VersionOperator.IN),
            platform = @ApiClientPlatform(value = "ios", operator = VersionOperator.NE)
    )
    public Result apiForTypeAndMethod() {
        return ResultSuccess.defaultResultSuccess();
    }
}
