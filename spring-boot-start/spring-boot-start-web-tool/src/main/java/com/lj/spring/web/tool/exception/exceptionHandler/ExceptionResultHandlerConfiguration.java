package com.lj.spring.web.tool.exception.exceptionHandler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by junli on 2019-07-10
 */
@ConditionalOnMissingBean(ExceptionResultHandler.class)
public class ExceptionResultHandlerConfiguration {

    @Bean
    public ExceptionResultHandler defaultExceptionResultHandler() {
        return new DefaultExceptionResultHandler();
    }

}

