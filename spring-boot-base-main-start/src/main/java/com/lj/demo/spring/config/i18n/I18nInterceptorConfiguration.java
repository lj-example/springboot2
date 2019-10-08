package com.lj.demo.spring.config.i18n;

import com.lj.spring.i18n.common.Common;
import com.lj.spring.i18n.config.I18nProperties;
import com.lj.spring.i18n.core.I18nHandler.I18nResourceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by junli on 2019-07-18
 */
@Configuration(value = Common.I18N_INTERCEPTOR_NAME)
@ConditionalOnMissingBean(name = Common.I18N_INTERCEPTOR_NAME)
@RequiredArgsConstructor
public class I18nInterceptorConfiguration implements HandlerInterceptor {

    private final I18nProperties i18nProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String headLanguage = request.getHeader(i18nProperties.getHeadKey());
        if (StringUtils.isEmpty(headLanguage)) {
            I18nResourceHandler.setInfo(i18nProperties.getDefaultLocale());
        } else {
            String[] localeInfo = headLanguage.split("_");
            I18nResourceHandler.setInfo(new Locale(localeInfo[0], localeInfo.length > 1 ? localeInfo[1] : ""));
        }
        //此处必须返回true 保证后续继续可执行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        I18nResourceHandler.clean();
    }
}

