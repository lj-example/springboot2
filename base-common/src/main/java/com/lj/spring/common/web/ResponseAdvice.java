package com.lj.spring.common.web;


import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * Created by lijun on 2019/3/26
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
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
        if (Objects.nonNull(result) || result instanceof Result || result instanceof Throwable) {
            return result;
        }
        ResultSuccess resultSuccess = ResultSuccess.buildResult(result);
        return resultSuccess;
    }
}
