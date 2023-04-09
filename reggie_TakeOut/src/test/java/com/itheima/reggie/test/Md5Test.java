package com.itheima.reggie.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密加盐测试
 */
public class Md5Test {

    @Test
    public void testPassword() {
        //SpringSecurity框架中提供的工具类，用于密码的加密加盐
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //生成3个密码字符串
        for (int i = 0; i < 3; i++) {
            //得到加密后的字符串
            String encode = encoder.encode("123456");
            System.out.println("加密后：" + encode);

            //判断密码是否相等：要判断的明文，加密后的字符串
            boolean success = encoder.matches("123456", encode);
            System.out.println(success);
        }
    }
}
