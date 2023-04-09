package com.itheima.reggie.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Projectname: store
 * @Filename: MySwagger2Config
 * @Author: Steven
 * @Data:2022-10-24 10:31
 */

@Configuration
@EnableSwagger2
@EnableKnife4j
@Slf4j
public class MySwagger2Config {

    @Bean
    public Docket createRestApi(){
        //  文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                //  文档信息，调用下面的方法获取文档的信息
                .apiInfo(apiInfo())
                //  创建api选择生成器，选中哪些包和哪些方法
                .select()
                //  对哪个包生成文档信息
                .apis(RequestHandlerSelectors.basePackage("com.itheima.reggie.controller"))
                //  所有的方法都要生成接口文档
                .paths(PathSelectors.any())
                .build();
    }

    //  当前模块的文档信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("瑞吉外卖")
                .contact(new Contact("fugulu","https:www.itcast.cn","fugulu@itheima.com"))
                .version("1.0")
                .description("瑞吉外卖接口文档")
                .build();
    }
}
