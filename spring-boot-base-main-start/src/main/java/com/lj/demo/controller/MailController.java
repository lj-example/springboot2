package com.lj.demo.controller;

import com.google.common.collect.Lists;
import com.lj.demo.api.MailApi;
import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultSuccess;
import com.lj.spring.mail.core.MailSenderTemplate;
import com.lj.spring.mail.model.AttachmentStreamMailMessage;
import com.lj.spring.util.base.poi.ExcelField;
import com.lj.spring.util.base.poi.ExcelUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by junli on 2019-09-25
 */
@Slf4j
@RestController
@RequestMapping("mail")
@RequiredArgsConstructor
public class MailController implements MailApi {

    private final MailSenderTemplate mailSenderTemplate;

    @Override
    @PutMapping
    public Result sendMail() {
        AttachmentStreamMailMessage mailMessage = new AttachmentStreamMailMessage();
        mailMessage.setToUserList(Lists.newArrayList("156735229@qq.com"));
        mailMessage.setSubject("测试邮件");
        mailMessage.setContent("测试邮件内容");
        ExcelUtil<MailBean> mailBeanExcelUtil = new ExcelUtil<>(MailBean.class);

        ArrayList<MailBean> mailBeanArrayList = Lists.newArrayList(new MailBean(1, "A"), new MailBean(2, "B"));

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024)) {
            mailBeanExcelUtil.exportExcel(mailBeanArrayList, "sheet1", byteArrayOutputStream);
            InputStreamSource inputStreamSource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
            //构建邮件发送需要的文件流
            AttachmentStreamMailMessage.AttachmentStream attachmentStream = AttachmentStreamMailMessage.AttachmentStream.builder()
                    .fileName("逾期名单.xls")
                    .inputStreamSource(inputStreamSource)
                    .build();
            mailMessage.setAttachmentStreamList(Lists.newArrayList(attachmentStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mailSenderTemplate.sendAttachmentStreamMail(mailMessage);

        return ResultSuccess.defaultResultSuccess();
    }

    @AllArgsConstructor
    @Getter
    static class MailBean {
        /**
         * 序号
         */
        @ExcelField(name = "标号",column = "A")
        private Integer index;

        /**
         * 值
         */
        @ExcelField(name = "值",column = "B")
        private String value;
    }
}
