package com.lj.spring.i18n.core.I18nHandler;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by junli on 2019-07-18
 */
@Aspect
@Configuration
@ConditionalOnMissingBean(AbstractI18nAspectTemplate.class)
public class I18nAspectTemplateConfiguration extends AbstractI18nAspectTemplate {

    @Override
    @Pointcut("@annotation(com.lj.spring.i18n.core.I18nHandler.I18nFolderName)")
    public void enablePath() {

    }

}

