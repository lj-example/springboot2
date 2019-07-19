package com.lj.spring.common.result;

import lombok.Builder;

/**
 * Created by lijun on 2019/3/26
 */
public class ResultFail extends Result {

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


    public static ResultFail buildResult(Object result) {
        return ResultFail.builder()
                .code(FAIL_CODE)
                .data(result)
                .message(FAIL_MESSAGE)
                .build();
    }

    public static ResultFail of(Integer code) {
        return ResultFail.builder()
                .code(code)
                .data(DEFAULT_DATA)
                .message(FAIL_MESSAGE)
                .build();
    }

    public static ResultFail of(String message) {
        return ResultFail.builder()
                .code(FAIL_CODE)
                .data(DEFAULT_DATA)
                .message(message)
                .build();
    }

    public static ResultFail of(Integer code, String message) {
        return ResultFail.builder()
                .code(code)
                .data(DEFAULT_DATA)
                .message(message)
                .build();
    }

    public static ResultFail defaultResultFail() {
        return ResultFail.builder()
                .code(FAIL_CODE)
                .data(DEFAULT_DATA)
                .message(FAIL_MESSAGE)
                .build();
    }

    public static Integer getFailCode() {
        return FAIL_CODE;
    }

    public static String getFailMessage() {
        return FAIL_MESSAGE;
    }

    @Override
    public String toString() {
        return String.format("{ \"code\": %s ,\"message\": \"%s\", \"data\": \"%s\"}",
                code, message, data.toString());
    }
}
