package com.itheima.reggie.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @Projectname: reggie
 * @Filename: SendEmail
 * @Author: Steven
 * @Data:2022-10-14 23:00
 */
@SpringBootTest
public class SendEmail {
    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void sendSimpleMail() throws Exception {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2810705387@qq.com");
        message.setTo("1397647906@qq.com");
        message.setSubject("这是一条测试语句");
        message.setText("收到了吗，收到了吗");
        mailSender.send(message);
    }
}
