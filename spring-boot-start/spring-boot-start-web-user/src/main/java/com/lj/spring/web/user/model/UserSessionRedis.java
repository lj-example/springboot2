package com.lj.spring.web.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by junli on 2019-07-02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSessionRedis implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 设备更新 token 时间
     */
    private String deviceSignTime;

    /**
     * 登录时间
     */
    private String createTime;

    /**
     * 是否为旧token,是为 1
     */
    private Boolean oldToken;

    /**
     * 新设备登录时间
     */
    private String newDeviceLoginTime;

}

