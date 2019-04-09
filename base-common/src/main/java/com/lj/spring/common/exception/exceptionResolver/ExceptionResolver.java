package com.lj.spring.common.exception.exceptionResolver;

import com.lj.spring.common.webCommon.HttpStatusUtil;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lijun on 2019/4/8
 */
public interface ExceptionResolver {

    /**
     * 自定义异常处理
     */
    void resolve(HttpServletRequest request, Exception exception);

    /**
     * 是否可以被处理
     */
    boolean canResolve(HttpServletRequest request, Exception exception,HttpStatus httpStatus);

    /**
     * 获取请求状态码
     */
    default HttpStatus status(HttpServletRequest request) {
        return HttpStatusUtil.getStatus(request);
    }
}
