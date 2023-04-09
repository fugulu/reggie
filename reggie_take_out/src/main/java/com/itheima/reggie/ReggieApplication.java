package com.itheima.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Slf4j注解的作用：
 * 在字节码文件中生成log对象，用于记录日志功能，后面的方法中就可以直接使用log对象输出日志信息
 * 日志输出级别常用有5种：
 * debug(调试级) < info(一般信息) < warning(警告) < error(错误) < fatal (致命错误)
 * 级别越低，信息越详细，级别越高信息越少，问题就越严重
 * 1 debug: 调试信息，越详细越好
 * 2. info：一般的输出信息
 * 3. warning：警告，表示可能会有问题，我们也可以不理会
 * 4. error：出现了错误，但程序可以正常运行
 * 5. fatal：致命错误，不能修复，程序已经崩了
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.itheima.reggie.mapper")  //扫描这个包下所有的接口，生成代理对象
public class ReggieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        //在服务器的控制台上可以看到输出的日志信息
        log.info("启动瑞吉点餐系统");
    }
}
