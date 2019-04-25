package com.lj.spring.core;

import com.lj.spring.model.AttachmentMailMessage;
import com.lj.spring.model.AttachmentStreamMailMessage;
import com.lj.spring.model.SimpleMailMessage;
import com.lj.spring.model.TemplateSimpleMailMessage;

/**
 * Created by lijun on 2019/4/9
 */
public interface MailSenderTemplate {

    /**
     * 发送基础邮件
     */
    void sendSimpleMail(SimpleMailMessage simpleMailMessage);

    /**
     * 发送模板邮件
     */
    void sendTemplateMail(TemplateSimpleMailMessage templateMailMessage);


    /**
     * 发送带有附件的邮件
     */
    void sendAttachmentMail(AttachmentMailMessage attachmentMailMessage);

    /**
     * 发送带有附件的邮件，附件为文件流的形式
     */
    void sendAttachmentStreamMail(AttachmentStreamMailMessage attachmentStreamMailMessage);
}
