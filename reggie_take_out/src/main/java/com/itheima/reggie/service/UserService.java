package com.itheima.reggie.service;

import com.itheima.reggie.entity.User;

/**
 * @Projectname: reggie
 * @Filename: UserService
 * @Author: Steven
 * @Data:2022-10-14 17:38
 */
public interface UserService {
    /**
     * 登录或者注册
     */
    User login(String email);
}
