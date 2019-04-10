package com.lj.spring.support;

import com.lj.spring.config.MailProperties;
import com.lj.spring.core.MailSenderTemplate;
import com.lj.spring.core.impl.MailSenderTemplateImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by lijun on 2019/4/9
 */
@Configuration
@EnableConfigurationProperties(MailProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.mail.enable", havingValue = "true", matchIfMissing = true)
public class JavaMailSendSupport {

    private final MailProperties mailProperties;

    @Bean("simpleJavaMailSender")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //主机、端口
        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setPort(mailProperties.getPort());
        //用户名、密码
        javaMailSender.setUsername(mailProperties.getUsername());
        javaMailSender.setPassword(mailProperties.getPassword());
        //编码
        javaMailSender.setDefaultEncoding(mailProperties.getCharset().name());

        //额外配置信息
        Properties properties = new Properties();
        properties.putAll(mailProperties.getProperties());
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

    @Bean
    @ConditionalOnMissingBean(MailSenderTemplate.class)
    public MailSenderTemplate mailSenderTemplate(@Qualifier("simpleJavaMailSender") JavaMailSender javaMailSender) {
        return new MailSenderTemplateImpl(mailProperties, javaMailSender);
    }
}
