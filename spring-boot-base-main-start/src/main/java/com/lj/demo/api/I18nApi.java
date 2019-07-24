package com.lj.demo.api;

import com.lj.spring.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by junli on 2019-07-18
 */
@Api(value = "i18n示例", tags = "i18n示例")
public interface I18nApi {

    @ApiOperation("获取多语言信息")
    @ApiImplicitParam(name = "key", value = "查询名称", dataTypeClass = String.class)
    Result getMessageByKey(String key);

    @ApiOperation("获取多语言枚举信息")
    List<HashMap<String, Object>> testEnumUtil();

    @ApiOperation("测试数据库类型多语言工具")
    String testDBI18nUtil();
}

