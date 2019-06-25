package com.lj.spring.i18n.core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Objects;

/**
 * Created by junli on 2019-06-17
 */
@RequiredArgsConstructor
public final class DefaultI18nSource {

    public DefaultI18nSource(MessageSource messageSource) {

    }

    private MessageSource messageSource;

    public static MessageSource getMessageSource() {
        return InnerClass.InnerClass.messageSource;
    }

    /**
     * 用以存储 messageSource
     */
    private static class InnerClass {
        private static DefaultI18nSource InnerClass = new DefaultI18nSource();
    }

    /**
     * 初始化 messageSource 方便被后续调用
     */
    public static void initDefaultI18nSource(MessageSource messageSource) {
        synchronized (messageSource) {
            MessageSource value = InnerClass.InnerClass.messageSource;
            //避免重复注册
            if (Objects.isNull(value)) {
                InnerClass.InnerClass.messageSource = messageSource;
            }
        }
    }
}

