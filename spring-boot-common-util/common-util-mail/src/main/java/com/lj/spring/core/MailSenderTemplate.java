package com.lj.spring.core;

/**
 * Created by lijun on 2019/4/9
 */
public interface MailSenderTemplate {

    /**
     * 发送基础邮件
     *
     * @param to      目标用户
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);
}
