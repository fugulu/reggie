package com.itheima.reggie.controller;

/**
 * @Projectname: reggie
 * @Filename: UserController
 * @Author: Steven
 * @Data:2022-10-14 17:43
 */

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录或者注册，封装的参数是Map，封装邮箱和验证码
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;
    /**
     * 发短信
     * 手机号从user中获取phone属性
     */
    @PostMapping("sendMsg")
    public R<String> sendMsg(@RequestBody User user) {
        //1.获取邮箱号
        String email = user.getEmail();
        //2.生成4位验证码
        String code = ValidateCodeUtils.generateValidateCode(4);
        //在控制台输出验证码
        log.info("验证码是：{}", code);
        //发送短信
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2810705387@qq.com");
        message.setTo("1397647906@qq.com");
        message.setSubject("瑞吉外卖注册");
        message.setText("这是您本次注册的验证码：" + code +"。验证码有效时间为：1分钟，请保管好您的验证码，防止泄露哦~");
        mailSender.send(message);
        //5.返回发送成功信息
        return R.success("发送成功");
    }

    @PostMapping("login")
    public R<String> login(@RequestBody Map<String,String> param){
        //1.获取用户提交的参数
        String email = param.get("email");
        String code = param.get("code");
        //2.从会话域中得到验证码
        String sessionCode = (String)session.getAttribute("code");
        //3.如果验证码错误就返回错误信息
        if(!code.equalsIgnoreCase(sessionCode)){
            return R.error("验证码错误");
        }
        //4.验证码正确则调用业务层的登录或者注册
        User user = userService.login(email);
        //5.如果用户不为空，则登录或注册成功
        if(user != null){
            //  登录成功则将用户id保存在会话域中
            session.setAttribute("LOGIN_ID", user.getId());
            return R.success("登录成功");
        }
        //6.否则登录失败
        else {
            return R.error("登录失败");
        }
    }

    /**
     * 用户退出
     */
    @PostMapping("loginout")
    public R<String> logout(){
        session.invalidate();
        return R.success("用户退出成功");
    }
}
