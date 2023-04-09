package com.itheima.reggie.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Projectname: store
 * @Filename: RedisConfig
 * @Author: Steven
 * @Data:2022-10-21 9:12
 */

@Configuration
public class RedisConfig {

    /**
     * 指定键和值的序列化器
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        //  1.创建模板对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //  2.设置连接工厂
        template.setConnectionFactory(factory);
        //  3.指定键和值的序列化器
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        //  4.返回模板对象
        return template;
    }
}
