package com.itheima.reggie.config;

import com.itheima.reggie.common.CheckLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 静态资源放行
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("静态资源放行");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 配置拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("加载了用户权限的拦截器");
        //要放行的资源：登录，退出，所有的静态资源(因为以后静态资源不在同一个服务器上)
        String[] urls = {
                "/employee/login",      //登录
                "/employee/logout",     //退出
                "/backend/**",          //后台管理静态页面
                "/front/**",            //手机静态页面
                "/common/**",            //上传和下载
                "user/sendMsg",
                "user/login"
        };
        //注册拦截器到SpringMVC, addPathPatterns()指定哪些地址需要拦截，excludePathPatterns()哪些地址不被拦截
        registry.addInterceptor(new CheckLoginInterceptor()).addPathPatterns("/**").excludePathPatterns(urls);
    }

    /**
     * 扩展消息转换器，使用自定义的JSON类型转换器
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展JSON消息转换器");
        //1.创建消息转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //2.设置自定义的消息转换器
        converter.setObjectMapper(new JacksonObjectMapper());
        //3.将这个消息转换器添加到集合中让它起作用，放在最前面，让它先起作用
        converters.add(0, converter);
    }
}
