package com.itheima.reggie.mapper;

import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * 用户的持久层
 */
public interface UserMapper {

    /**
     * 通过手机号查询
     */
    @Select("select * from user where phone=#{phone}")
    User findUserByPhone(String phone);

    /**
     * 新增用户
     * 因为注册以后就认为登录成功，需要将用户id保存到会话域中，所以要返回新增用户的id
     * 1. 插入用户只有2个字段：phone, status
     * 2. 要获取新增用户的id
     * 3. 方法名不能叫save()，因为没有公共字段要填充
     */
    @Insert("insert into user (phone,status) values(#{phone},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(User user);

    /**
     * 通过用户id查询一个用户对象
     */
    @Select("select * from user where id=#{id}")
    User findById(Long id);
}
