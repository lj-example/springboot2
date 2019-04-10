package com.lj.spring.common.exception.exceptionResolver;

import com.lj.spring.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 业务异常处理
 * Created by lijun on 2019/4/8
 */
@Slf4j
public class DefaultBizExceptionResolver implements ExceptionResolver, Ordered {

    @Override
    public void resolve(HttpServletRequest request, Exception exception) {
        log.info("DefaultBizExceptionResolver = {}", exception.getMessage());
    }

    @Override
    public boolean canResolve(HttpServletRequest request, Exception exception, HttpStatus httpStatus) {
        return exception instanceof BizException;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE - 100;
    }
}
