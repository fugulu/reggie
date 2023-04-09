package com.itheima.reggie.service;

import com.itheima.reggie.entity.User;

/**
 * 用户业务类
 */
public interface UserService {

    /**
     * 登录和注册
     */
    User login(String phone);
}
