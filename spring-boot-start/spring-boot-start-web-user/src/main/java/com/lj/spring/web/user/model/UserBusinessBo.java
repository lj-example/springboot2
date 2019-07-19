package com.lj.spring.web.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by junli on 2019-07-02
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserBusinessBo {

    /**
     * userId
     */
    private Long userId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户 token
     */
    private String token;

}

