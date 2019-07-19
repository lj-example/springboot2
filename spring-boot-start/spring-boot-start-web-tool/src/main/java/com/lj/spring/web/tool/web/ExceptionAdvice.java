package com.lj.spring.web.tool.web;


import com.lj.spring.common.exception.BizException;
import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultCommon;
import com.lj.spring.web.tool.exception.exceptionHandler.ExceptionResultHandler;
import com.lj.spring.web.tool.exception.exceptionHandler.ExceptionResultHandlerConfiguration;
import com.lj.spring.web.tool.exception.exceptionResolver.ExceptionResolver;
import com.lj.spring.web.tool.exception.exceptionResolver.ExceptionResolverConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * spring 会根据声明的顺序匹配
 * 也可以继承：ResponseEntityExceptionHandler
 * Created by lijun on 2019/4/8
 */
@Configuration
@ConditionalOnMissingBean(ExceptionAdvice.class)
@Import({ExceptionResolverConfiguration.class, ExceptionResultHandlerConfiguration.class})
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice implements InitializingBean, Ordered {

    @Autowired(required = false)
    public List<ExceptionResolver> exceptionResolverList;

    @Autowired
    public ExceptionResultHandler exceptionResultHandler;

    /**
     * 业务异常信息
     */
    @ExceptionHandler(BizException.class)
    public Result bizExceptionHandler(HttpServletRequest request, HandlerMethod handlerMethod, Exception exception) {
        Result result = ((BizException) exception).getResultFail();
        return resolverAndHandle(request, handlerMethod, exception, HttpStatus.OK, result);
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(value = {ServletException.class, BindException.class,
            ValidationException.class, javax.validation.ValidationException.class,
            MethodArgumentNotValidException.class, HttpMessageConversionException.class,
            MethodArgumentTypeMismatchException.class})
    public Result invalidArgumentsHandler(HttpServletRequest request, HandlerMethod handlerMethod, Exception exception) {
        return resolverAndHandle(request, handlerMethod, exception, HttpStatus.BAD_REQUEST, ResultCommon.INVALID_ARGUMENTS);
    }

    /**
     * 其他异常
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, NoHandlerFoundException.class})
    public Result notFoundHandler(HttpServletRequest request, Exception exception) {
        return resolverAndHandle(request, null, exception, HttpStatus.NOT_FOUND, ResultCommon.RESOURCE_NOT_FOUND);
    }

    /**
     * 系统异常信息
     */
    @ExceptionHandler(Throwable.class)
    public Result exceptionHandler(HttpServletRequest request, HandlerMethod handlerMethod, Exception exception) {
        return resolverAndHandle(request, handlerMethod, exception, HttpStatus.INTERNAL_SERVER_ERROR, ResultCommon.INTERNAL_SERVER_ERROR);
    }


    @Override
    public void afterPropertiesSet() {
        AnnotationAwareOrderComparator.sort(exceptionResolverList);
    }

    /**
     * 异常信息处理及转换
     */
    private Result resolverAndHandle(HttpServletRequest request, HandlerMethod handlerMethod, Exception exception, HttpStatus httpStatus, Result result) {
        for (ExceptionResolver resolver : exceptionResolverList) {
            if (resolver.canResolve(request, exception, httpStatus)) {
                resolver.resolve(request, exception);
            }
        }
        return exceptionResultHandler.handle(request, handlerMethod, exception, httpStatus, result);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE - 100;
    }
}
