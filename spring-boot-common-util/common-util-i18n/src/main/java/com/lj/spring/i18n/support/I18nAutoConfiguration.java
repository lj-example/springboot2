package com.lj.spring.i18n.support;

import com.lj.spring.i18n.config.I18nProperties;
import com.lj.spring.i18n.core.I18nResourceBundleMessageSource;
import com.lj.spring.i18n.core.util.DefaultI18nSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lj.spring.i18n.support.I18nAutoConfiguration.NAME;
import static com.lj.spring.i18n.support.I18nAutoConfiguration.NAME_DEFAULT_VALUE;
import static org.springframework.context.support.AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME;

/**
 * Created by junli on 2019-06-14
 */
@Configuration
@EnableConfigurationProperties(I18nProperties.class)
@ConditionalOnProperty(name = NAME, havingValue = NAME_DEFAULT_VALUE, matchIfMissing = true)
@RequiredArgsConstructor
public class I18nAutoConfiguration {

    private final I18nProperties i18nProperties;

    /**
     * 开启路径
     */
    static final String NAME = "spring.i18n";

    /**
     * 默认开启该配置对应的名称 - 值 如果不配置 则认为开启
     */
    static final String NAME_DEFAULT_VALUE = "enable";

    /**
     * 注册一个 messageSource bean 替换官方 messageSource
     */
    @ConditionalOnMissingBean(MessageSource.class)
    @Bean(MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource i18nResourceBundleMessageSource() {
        MessageSource messageSource = I18nResourceBundleMessageSource.from(i18nProperties);
        DefaultI18nSource.InitDefaultI18nSource(messageSource);
        return messageSource;
    }

}

