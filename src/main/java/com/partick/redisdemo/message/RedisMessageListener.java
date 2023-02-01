package com.partick.redisdemo.message;

import com.partick.redisdemo.message.guard.GuardObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    private static Map<String, Object> MAP = new ConcurrentHashMap<>();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock r = readWriteLock.readLock();

    private final Lock w = readWriteLock.writeLock();

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
        //System.out.println("接收消息频道:" + new String(pattern));
        Object o = redisTemplate.getValueSerializer().deserialize(message.getBody());
        assert o != null;
        LinkedHashMap linkedHashMap = (LinkedHashMap) o;
        Object id = linkedHashMap.get("id");
        try {
            w.lock();
            MAP.put(new String(pattern) + id.toString(), o);
        }finally {
            w.unlock();
        }
        //接收消息内容
        //System.out.println("接收消息内容: " + o);
    }

    public Boolean getMessage(String id){
        try {
            r.lock();
            if (!MAP.containsKey(id)){
                return false;
            }
            GuardObject.findEvent(id, MAP.get(id));
            return true;
        }finally {
            r.unlock();
        }
    }
}

