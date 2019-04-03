package com.lj.spring.start.common.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lijun on 2019/3/26
 */
@Data
public final class SuccessResult extends Result implements Serializable {

    /**
     * 状态值
     */
    protected Integer code;

    /**
     * 提示信息
     */
    protected String message;

    /**
     * 返回结果值
     */
    protected Object data;

    /**
     * 默认状态码
     */
    private static final Integer SUCCESS_CODE = 1000000;

    /**
     * 默认显示信息
     */
    private static final String SUCCESS_MESSAGE = "操作成功";

    @Builder
    public SuccessResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 正常响应构建
     */
    public static SuccessResult buildResult(Object result) {
        return SuccessResult.builder()
                .code(SUCCESS_CODE)
                .data(result)
                .message(SUCCESS_MESSAGE)
                .build();
    }

}
