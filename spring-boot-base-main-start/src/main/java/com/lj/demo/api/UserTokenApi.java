package com.lj.demo.api;

import com.lj.spring.common.result.Result;
import com.lj.spring.web.user.model.UserSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by junli on 2019-07-23
 */
@Api(value = "userToken示例", tags = "userToken示例")
public interface UserTokenApi {

    @ApiOperation(value = "登入")
    Result login();

    @ApiOperation(value = "登出")
    Result loginOut(@ApiIgnore String token, @ApiIgnore UserSession userSession);

    @ApiOperation(value = "登录信息测试")
    Result check(@ApiIgnore String token, @ApiIgnore UserSession userSession);
}

