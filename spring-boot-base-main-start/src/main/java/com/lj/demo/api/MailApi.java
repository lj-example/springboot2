package com.lj.demo.api;

import com.lj.spring.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by junli on 2019-09-25
 */
@Api(value = "邮件示例", tags = "邮件示例")
public interface MailApi {

    @ApiOperation("发送邮件")
    Result sendMail();
}
