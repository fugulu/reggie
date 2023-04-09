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
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @PostMapping("/login")
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
