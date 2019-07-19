package com.lj.spring.web.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户 userToken
 * Created by junli on 2019-07-01
 */
@Data
@AllArgsConstructor
@Builder
public class UserSession implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户登录用的手机号
     */
    private String mobile;

    /**
     * 用户IP
     */
    private String ip;

}

