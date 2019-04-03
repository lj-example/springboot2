package com.lj.spring.start.web;

import com.lj.spring.start.common.result.SuccessResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by lijun on 2019/3/26
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {

    /**
     * 增强器前置条件
     * 此处可以使用注解过滤
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object result, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        SuccessResult successResult = SuccessResult.buildResult(result);
        return new MappingJacksonValue(successResult);
    }
}
