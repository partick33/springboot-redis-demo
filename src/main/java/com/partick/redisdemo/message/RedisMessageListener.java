package com.partick.redisdemo.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author patrick_peng
 * @description Redis消息监听者
 * @date 2023-01-31 17:38
 **/
@Component
public class RedisMessageListener implements MessageListener {

    @Value("${demo.redis.topic:}")
    private String topics;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public List<String> topicsToList(){
        return Arrays.asList(topics.split(","));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 接收消息频道
        System.out.println("接收消息频道:" + new String(pattern));
        Object o = redisTemplate.getValueSerializer().deserialize(message.getBody());
        //接收消息内容
        System.out.println("接收消息内容: " + o);
    }
}

