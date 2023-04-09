package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.User;
import com.itheima.reggie.mapper.UserMapper;
import com.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Projectname: reggie
 * @Filename: UserServiceImpl
 * @Author: Steven
 * @Data:2022-10-14 17:39
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录或者注册
     * @param email
     */
    @Override
    public User login(String email) {
        //1.通过邮箱号查询用户对象
        User user = userMapper.findUserByEmail(email);
        //2.用户不存在，则注册
        if(user == null){
            //创建一个新用户
            user = new User();
            //  设置邮箱号
            user.setEmail(email);
            //  设置用户状态
            user.setStatus(1);
            //  保存，返回主键
            userMapper.add(user);
        }
        //3.返回用户对象
        return user;
    }
}
