package com.lj.spring.common.result;

import lombok.Getter;

import java.io.Serializable;

/**
 * Created by lijun on 2019/3/26
 */
@Getter
public abstract class Result<T> implements Serializable {

    /**
     * 默认值
     */
    protected static Object DEFAULT_DATA = "";

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
    protected T data;

    @Override
    public String toString() {
        return String.format("{ \"code\": %s ,\"message\": \"%s\", \"data\": \"%s\"}",
                code, message, data.toString());
    }
}
