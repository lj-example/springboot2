package com.lj.spring.web.user.service.impl.session;

import com.lj.spring.web.user.exception.TokenBizException;
import com.lj.spring.web.user.model.UserSessionRedis;
import com.lj.spring.web.user.service.session.UserIllegalTokenHandleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by junli on 2019-07-02
 */
@Slf4j
@RequiredArgsConstructor
public class UserIllegalTokenHandleDefaultServiceImpl implements UserIllegalTokenHandleService {

    /**
     * Head 头中没有 token 信息
     */
    @Override
    public void assertAndHandleNoTokenHead() {
        throw new TokenBizException("head miss token info");
    }

    /**
     * 当前 token 不存在
     */
    @Override
    public void assertAndHandleNoTokenInfo() {
        throw new TokenBizException("Token information is lost");
    }

    /**
     * 当前 token 已经过期
     *
     * @param oldUserSession 过期用户信息
     */
    @Override
    public void assertAndHandOldToken(UserSessionRedis oldUserSession) {
        throw new TokenBizException("Your account is logged in elsewhere");
    }
}
