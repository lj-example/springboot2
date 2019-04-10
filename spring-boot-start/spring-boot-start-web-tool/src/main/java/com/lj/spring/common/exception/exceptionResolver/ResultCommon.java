package com.lj.spring.common.exception.exceptionResolver;

import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultFail;
import com.lj.spring.common.result.ResultSuccess;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lijun on 2019/4/9
 */
@AllArgsConstructor
@Getter
public final class ResultCommon {
    /**
     * 默认的成功请求
     */
    public static final Result SUCCESS = ResultSuccess.defaultResultSuccess();

    /**
     * 错误请求 - 非法的参数
     */
    public static final Result INVALID_ARGUMENTS = ResultFail.buildResult(1000101, "非法的参数");

    /**
     * 错误请求 - 服务内部错误
     */
    public static final Result INTERNAL_SERVER_ERROR = ResultFail.buildResult(1000102, "服务内部错误");

    /**
     * 错误请求 - 资源未发现
     */
    public static final Result RESOURCE_NOT_FOUND = ResultFail.buildResult(1000103, "资源未发现");

    /**
     * 错误请求 - 请求非法
     */
    public static final Result FORBIDDEN = ResultFail.buildResult(1000104, "请求非法");

}