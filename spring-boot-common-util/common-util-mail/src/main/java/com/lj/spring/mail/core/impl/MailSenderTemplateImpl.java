package com.lj.spring.mail.core.impl;

import com.lj.spring.mail.config.MailProperties;
import com.lj.spring.mail.core.MailSenderTemplate;
import com.lj.spring.mail.model.AttachmentMailMessage;
import com.lj.spring.mail.model.AttachmentStreamMailMessage;
import com.lj.spring.mail.model.SimpleMailMessage;
import com.lj.spring.mail.model.TemplateSimpleMailMessage;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Cleanup;
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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.BiFunction;
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
            try {
                final MimeMessage mimeMessage = buildMimeMessageByMailMessage(simpleMailMessage);
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                log.info(buildFailMessage("未知原因发送失败！"));
                e.printStackTrace();
            }
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
                Template template = freeMarkerConfigurer.getConfiguration()
                        .getTemplate(templateName, freeMarkConfigurer.getCharset());
                String templateIntoString = FreeMarkerTemplateUtils
                        .processTemplateIntoString(template, templateMailMessage.getData());
                mimeMessageHelper.setText(templateIntoString, true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException | IOException e) {
                log.info(buildFailMessage("获取模板文件失败！"));
                e.printStackTrace();
            } catch (TemplateException e) {
                log.info(buildFailMessage("模板转换失败！"));
                e.printStackTrace();
            } catch (Exception e) {
                log.info(buildFailMessage("未知原因发送失败！"));
                e.printStackTrace();
            }
        };
        SEND_POOL.submit(task);
    }

    @Override
    public void sendAttachmentMail(AttachmentMailMessage attachmentMailMessage) {
        validateAttachmentMailMessageInfo(attachmentMailMessage);
        //获取文件的最终 文件名，拼接 后缀
        BiFunction<String, String, String> getFileName = (fileName, filePath) -> {
            String prefix = filePath.substring(filePath.lastIndexOf("."));
            if (!fileName.endsWith(prefix)) {
                fileName = fileName + prefix;
            }
            try {
                fileName = MimeUtility.encodeText(fileName, mailProperties.getCharset().name(), "B");
            } catch (UnsupportedEncodingException e) {
                log.info(buildFailMessage("附件名称格式化错误！"));
                e.printStackTrace();
            }
            return fileName;
        };
        Runnable task = () -> {
            final MimeMessage mimeMessage = buildMimeMessageByMailMessage(attachmentMailMessage);
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setText(attachmentMailMessage.getContent(), true);
                for (AttachmentMailMessage.Attachment attachment : attachmentMailMessage.getAttachmentList()) {
                    String filName = attachment.getFileName();
                    String filePath = attachment.getFilePath();
                    final File file = new File(filePath);
                    mimeMessageHelper.addAttachment(getFileName.apply(filName, file.getAbsolutePath()), file);
                }
                javaMailSender.send(mimeMessage);
                attachmentMailMessage.getAttachmentList().stream()
                        .filter(AttachmentMailMessage.Attachment::getIsDelete)
                        .forEach(attachment ->
                                new File(attachment.getFilePath()).delete()
                        );
            } catch (MessagingException e) {
                log.info(buildFailMessage("邮件附件初始化失败！"));
                e.printStackTrace();
            } catch (Exception e) {
                log.info(buildFailMessage("未知原因发送失败！"));
                e.printStackTrace();
            }
        };
        SEND_POOL.submit(task);
    }

    @Override
    public void sendAttachmentStreamMail(AttachmentStreamMailMessage mailMessage) {
        validateAttachmentStreamMailMessageInfo(mailMessage);
        Runnable task = () -> {
            //发送邮件
            final MimeMessage mimeMessage = buildMimeMessageByMailMessage(mailMessage);
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setText(mailMessage.getContent(), true);
                for (AttachmentStreamMailMessage.AttachmentStream attachment : mailMessage.getAttachmentStreamList()) {
                    try {
                        String fileName = MimeUtility.encodeText(attachment.getFileName(),
                                mailProperties.getCharset().name(), "Q");
                        mimeMessageHelper.addAttachment(fileName, attachment.getInputStreamSource());
                    } catch (UnsupportedEncodingException e) {
                        log.info(buildFailMessage("附件名称格式化错误！"));
                        e.printStackTrace();
                    }
                }
                javaMailSender.send(mimeMessage);
                mailMessage.getAttachmentStreamList().forEach(attachmentStream -> {
                    try {
                        @Cleanup InputStream inputStream = attachmentStream.getInputStreamSource().getInputStream();
                    } catch (IOException e) {
                        log.info(buildFailMessage("数据流清理失败！"));
                        e.printStackTrace();
                    }
                });
            } catch (MessagingException e) {
                log.info(buildFailMessage("邮件附件初始化失败！"));
                e.printStackTrace();
            } catch (Exception e) {
                log.info(buildFailMessage("未知原因发送失败！"));
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
        Assert.isTrue(nonEmptyCollection(simpleMailMessage.getToUserList()), "收件人不能为空！");
    }

    /**
     * 验证模板邮件信息
     */
    private static void validateTemplateMailMessageInfo(TemplateSimpleMailMessage templateMailMessage) {
        Assert.isTrue(!StringUtils.isEmpty(templateMailMessage.getSubject()), "邮件主题不能为空！");
        Assert.isTrue(nonEmptyCollection(templateMailMessage.getToUserList()), "收件人不能为空！");
        Assert.isTrue(!StringUtils.isEmpty(templateMailMessage.getTemplateName()), "模板名称不能为空！");
    }

    /**
     * 验证附件邮件
     */
    private static void validateAttachmentMailMessageInfo(AttachmentMailMessage attachmentMailMessage) {
        Assert.isTrue(!StringUtils.isEmpty(attachmentMailMessage.getSubject()), "邮件主题不能为空！");
        Assert.isTrue(nonEmptyCollection(attachmentMailMessage.getToUserList()), "收件人不能为空！");
        Assert.isTrue(!StringUtils.isEmpty(attachmentMailMessage.getContent()), "邮件内容不能为空！");
        Assert.isTrue(nonEmptyCollection(attachmentMailMessage.getAttachmentList()), "附件信息不能为空！");
    }

    /**
     * 验证附件邮件
     */
    private static void validateAttachmentStreamMailMessageInfo(AttachmentStreamMailMessage mailMessage) {
        Assert.isTrue(!StringUtils.isEmpty(mailMessage.getSubject()), "邮件主题不能为空！");
        Assert.isTrue(nonEmptyCollection(mailMessage.getToUserList()), "收件人不能为空！");
        Assert.isTrue(!StringUtils.isEmpty(mailMessage.getContent()), "邮件内容不能为空！");
        Assert.isTrue(nonEmptyCollection(mailMessage.getAttachmentStreamList()), "附件信息不能为空！");
    }

    /**
     * 构建邮件发送基础对象
     *
     * @param simpleMailMessage 邮件基础信息
     * @return 邮件发送基础对象
     */
    private MimeMessage buildMimeMessageByMailMessage(SimpleMailMessage simpleMailMessage) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final String charset = mailProperties.getCharset().name();
        try {
            mimeMessage.setFrom(new InternetAddress(mailProperties.getUsername(), mailProperties.getNickname(), "UTF-8"));
            mimeMessage.setSubject(simpleMailMessage.getSubject(), charset);
            mimeMessage.setText(simpleMailMessage.getContent(), charset);
            //设置邮件发送时间
            if (null != simpleMailMessage.getDate()) {
                mimeMessage.setSentDate(simpleMailMessage.getDate());
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.info(buildFailMessage("邮件基础信息构建失败！"));
            e.printStackTrace();
        }
        Function<List<String>, String> listToString = (list) -> list.stream()
                .distinct().collect(Collectors.joining(","));
        try {
            String internetAddressTo = listToString.apply(simpleMailMessage.getToUserList());
            mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);
            if (nonEmptyCollection(simpleMailMessage.getCcUserList())) {
                //抄送
                final String internetAddressCC = listToString.apply(simpleMailMessage.getCcUserList());
                mimeMessage.setRecipients(Message.RecipientType.CC, internetAddressCC);
            }
            if (nonEmptyCollection(simpleMailMessage.getBccUserList())) {
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
    private static boolean nonEmptyCollection(Collection collection) {
        return null != collection && collection.size() != 0;
    }

    /**
     * 错误信息
     */
    private static String buildFailMessage(String message) {
        return String.format(" 【邮件发送失败】: %s", message);
    }
}