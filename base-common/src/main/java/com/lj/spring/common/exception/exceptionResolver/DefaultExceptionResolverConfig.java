package com.lj.spring.common.exception.exceptionResolver;

import com.lj.spring.common.exception.exceptionHandler.DefaultExceptionResultHandler;
import com.lj.spring.common.exception.exceptionHandler.ExceptionResultHandler;
import com.lj.spring.common.web.ExceptionAdvice;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lijun on 2019/4/8
 */
@Configuration
@ConditionalOnBean(ExceptionAdvice.class)
@AutoConfigureAfter(ExceptionAdvice.class)
public class DefaultExceptionResolverConfig {

    @Bean
    public DefaultBizExceptionResolver defaultBizExceptionResolver() {
        return new DefaultBizExceptionResolver();
    }

    @Bean
    public DefaultRuntimeExceptionResolver defaultRuntimeExceptionResolver() {
        return new DefaultRuntimeExceptionResolver();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionResultHandler.class)
    public DefaultExceptionResultHandler defaultExceptionResultHandler() {
        return new DefaultExceptionResultHandler();
    }
}
