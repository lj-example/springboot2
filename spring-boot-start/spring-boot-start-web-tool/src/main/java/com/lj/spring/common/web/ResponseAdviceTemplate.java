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
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by lijun on 2019/3/26
 */
@Slf4j
public abstract class ResponseAdviceTemplate implements ResponseBodyAdvice, Ordered {

    /**
     * 增强器前置条件
     * 此处可以使用注解过滤
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        String name = methodParameter.getMethod().getDeclaringClass().getName();
        return supportPath().stream().anyMatch((path) -> name.startsWith(path));
    }

    @Override
    public Object beforeBodyWrite(Object result, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (Objects.isNull(result) || result instanceof Result || result instanceof Throwable) {
            return result;
        }
        ResultSuccess resultSuccess = ResultSuccess.of(result);
        if (result instanceof CharSequence) {
            return resultSuccess.toString();
        }
        return resultSuccess;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    public abstract List<String> supportPath();
}
