package com.partick.redisdemo.message;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author patrick_peng
 * @description Redis消息发送者
 * @date 2023-01-31 17:56
 **/
@Component
public class RedisMessageSend {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 向通道发布消息
     */
    public void sendMessage(String topic, Object message) {
        if (!StringUtils.hasText(topic)) {
            return;
        }
        try {
            redisTemplate.convertAndSend(topic, message);
            System.out.println("发送消息成功，topic：" + topic + "，message：" +  message);
        } catch (Exception e) {
            System.out.println("发送消息失败，topic：" + topic + "，message：" +  message);
            e.printStackTrace();
        }
    }


}
