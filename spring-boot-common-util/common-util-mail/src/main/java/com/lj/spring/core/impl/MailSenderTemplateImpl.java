package com.lj.spring.core.impl;

import com.lj.spring.config.MailProperties;
import com.lj.spring.core.MailSenderTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by lijun on 2019/4/9
 */
@RequiredArgsConstructor
public class MailSenderTemplateImpl implements MailSenderTemplate, InitializingBean {

    private final MailProperties mailProperties;
    private final JavaMailSender javaMailSender;

    private static ExecutorService SEND_POOL;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailProperties.getUsername());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        SEND_POOL.submit(() -> javaMailSender.send(simpleMailMessage));
    }

    @Override
    public void afterPropertiesSet() {
        SEND_POOL = Executors.newFixedThreadPool(mailProperties.getSendPoolSize(), (runnable) -> {
            final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
            Thread thread = defaultFactory.newThread(runnable);
            thread.setName("sendMailPool : " + thread.getName());
            return thread;
        });
    }
}
