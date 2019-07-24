package com.lj.spring.web.user.support;

import com.lj.spring.web.user.config.UserRedisProperties;
import com.lj.spring.web.user.core.redis.UserSimpleRedisTemplate;
import com.lj.spring.web.user.service.impl.login.UserLoginSessionServiceImpl;
import com.lj.spring.web.user.service.login.UserLoginSessionService;
import com.lj.spring.web.user.service.session.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Created by junli on 2019-07-03
 */
@AutoConfigureAfter(UserSimpleRedisTemplate.class)
@EnableConfigurationProperties(UserRedisProperties.class)
@RequiredArgsConstructor
public class UserTemplateConfiguration {

    /**
     * 登录时 user-session 操作
     */
    @Bean
    @ConditionalOnMissingBean(UserLoginSessionService.class)
    public UserLoginSessionService userLoginSessionService(
            UserSimpleRedisTemplate userSimpleRedisTemplate,
            UserSessionService userSessionService,
            UserRedisProperties userRedisProperties) {
        return new UserLoginSessionServiceImpl(userSimpleRedisTemplate, userSessionService, userRedisProperties);
    }

}

