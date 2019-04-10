package com.lj.spring.core.impl;

import com.lj.spring.config.MailProperties;
import com.lj.spring.core.MailSenderTemplate;
import com.lj.spring.model.SimpleMailMessage;
import com.lj.spring.model.TemplateSimpleMailMessage;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lijun on 2019/4/9
 */
@RequiredArgsConstructor
@Slf4j
public class MailSenderTemplateImpl implements MailSenderTemplate, InitializingBean {

    private final MailProperties mailProperties;
    private final JavaMailSender javaMailSender;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    private static ExecutorService SEND_POOL;


    @Override
    public void sendSimpleMail(SimpleMailMessage simpleMailMessage) {
        validateMailMessageInfo(simpleMailMessage);
        Runnable task = () -> {
            final MimeMessage mimeMessage2 = buildMimeMessageByMailMessage(simpleMailMessage);
            javaMailSender.send(mimeMessage2);
        };
        SEND_POOL.submit(task);
    }

    @Override
    public void sendTemplateMail(TemplateSimpleMailMessage templateMailMessage) {
        validateTemplateMailMessageInfo(templateMailMessage);
        Runnable task = () -> {
            final MimeMessage mimeMessage = buildMimeMessageByMailMessage(templateMailMessage);
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                //处理模板名称后缀
                String templateName = templateMailMessage.getTemplateName();
                MailProperties.MailFreeMarkConfigurer freeMarkConfigurer = mailProperties.getFreeMark();
                if (!templateName.endsWith(freeMarkConfigurer.getSuffix())) {
                    templateName += freeMarkConfigurer.getSuffix();
                }
                Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName, freeMarkConfigurer.getCharset());
                String templateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMailMessage.getData());
                mimeMessageHelper.setText(templateIntoString, true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                log.info(buildFailMessage("获取模板文件失败！"));
                e.printStackTrace();
            } catch (IOException e) {
                log.info(buildFailMessage("获取模板文件失败！"));
                e.printStackTrace();
            } catch (TemplateException e) {
                log.info(buildFailMessage("模板转换失败！"));
                e.printStackTrace();
            }
        };
        SEND_POOL.submit(task);
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

    /**
     * 验证基础邮件信息
     */
    private static void validateMailMessageInfo(SimpleMailMessage simpleMailMessage) {
        Assert.isTrue(!StringUtils.isEmpty(simpleMailMessage.getSubject()), "邮件主题不能为空！");
        Assert.isTrue(!StringUtils.isEmpty(simpleMailMessage.getContent()), "邮件内容不能为空！");
        Assert.isTrue(!isEmptyCollection(simpleMailMessage.getToUserList()), "收件人不能为空！");
    }

    /**
     * 验证模板邮件信息
     */
    private static void validateTemplateMailMessageInfo(TemplateSimpleMailMessage templateMailMessage) {
        Assert.isTrue(!StringUtils.isEmpty(templateMailMessage.getSubject()), "邮件主题不能为空！");
        Assert.isTrue(!isEmptyCollection(templateMailMessage.getToUserList()), "收件人不能为空！");
        Assert.isTrue(!StringUtils.isEmpty(templateMailMessage.getTemplateName()), "模板名称不能为空！");
    }

    /**
     * 构建邮件发送
     *
     * @param simpleMailMessage
     * @return
     */
    private MimeMessage buildMimeMessageByMailMessage(SimpleMailMessage simpleMailMessage) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final String charset = mailProperties.getCharset().name();
        try {
            mimeMessage.setFrom(mailProperties.getUsername());
            mimeMessage.setSubject(simpleMailMessage.getSubject(), charset);
            mimeMessage.setText(simpleMailMessage.getContent(), charset);
            //设置邮件发送时间
            if (null != simpleMailMessage.getDate()) {
                mimeMessage.setSentDate(simpleMailMessage.getDate());
            }
        } catch (MessagingException e) {
            log.info(buildFailMessage("邮件基础信息构建失败！"));
            e.printStackTrace();
        }
        Function<List<String>, String> listToString = (list) -> list.stream().distinct().collect(Collectors.joining(","));
        try {
            String internetAddressTo = listToString.apply(simpleMailMessage.getToUserList());
            mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);
            if (!isEmptyCollection(simpleMailMessage.getCcUserList())) {
                //抄送
                final String internetAddressCC = listToString.apply(simpleMailMessage.getCcUserList());
                mimeMessage.setRecipients(Message.RecipientType.CC, internetAddressCC);
            }
            if (!isEmptyCollection(simpleMailMessage.getBccUserList())) {
                //秘密抄送
                final String internetAddressBCC = listToString.apply(simpleMailMessage.getBccUserList());
                mimeMessage.setRecipients(Message.RecipientType.BCC, internetAddressBCC);
            }
        } catch (MessagingException e) {
            log.info(buildFailMessage("设置目标地址信息出错！"));
            e.printStackTrace();
        }
        return mimeMessage;
    }

    /**
     * 判断集合是否为空
     */
    private static boolean isEmptyCollection(Collection collection) {
        return null == collection || collection.size() == 0;
    }

    /**
     * 错误信息
     */
    private static String buildFailMessage(String message) {
        return String.format(" 【邮件发送失败】: %s", message);
    }
}
