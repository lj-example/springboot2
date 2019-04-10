package com.lj.spring.common.exception;


import com.lj.spring.common.result.ResultFail;

/**
 * Created by lijun on 2019/4/4
 */
public abstract class AbstractException extends RuntimeException {

    public AbstractException(String rules) {
        super(rules);
    }

    /**
     * 错误结果 值
     */
    protected ResultFail resultFail;

    /**
     * 提示消息
     */
    protected String message;

    public ResultFail getResultFail() {
        return resultFail;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
