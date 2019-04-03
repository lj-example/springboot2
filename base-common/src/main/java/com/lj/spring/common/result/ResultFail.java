package com.lj.spring.common.result;

import lombok.Builder;

/**
 * Created by lijun on 2019/3/26
 */
public final class ResultFail extends Result{

    /**
     * 默认状态码
     */
    private static final Integer FAIL_CODE = 5000000;

    /**
     * 默认显示信息
     */
    private static final String FAIL_MESSAGE = "操作失败";

    @Builder
    public ResultFail(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 失败响应构建
     */
    public static ResultFail buildResult(Object result) {
        return ResultFail.builder()
                .code(FAIL_CODE)
                .data(result)
                .message(FAIL_MESSAGE)
                .build();
    }

}
