package com.lj.spring.web.tool.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * Created by junli on 2019-07-09
 */
@Configuration
@ConditionalOnMissingBean(ResponseBodyAdvice.class)
@RestControllerAdvice
public class ResponseAdvice extends AbstractResponseAdviceTemplate {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        //匹配所有
        return true;
    }

    @Override
    public List<String> supportPath() {
        return null;
    }

}

