package com.lj.demo.mail;

import com.lj.demo.BaseWebTest;
import com.lj.demo.service.MailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * Created by junli on 2019-07-22
 */
public class MailTest extends BaseWebTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendSimpleMail() {
        mailService.sendSimpleMail();
        sleep();
    }

    @Test
    public void testSendTemplateMail() {
        mailService.sendTemplateMail();
        sleep();
    }

    @Test
    public void testSendAttachmentStreamMail(){
        mailService.sendAttachmentStreamMail();
        sleep();
    }



    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

