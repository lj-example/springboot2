package com.lj.spring.web.user.support;

import com.lj.spring.web.user.config.UserRedisProperties;
import com.lj.spring.web.user.core.argumentResolver.TokenMethodArgumentResolver;
import com.lj.spring.web.user.core.redis.UserSimpleRedisTemplate;
import com.lj.spring.web.user.service.session.UserIllegalTokenHandleService;
import com.lj.spring.web.user.service.session.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by junli on 2019-07-01
 */
@Configuration
@EnableConfigurationProperties(UserRedisProperties.class)
@Import({UserSessionConfiguration.class, UserTemplateConfiguration.class})
public class UserTokenAutoConfiguration {

    /**
     * 生成一个 redisTemplate
     */
    @Bean
    public UserSimpleRedisTemplate userSimpleRedisTemplate(UserRedisProperties userRedisProperties) {
        return new UserSimpleRedisTemplate(userRedisProperties);
    }

    /**
     * 注册参数解析器
     */
    @Bean
    @ConditionalOnClass(RequestHeaderMethodArgumentResolver.class)
    public TokenMethodArgumentResolver tokenMethodArgumentResolver(
            ConfigurableBeanFactory beanFactory,
            UserSessionService userSessionService,
            UserIllegalTokenHandleService userIllegalTokenHandleService) {
        return new TokenMethodArgumentResolver(beanFactory, userSessionService, userIllegalTokenHandleService);
    }

    /**
     * 添加参数解析器
     */
    @Configuration
    @ConditionalOnWebApplication
    @RequiredArgsConstructor
    public static class TokenMvcConfig implements WebMvcConfigurer {

        private final TokenMethodArgumentResolver tokenMethodArgumentResolver;

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(tokenMethodArgumentResolver);
        }
    }

}

