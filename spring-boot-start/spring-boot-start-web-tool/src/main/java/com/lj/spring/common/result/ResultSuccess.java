package com.lj.spring.common.result;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by lijun on 2019/3/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class ResultSuccess extends Result {

    /**
     * 默认状态码
     */
    private static final Integer SUCCESS_CODE = 1000000;

    /**
     * 默认显示信息
     */
    private static final String SUCCESS_MESSAGE = "操作成功";

    @Builder
    public ResultSuccess(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 正常响应构建
     */
    public static ResultSuccess of(Object result) {
        return ResultSuccess.builder()
                .code(SUCCESS_CODE)
                .data(result)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    public static ResultSuccess defaultResultSuccess() {
        return ResultSuccess.builder()
                .code(SUCCESS_CODE)
                .data(null)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    public String toString() {
        return super.toString();
    }
}
