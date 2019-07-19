package com.lj.spring.web.tool.exception.exceptionResolver;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by lijun on 2019/4/8
 */
@ConditionalOnMissingBean(ExceptionResolver.class)
public class ExceptionResolverConfiguration {

    @Bean
    public DefaultRuntimeExceptionResolver defaultRuntimeExceptionResolver() {
        return new DefaultRuntimeExceptionResolver();
    }

    @Bean
    public DefaultBizExceptionResolver defaultBizExceptionResolver() {
        return new DefaultBizExceptionResolver();
    }

}
