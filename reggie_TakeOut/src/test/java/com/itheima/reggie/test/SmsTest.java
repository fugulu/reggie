package com.itheima.reggie.test;

import com.itheima.reggie.util.SMSUtils;
import org.junit.jupiter.api.Test;

/**
 * 短信测试类
 */
public class SmsTest {

    @Test
    public void testSendMessage() {
        //签名，模板，电话，验证码
        SMSUtils.sendMessage("黑马旅游", "SMS_195723031", "15820201641", "6868");
        //如果返回OK，则发送成功
    }
}
