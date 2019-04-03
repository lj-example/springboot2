package com.lj.spring.start.common.result;

import java.io.Serializable;

/**
 * Created by lijun on 2019/3/26
 */
public abstract class Result implements Serializable {

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
    protected Object result;

}
