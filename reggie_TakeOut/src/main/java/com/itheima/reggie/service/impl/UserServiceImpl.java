package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.User;
import com.itheima.reggie.mapper.UserMapper;
import com.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录和注册
     * @param phone
     */
    @Override
    public User login(String phone) {
        //1.通过手机号查询用户是否存在
        User user = userMapper.findUserByPhone(phone);
        //2.如果不存在则注册一个用户
        if (user == null) {
            //创建一个新的用户
            user = new User();
            //设置手机，状态
            user.setPhone(phone);
            user.setStatus(1);
            //保存
            userMapper.add(user);
        }
        //3.返回用户对象
        return user;
    }
}
