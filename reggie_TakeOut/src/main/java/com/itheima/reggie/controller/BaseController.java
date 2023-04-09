package com.itheima.reggie.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 做为其它控制器的父类
 */
public class BaseController {

    //1.注入请求，响应，会话对象
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    //2.从会话域中获取当前登录用户的ID
    public Long getLoginIdFromSession() {
        //注：与登录成功的键名一样
        return (Long) session.getAttribute("LOGIN_ID");
    }
}
