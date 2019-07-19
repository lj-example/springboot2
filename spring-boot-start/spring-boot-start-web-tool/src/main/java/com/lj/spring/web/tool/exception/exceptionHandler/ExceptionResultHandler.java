package com.lj.spring.web.tool.exception.exceptionHandler;

import com.lj.spring.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lijun on 2019/4/9
 */
public interface ExceptionResultHandler {

    /**
     * exception 后置处理器
     */
    Result handle(HttpServletRequest request, HandlerMethod handlerMethod, Exception exception, HttpStatus httpStatus, Result result);
}
