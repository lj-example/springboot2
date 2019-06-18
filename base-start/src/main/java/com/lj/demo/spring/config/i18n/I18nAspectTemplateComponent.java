package com.lj.demo.spring.config.i18n;

import com.lj.spring.i18n.core.I18nHandler.I18nAspectTemplate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by junli on 2019-06-14
 */
@Aspect
@Component
public class I18nAspectTemplateComponent extends I18nAspectTemplate {

    @Override
    @Pointcut("execution(* com.lj.demo.controller.*.*(..))")
    public void enablePath() { }

    @Override
    protected Locale getLocaleInfo() {
        return Locale.CHINA;
    }
}

