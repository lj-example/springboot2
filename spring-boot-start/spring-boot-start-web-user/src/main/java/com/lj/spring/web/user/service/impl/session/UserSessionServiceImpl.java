package com.lj.spring.web.user.service.impl.session;

import com.lj.spring.web.user.common.Common;
import com.lj.spring.web.user.config.UserRedisProperties;
import com.lj.spring.web.user.core.redis.UserSimpleRedisTemplate;
import com.lj.spring.web.user.model.UserSessionRedis;
import com.lj.spring.web.user.service.session.UserSessionService;
import com.lj.spring.web.user.util.UserSessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * Created by junli on 2019-07-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSimpleRedisTemplate userSimpleRedisTemplate;

    private final UserRedisProperties userRedisProperties;

    @Override
    public Long getUserIdByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String userId = userSimpleRedisTemplate.hGet(token, Common.HashKey.USER_ID.name());
        if (StringUtils.isBlank(userId)) {
            return Long.valueOf(userId);
        }
        return null;
    }

    @Override
    public UserSessionRedis getUserSessionRedisByToken(String token) {
        Map<String, String> map = userSimpleRedisTemplate.hGetAll(token);
        if (Objects.nonNull(map)) {
            return UserSessionRedis.builder()
                    .userId(map.get(Common.HashKey.USER_ID.name()))
                    .mobile(map.get(Common.HashKey.MOBILE.name()))
                    .createTime(map.get(Common.HashKey.CREATE_TIME.name()))
                    .deviceSignTime(map.get(Common.HashKey.DEVICE_SIGN_TIME.name()))
                    .oldToken(Common.IS_OLD_TOKEN.equals(map.get(Common.HashKey.OLD_TOKEN.name())))
                    .newDeviceLoginTime(Objects.isNull(map.get(Common.HashKey.NEW_DEVICE_LOGIN_TIME.name()))
                            ? StringUtils.EMPTY
                            : map.get(Common.HashKey.NEW_DEVICE_LOGIN_TIME.name()))
                    .build();
        }
        return null;
    }

    @Override
    public UserSessionRedis getUserSessionRedisByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        String tokenKey = userSimpleRedisTemplate.get(UserSessionUtil.buildUserTokenKey(mobile));
        if (StringUtils.isBlank(tokenKey)) {
            return null;
        }
        return getUserSessionRedisByToken(tokenKey);
    }

    @Override
    public void handleNormalUserToken(String userToken) {
        userSimpleRedisTemplate.expire(userToken, userRedisProperties.getTokenExpireSecond());
    }

    @Override
    public void handleNormalTokenCacheInfo(String token) {
        if (StringUtils.isNoneBlank(token)) {
            userSimpleRedisTemplate.hSet(token, Common.HashKey.DEVICE_SIGN_TIME.name(), UserSessionUtil.getNowDateStr());
            userSimpleRedisTemplate.expire(token, userRedisProperties.getTokenExpireSecond());
        }
    }

    @Override
    public void handleOldToken(String token) {
        if (StringUtils.isNoneBlank(token)) {
            userSimpleRedisTemplate.expire(token, userRedisProperties.getOldTokenExpireSecond());
        }
    }
}

