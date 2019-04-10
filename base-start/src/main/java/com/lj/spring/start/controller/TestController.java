package com.lj.spring.start.controller;

import com.lj.spring.common.exception.exceptionResolver.ResultCommon;
import com.lj.spring.core.MailSenderTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * Created by lijun on 2019/4/4
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class TestController {


    private final MailSenderTemplate javaMailSender;

    @GetMapping
    public Object test() throws MessagingException {


        javaMailSender.sendSimpleMail("156735229@qq.com","主题234","内容333");

        return ResultCommon.SUCCESS;
    }
}
