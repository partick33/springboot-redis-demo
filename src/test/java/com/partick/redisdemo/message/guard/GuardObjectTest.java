package com.partick.redisdemo.message.guard;

import com.partick.redisdemo.annotation.entity.MyMessage;
import com.partick.redisdemo.message.RedisMessageListener;
import com.partick.redisdemo.message.RedisMessageSend;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author patrick_peng
 * @description
 * @date 2023-02-01 10:24
 **/
@SpringBootTest
public class GuardObjectTest {

    @Resource
    private RedisMessageSend redisMessageSend;

    @Resource
    private RedisMessageListener redisMessageListener;

    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Test
    public void GuardObjectTest() throws InterruptedException {
        executorService.submit(()->{
            //异步处理任务，发送进度到Redis主题下
            for (int i = 0; i < 100; i++) {
                redisMessageSend.sendMessage("GuardObjectTest", new MyMessage(String.valueOf(i), "GuardObjectTest" + i));
            }
            //不断刷新是否有请求进来获取数据
            while (true){
                for (int i = 0; i < 100; i++) {
                    System.out.println("---------------");
                    Boolean message = redisMessageListener.getMessage("GuardObjectTest" + i);
                    //踢出队列
                    if (message){
                    }
                }
            }
        });

        Thread.sleep(5000);
        //等待唤醒
        GuardObject object = GuardObject.create("GuardObjectTest" + 50);
        Object o = object.get(t -> t != null);
        System.out.println(o);
    }

}