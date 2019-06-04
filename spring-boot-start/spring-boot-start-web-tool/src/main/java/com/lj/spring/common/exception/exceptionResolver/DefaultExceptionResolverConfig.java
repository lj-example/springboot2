package com.lj.spring.common.exception.exceptionResolver;


import com.lj.spring.common.exception.exceptionHandler.DefaultExceptionResultHandler;
import com.lj.spring.common.exception.exceptionHandler.ExceptionResultHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by lijun on 2019/4/8
 */
public class DefaultExceptionResolverConfig {

    @Bean
    @ConditionalOnMissingBean(ExceptionResultHandler.class)
    public DefaultExceptionResultHandler defaultExceptionResultHandler() {
        return new DefaultExceptionResultHandler();
    }

    @Bean
    public DefaultBizExceptionResolver defaultBizExceptionResolver() {
        return new DefaultBizExceptionResolver();
    }

}
