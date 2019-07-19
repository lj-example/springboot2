package com.lj.demo.spring.config.i18n;

import com.lj.spring.i18n.core.I18nHandler.AbstractI18nAspectTemplate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by junli on 2019-06-14
 */
@Aspect
@Component
public class I18nAspectTemplateComponent extends AbstractI18nAspectTemplate {

    @Override
    @Pointcut("execution(* com.lj.demo.controller.*.*(..))")
    public void enablePath() { }

}

