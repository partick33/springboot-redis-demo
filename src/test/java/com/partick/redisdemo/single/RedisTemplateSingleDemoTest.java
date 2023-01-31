package com.partick.redisdemo.single;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RedisTemplateSingleDemoTest {

    @Autowired
    private RedisTemplateSingleDemo redisTemplateSingleDemo;

    @Test
    void stringDemo() {
        redisTemplateSingleDemo.StringDemo();
    }

    @Test
    void hashTest() {
        redisTemplateSingleDemo.HashTest();
    }
}