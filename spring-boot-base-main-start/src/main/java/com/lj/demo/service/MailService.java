package com.lj.demo.service;

/**
 * Created by junli on 2019-07-22
 */
public interface MailService {

    /**
     * 发送简单的邮件
     */
    void sendSimpleMail();

    /**
     * 发送模板邮件
     */
    void sendTemplateMail();

    /**
     * 发送带有附件的邮件
     */
    void sendAttachmentStreamMail();
}

