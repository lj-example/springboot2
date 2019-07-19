package com.lj.spring.web.user.service.session;

import com.lj.spring.web.user.model.UserSessionRedis;

/**
 * Created by junli on 2019-07-02
 */
public interface UserSessionService {

    /**
     * 通过token 获取用户id
     *
     * @param token
     * @return 用户ID
     */
    Long getUserIdByToken(String token);

    /**
     * 通过 token 获取缓存数据
     *
     * @param token
     * @return UserSessionRedis
     */
    UserSessionRedis getUserSessionRedisByToken(String token);

    /**
     * 通过手机号获取缓存数据
     *
     * @param mobile 手机号
     * @return UserSessionRedis
     */
    UserSessionRedis getUserSessionRedisByMobile(String mobile);

    /**
     * 处理正常的 缓存 token-key的 k-v
     */
    void handleNormalUserToken(String userToken);

    /**
     * 处理正常的 token
     */
    void handleNormalTokenCacheInfo(String token);

    /**
     * 处理 oldToken
     *
     * @param token oldToken
     */
    void handleOldToken(String token);
}

