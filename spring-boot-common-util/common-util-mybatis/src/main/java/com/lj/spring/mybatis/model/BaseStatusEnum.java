package com.lj.spring.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lijun on 2019/5/8
 */
@Getter
@AllArgsConstructor
public enum  BaseStatusEnum {

    NORMAL(1, "正常"),
    DELETED(-1, "逻辑删除");
    private Integer code;
    private String name;

    /**
     * 是否为正常数据
     * @param code 需要判断的code
     * @return 正常数据返回 true
     */
    public static Boolean isNormal(int code) {
        return code == NORMAL.getCode();
    }

}
