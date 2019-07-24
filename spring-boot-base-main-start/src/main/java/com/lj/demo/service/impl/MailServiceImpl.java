package com.lj.demo.service.impl;

import com.google.common.collect.Lists;
import com.lj.demo.entity.bo.MailBo;
import com.lj.demo.service.MailService;
import com.lj.spring.mail.core.MailSenderTemplate;
import com.lj.spring.mail.model.AttachmentStreamMailMessage;
import com.lj.spring.mail.model.SimpleMailMessage;
import com.lj.spring.mail.model.TemplateSimpleMailMessage;
import com.lj.spring.util.base.collection.HashMapBuilder;
import com.lj.spring.util.base.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by junli on 2019-07-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @Autowired
    private final MailSenderTemplate mailSenderTemplate;

    /**
     * 发送简单的邮件
     */
    @Override
    public void sendSimpleMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setToUserList(Lists.newArrayList("156735229@qq.com"));
        simpleMailMessage.setContent("内容");
        simpleMailMessage.setSubject("主题");
        mailSenderTemplate.sendSimpleMail(simpleMailMessage);
    }

    /**
     * 发送模板邮件
     */
    @Override
    public void sendTemplateMail() {
        TemplateSimpleMailMessage templateSimpleMailMessage = new TemplateSimpleMailMessage();
        templateSimpleMailMessage.setToUserList(Lists.newArrayList("156735229@qq.com"));
        templateSimpleMailMessage.setContent("内容");
        templateSimpleMailMessage.setSubject("主题");
        templateSimpleMailMessage.setTemplateName("message.ftl");
        HashMap<String, Object> data = HashMapBuilder.<String, Object>newBuilder()
                .put("username", "示例名称")
                .build();
        templateSimpleMailMessage.setData(data);
        mailSenderTemplate.sendTemplateMail(templateSimpleMailMessage);
    }

    /**
     * 发送带有附件的邮件
     */
    @Override
    public void sendAttachmentStreamMail() {
        AttachmentStreamMailMessage streamMailMessage = new AttachmentStreamMailMessage();
        streamMailMessage.setToUserList(Lists.newArrayList("156735229@qq.com"));
        streamMailMessage.setContent("内容");
        streamMailMessage.setSubject("主题");
        //构建文件流
        ArrayList<MailBo> mailBoArrayList = Lists.newArrayList(
                new MailBo("name-1", 1),
                new MailBo("name-2", 2));
        ExcelUtil<MailBo> mailBoExcelUtil = new ExcelUtil<>(MailBo.class);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024)) {
            mailBoExcelUtil.exportExcel(mailBoArrayList, "sheetName", byteArrayOutputStream);
            InputStreamSource inputStreamSource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
            //构建邮件发送需要的文件流
            AttachmentStreamMailMessage.AttachmentStream attachmentStream = AttachmentStreamMailMessage.AttachmentStream.builder()
                    .fileName("附件名称.xls")
                    .inputStreamSource(inputStreamSource)
                    .build();
            streamMailMessage.setAttachmentStreamList(Lists.newArrayList(attachmentStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mailSenderTemplate.sendAttachmentStreamMail(streamMailMessage);
    }
}

