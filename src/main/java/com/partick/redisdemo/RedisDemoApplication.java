package com.partick.redisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author partick_peng
 * @EnableCaching 启用声明式缓存
 */
@SpringBootApplication
@EnableCaching
public class RedisDemoApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RedisDemoApplication.class);
        application.run();
    }
}
