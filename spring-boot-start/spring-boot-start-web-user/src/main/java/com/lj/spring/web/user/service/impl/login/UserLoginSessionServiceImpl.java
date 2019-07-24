package com.lj.spring.web.user.service.impl.login;

import com.lj.spring.web.user.common.Common;
import com.lj.spring.web.user.config.UserRedisProperties;
import com.lj.spring.web.user.core.redis.UserSimpleRedisTemplate;
import com.lj.spring.web.user.model.UserBusinessBo;
import com.lj.spring.web.user.service.login.UserLoginSessionService;
import com.lj.spring.web.user.service.session.UserSessionService;
import com.lj.spring.web.user.util.UserSessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by junli on 2019-07-02
 */
@Slf4j
@RequiredArgsConstructor
public class UserLoginSessionServiceImpl implements UserLoginSessionService {

    private final UserSimpleRedisTemplate userSimpleRedisTemplate;
    private final UserSessionService userSessionService;

    private final UserRedisProperties userRedisProperties;

    @Override
    public String buildUserTokenAfterLogin(UserBusinessBo userBusinessBo) {
        //校验参数
        if (Objects.isNull(userBusinessBo)
                || Objects.isNull(userBusinessBo.getUserId())
                || StringUtils.isBlank(userBusinessBo.getMobile())) {
            return StringUtils.EMPTY;
        }
        String userOldToken = userSimpleRedisTemplate.get(UserSessionUtil.buildUserTokenKey(userBusinessBo.getMobile()));
        final String newToken = buildNewCacheInfo(userBusinessBo);
        //先生成新 token 再处理旧Token
        if (StringUtils.isNoneBlank(userOldToken)) {
            //处理 oldToken
            userSimpleRedisTemplate.hSet(userOldToken, Common.HashKey.OLD_TOKEN.name(), Common.IS_OLD_TOKEN);
            userSimpleRedisTemplate.hSet(userOldToken, Common.HashKey.NEW_DEVICE_LOGIN_TIME.name(), UserSessionUtil.getNowDateStr());
            userSessionService.handleOldToken(userOldToken);
        }
        return newToken;
    }

    /**
     * 构建新的缓存
     *
     * @return token
     */
    private String buildNewCacheInfo(UserBusinessBo userBusinessBo) {
        if (Objects.isNull(userBusinessBo)
                || Objects.isNull(userBusinessBo.getUserId())
                || StringUtils.isBlank(userBusinessBo.getMobile())) {
            return StringUtils.EMPTY;
        }
        //处理token的 key-value
        String userTokenKey = UserSessionUtil.buildUserTokenKey(userBusinessBo.getMobile());
        String token = UserSessionUtil.uuIdToken();
        userSimpleRedisTemplate.set(userTokenKey, token);
        userSimpleRedisTemplate.expire(userTokenKey, userRedisProperties.getTokenExpireSecond());
        //处理hash
        HashMap<String, String> map = new HashMap<>(16);
        String nowDateStr = UserSessionUtil.getNowDateStr();
        map.put(Common.HashKey.USER_ID.name(), String.valueOf(userBusinessBo.getUserId()));
        map.put(Common.HashKey.MOBILE.name(), userBusinessBo.getMobile());
        map.put(Common.HashKey.CREATE_TIME.name(), nowDateStr);
        map.put(Common.HashKey.DEVICE_SIGN_TIME.name(), nowDateStr);
        map.put(Common.HashKey.OLD_TOKEN.name(), Common.NO_OLD_TOKEN);
        map.put(Common.HashKey.NEW_DEVICE_LOGIN_TIME.name(), StringUtils.EMPTY);
        //设置缓存
        userSimpleRedisTemplate.hPutAll(token, map);
        userSimpleRedisTemplate.expire(token, userRedisProperties.getTokenExpireSecond());
        return token;
    }

    @Override
    public void cleanUserTokenAfterLoginOut(String token) {
        if (StringUtils.isNoneBlank(token)) {
            String mobile = userSimpleRedisTemplate.hGet(token, Common.HashKey.MOBILE.name());
            String userTokenKey = UserSessionUtil.buildUserTokenKey(mobile);
            userSimpleRedisTemplate.del(userTokenKey);
            userSimpleRedisTemplate.del(token);
        }
    }
}

