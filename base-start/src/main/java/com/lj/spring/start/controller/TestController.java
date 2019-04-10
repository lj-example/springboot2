package com.lj.spring.start.controller;

import com.google.common.collect.Lists;
import com.lj.spring.common.exception.exceptionResolver.ResultCommon;
import com.lj.spring.core.MailSenderTemplate;
import com.lj.spring.model.SimpleMailMessage;
import com.lj.spring.model.TemplateSimpleMailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;

/**
 * Created by lijun on 2019/4/4
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class TestController {

    private final MailSenderTemplate mailSenderTemplate;

    @GetMapping
    public Object test() throws MessagingException {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("测试主题-1");
        simpleMailMessage.setContent("测试内容-1");
        simpleMailMessage.setToUserList(Lists.newArrayList("156735229@qq.com", "lijun02@hexindai.com"));
        mailSenderTemplate.sendSimpleMail(simpleMailMessage);

        final TemplateSimpleMailMessage templateSimpleMailMessage = new TemplateSimpleMailMessage();
        templateSimpleMailMessage.setSubject("模板-测试主题-2");
        templateSimpleMailMessage.setContent("模板-测试内容-2");
        templateSimpleMailMessage.setToUserList(Lists.newArrayList("156735229@qq.com", "lijun02@hexindai.com"));
        final HashMap<String, Object> map = new HashMap<>();
        map.put("name","测试名称-2");
        templateSimpleMailMessage.setData(map);
        templateSimpleMailMessage.setTemplateName("message");

        mailSenderTemplate.sendTemplateMail(templateSimpleMailMessage);

        return ResultCommon.SUCCESS;
    }
}
