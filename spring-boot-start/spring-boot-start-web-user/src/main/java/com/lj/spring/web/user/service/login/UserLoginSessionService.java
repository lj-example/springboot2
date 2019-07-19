package com.lj.spring.web.user.service.login;

import com.lj.spring.web.user.model.UserBusinessBo;

/**
 * Created by junli on 2019-07-02
 */
public interface UserLoginSessionService {

    /**
     * 登录成功后 设置token信息
     *
     * @param userBusinessBo 待存储信息 - 用户ID/手机号 不能为 null
     * @return token 信息，操作失败返回 ""
     */
    String buildUserTokenAfterLogin(UserBusinessBo userBusinessBo);

    /**
     * 登出
     *
     * @param token 用户token
     */
    void cleanUserTokenAfterLoginOut(String token);
}

