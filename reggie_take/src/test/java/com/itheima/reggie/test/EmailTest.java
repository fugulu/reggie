package com.itheima.reggie.test;

import com.itheima.reggie.util.SendEmailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Projectname: reggie
 * @Filename: EmailTest
 * @Author: Steven
 * @Data:2022-10-14 18:09
 */
@SpringBootTest
public class EmailTest {

    @Autowired
    private SendEmailUtils sendEmailUtils ;

    @Test
    public void testSend(){
        String content = "测试邮件发送";
        sendEmailUtils.sendSimpleMail("350163328@qq.com","邮件测试：",content);
    }
}
