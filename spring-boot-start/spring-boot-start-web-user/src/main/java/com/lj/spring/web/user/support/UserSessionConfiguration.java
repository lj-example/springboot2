package com.lj.spring.web.user.support;


import com.lj.spring.web.user.core.redis.UserSimpleRedisTemplate;
import com.lj.spring.web.user.service.impl.session.UserIllegalTokenHandleDefaultServiceImpl;
import com.lj.spring.web.user.service.impl.session.UserSessionServiceImpl;
import com.lj.spring.web.user.service.session.UserIllegalTokenHandleService;
import com.lj.spring.web.user.service.session.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 注册 service
 * Created by junli on 2019-07-02
 */
@AutoConfigureAfter(UserSimpleRedisTemplate.class)
@RequiredArgsConstructor
public class UserSessionConfiguration {

    /**
     * 参数转换时候-异常处理
     */
    @Bean
    @ConditionalOnMissingBean(UserIllegalTokenHandleService.class)
    public UserIllegalTokenHandleService userIllegalTokenHandleService() {
        return new UserIllegalTokenHandleDefaultServiceImpl();
    }

    /**
     * userSession接口
     */
    @Bean
    @ConditionalOnMissingBean(UserSessionService.class)
    public UserSessionService userSessionService(UserSimpleRedisTemplate userSimpleRedisTemplate) {
        return new UserSessionServiceImpl(userSimpleRedisTemplate);
    }

}

