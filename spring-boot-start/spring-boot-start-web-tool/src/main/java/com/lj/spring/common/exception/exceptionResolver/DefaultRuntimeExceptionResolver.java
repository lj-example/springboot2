package com.lj.spring.common.exception.exceptionResolver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 5xx 类型处理
 * Created by lijun on 2019/4/8
 */
@Slf4j
public class DefaultRuntimeExceptionResolver implements ExceptionResolver, Ordered {

    @Override
    public void resolve(HttpServletRequest request, Exception exception) {
        log.info("exception = {}", ExceptionUtils.getStackTrace(exception));
    }

    @Override
    public boolean canResolve(HttpServletRequest request, Exception exception, HttpStatus httpStatus) {
        return httpStatus.is5xxServerError();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
