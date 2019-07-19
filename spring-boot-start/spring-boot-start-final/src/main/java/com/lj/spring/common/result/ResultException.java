package com.lj.spring.common.result;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by lijun on 2019/4/9
 */
@Setter
@Getter
public class ResultException extends ResultFail {

    /**
     * 错误信息
     */
    private String trace;

    public ResultException(Integer code, String message, Object data, String trace) {
        super(code, message, data);
        this.trace = trace;
    }

    public static ResultException create(Integer code, String message, Object data, String trace) {
        return new ResultException(code, message, data, trace);
    }

    @Override
    public String toString() {
        return String.format("{ \"code\": %s ,\"message\": \"%s\", \"data\": \"%s\", \"trace\": \"%s\"}"
                , code, message, data.toString(), trace);
    }
}
