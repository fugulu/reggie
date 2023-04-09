package com.itheima.reggie.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 短信发送工具类
 */
@Slf4j
@Component
public class SMSUtils {

    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送短信
     */
    public R<String> sendEmail(String to, String subject, String content) {
        SendSmsRequest request = new SendSmsRequest();
        String respCode = null;
        try {
            //获取发送的响应结果
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            //第二个参数为true表示邮件正文是html格式的，默认是false
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
            log.info("发送人：" + from + "收件人:" + to + "发送内容：" + subject);
            respCode = content;
        } catch (MessagingException e) {
            e.getMessage();
        }
        return R.success(respCode);
    }
}
