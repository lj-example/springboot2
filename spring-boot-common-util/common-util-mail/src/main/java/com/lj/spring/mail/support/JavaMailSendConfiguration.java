package com.lj.spring.mail.support;

import com.lj.spring.mail.config.MailProperties;
import com.lj.spring.mail.core.impl.MailSenderTemplateImpl;
import com.lj.spring.mail.core.MailSenderTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

/**
 * Created by lijun on 2019/4/9
 */
@Configuration
@EnableConfigurationProperties(MailProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.mail.enable", havingValue = "true", matchIfMissing = true)
public class JavaMailSendConfiguration {

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
        //邮件附件名字太长是否会被截断
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        return javaMailSender;
    }

    @Bean("javaMailFreeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        MailProperties.MailFreeMarkConfigurer freeMark = mailProperties.getFreeMark();
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath(freeMark.getTemplateLoaderPath());
        freeMarkerConfigurer.setDefaultEncoding(freeMark.getCharset());
        return freeMarkerConfigurer;
    }

    @Bean
    @ConditionalOnMissingBean(MailSenderTemplate.class)
    public MailSenderTemplate mailSenderTemplate(
            @Qualifier("simpleJavaMailSender") JavaMailSender javaMailSender,
            @Qualifier("javaMailFreeMarkerConfigurer") FreeMarkerConfigurer freeMarkerConfigurer) {
        return new MailSenderTemplateImpl(mailProperties, javaMailSender, freeMarkerConfigurer);
    }
}
