package com.partick.redisdemo.message;

import com.partick.redisdemo.annotation.entity.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author patrick_peng
 * @description
 * @date 2023-01-31 17:59
 **/
@SpringBootTest
public class RedisMessageTest {

    @Resource
    private RedisMessageSend redisMessageSend;

    @Test
    public void redisMessageSendTest(){
        for (int i = 0; i < 1000; i++) {
            Emp emp = new Emp(i, "partick", new Date(), 10.0f, "部门1");
            redisMessageSend.sendMessage("emp", emp);
        }
    }
}
