package com.lj.spring.i18n.core.util;

import com.lj.spring.i18n.core.I18nHandler.I18nResourceHandler;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Locale;

/**
 * Created by junli on 2019-06-17
 */
public interface UtilInterface {

    /**
     * 根据code获取对应的value信息
     */
    default String getMessage(String code) {
        return getMessage(code, getLocaleAndAssertNull());
    }

    /**
     * 根据code获取对应的value信息
     */
    default String getMessage(String code, Locale locale){
        return DefaultI18nSource.getMessageSource().getMessage(code, null, locale);
    }

    /**
     * 根据code 获取对应的value信息
     */
    default String getMessage(String code, @Nullable Object[] args) {
        return getMessage(code, args, getLocaleAndAssertNull());
    }

    /**
     * 根据code获取对应的value信息
     */
    default String getMessage(String code, @Nullable Object[] args, Locale locale){
        return DefaultI18nSource.getMessageSource().getMessage(code, args, locale);
    }

    /**
     * 获取本地local缓存的 messageSource
     */
    static Locale getLocaleAndAssertNull() {
        Locale locale = I18nResourceHandler.getLocale();
        Assert.notNull(locale, "i18n Locale is null");
        return locale;
    }

}
