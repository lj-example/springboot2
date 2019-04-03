package com.lj.spring.common.result;

import java.io.Serializable;

/**
 * Created by lijun on 2019/3/26
 */
public abstract class Result implements Serializable {

    /**
     * 状态值
     */
    public Integer code;

    /**
     * 提示信息
     */
    public String message;

    /**
     * 返回结果值
     */
    public Object data;

}
