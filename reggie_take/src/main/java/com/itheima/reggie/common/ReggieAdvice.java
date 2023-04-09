package com.itheima.reggie.common;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Page;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

@Component  //放到Spring容器中
@Aspect  //表示这是一个切面类
@Slf4j  //输出日志
public class ReggieAdvice {

    //注入会话对象
    @Autowired
    private HttpSession session;

    /**
     * 添加的前置增强
     * 前置通知：访问修饰符 返回值 包名.类名.方法名(参数) 异常
     * 星号放在参数中表示一个参数
     * @param joinPoint 获取目标方法信息
      */
    @SneakyThrows  //在字节码文件中抛出异常
    @Before("execution(* com.itheima.reggie.mapper.*Mapper.save*(*))")
    public void beforeSave(JoinPoint joinPoint) {
        //1.获取方法参数：save方法中实体类对象
        Object[] args = joinPoint.getArgs();
        //2.获取第0个元素，得到实体类对象
        Object entity = args[0];
        //3.通过反射调用set方法给四个属性赋值
        //获取类对象
        Class<?> entityClass = entity.getClass();
        //设置创建的时间
        //获取方法：方法名, 形参类型
        Method setCreateTime = entityClass.getMethod("setCreateTime", LocalDateTime.class);
        //调用方法: 哪个对象调用，调用方法参数
        setCreateTime.invoke(entity, LocalDateTime.now());

        //设置更新的时间
        Method setUpdateTime = entityClass.getMethod("setUpdateTime", LocalDateTime.class);
        setUpdateTime.invoke(entity, LocalDateTime.now());

        //从会话域中得到用户id
        Long loginId = (Long) session.getAttribute("LOGIN_ID");

        //设置创建的用户
        Method setCreateUser = entityClass.getMethod("setCreateUser", Long.class);
        setCreateUser.invoke(entity, loginId);

        //设置更新的用户
        Method setUpdateUser = entityClass.getMethod("setUpdateUser", Long.class);
        setUpdateUser.invoke(entity, loginId);

        log.info("添加操作自动填充通用属性值: {}", entity);
    }


    /**
     * 修改前置增强
     */
    @SneakyThrows
    @Before("execution(* com.itheima.reggie.mapper.*Mapper.update*(*))")
    public void beforeUpdate(JoinPoint joinPoint) {
        //1.获取方法参数：save方法中实体类对象
        Object[] args = joinPoint.getArgs();
        //2.获取第0个元素，得到实体类对象
        Object entity = args[0];
        //3.通过反射调用set方法给四个属性赋值
        //获取类对象
        Class<?> entityClass = entity.getClass();

        //设置更新的时间
        Method setUpdateTime = entityClass.getMethod("setUpdateTime", LocalDateTime.class);
        setUpdateTime.invoke(entity, LocalDateTime.now());

        //从会话域中得到用户id
        Long loginId = (Long) session.getAttribute("LOGIN_ID");

        //设置更新的用户
        Method setUpdateUser = entityClass.getMethod("setUpdateUser", Long.class);
        setUpdateUser.invoke(entity, loginId);

        log.info("更新操作自动填充通用属性值: {}", entity);
    }

    /**
     * 优化分页代码
     */
    @SneakyThrows
    @Around("execution(* com.itheima.reggie.service.*Service.findByPage(..))")
    public Page handlePage(ProceedingJoinPoint joinPoint) {
        //获取切入点方法参数
        Object[] args = joinPoint.getArgs();
        //获取页码和页大小
        Integer pageNum = (Integer) args[0];
        Integer pageSize = (Integer) args[1];

        log.info("查询第{}页，每页大小{}", pageNum, pageSize);

        //1.设置页码和页面大小(生成2条SQL语句)
        PageHelper.startPage(pageNum, pageSize);
        //2.调用业务层查询，只封装了1页的数据
        Page page = (Page) joinPoint.proceed();
        //3.封装成PageInfo对象 (帮我们计算页面属性值，如：总页数)
        PageInfo pageInfo = new PageInfo<>(page.getRecords());
        //4.修改了返回值
        return new Page<>(pageInfo.getList(), pageInfo.getTotal(), pageSize, pageNum);
    }
}
