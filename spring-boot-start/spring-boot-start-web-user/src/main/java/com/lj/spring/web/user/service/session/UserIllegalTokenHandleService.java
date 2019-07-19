package com.lj.spring.web.user.service.session;

import com.lj.spring.web.user.model.UserSessionRedis;

/**
 * Created by junli on 2019-07-02
 */
public interface UserIllegalTokenHandleService {

    /**
     * 处理头部没有 token 信息
     */
    void assertAndHandleNoTokenHead();

    /**
     * 处理token信息不存在
     */
    void assertAndHandleNoTokenInfo();

    /**
     * 处理 token
     */
    void assertAndHandOldToken(UserSessionRedis oldUserSession);

}

