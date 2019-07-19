package com.lj.demo.spring.config.i18n;

import com.lj.spring.i18n.common.Common;
import com.lj.spring.i18n.core.I18nHandler.I18nResourceHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by junli on 2019-07-18
 */
@Component(value = Common.I18N_INTERCEPTOR_NAME)
public class I18nInterceptorConfiguration implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        I18nResourceHandler.setInfo(Common.DEFAULT_LOCALE);
        //此处必须返回true 保证后续继续可执行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        I18nResourceHandler.clean();
    }
}

