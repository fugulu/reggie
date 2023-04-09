package com.itheima.reggie.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 用户登录权限拦截器
 */
@Slf4j
public class CheckLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断用户是否登录，获取会话域中用户id
        HttpSession session = request.getSession();
        Long loginId = (Long) session.getAttribute("LOGIN_ID");
        //2.如果会话ID不为空，放行
        if (loginId != null) {
            //{}相当于字符串中占位符
            log.info("用户ID是：{}，已经登录放行", loginId);
            return true;
        }
        //3.如果为空，则拦截，输出JSON字符串给前端
        else {
            log.info("拦截到非法的访问，访问地址是：{}", request.getRequestURL());
            //字符串必须是NOTLOGIN，自己手动转换，并且打印到浏览器
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            //转成JSON字符串，设置code等于0，msg等于"NOTLOGIN"
            String json = JSON.toJSONString(R.error("NOTLOGIN"));
            out.print(json);
            //拦截
            return false;
        }
    }
}
