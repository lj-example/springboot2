package com.lj.spring.web.tool.webUtil;

import org.springframework.http.HttpStatus;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lijun on 2019/4/8
 */
public final class HttpStatusUtil {

    /**
     * 获取请求状态码
     */
    public static HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
