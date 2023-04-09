package com.itheima.reggie.mapper;

/**
 * @Projectname: reggie
 * @Filename: UserMapper
 * @Author: Steven
 * @Data:2022-10-14 17:34
 */

import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * 手机端用户数据层
 */
public interface UserMapper {
    /**
     * 通过邮箱号查询用户
     */
    @Select("select * from user where email=#{email}")
    User findUserByEmail(String email);

    /**
     * 新增用户
     * 1. 插入用户信息：只填充2个字段：phone, status
     * 2. 获取新增用户的id
     * 3. 方法名不能叫：save() 因为会激活aop填充公共字段
     */
    @Insert("insert into user(email, status) values (#{emeai},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void add(User user);
}
