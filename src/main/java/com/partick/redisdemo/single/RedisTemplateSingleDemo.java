package com.partick.redisdemo.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * redis简单数据类型操作Demo
 * @author partick_peng
 */
@Component
public class RedisTemplateSingleDemo {

    private Logger LOG = LoggerFactory.getLogger(RedisTemplate.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 操作Redis的String数据类型
     */
    public void StringDemo() {
        redisTemplate.opsForValue().set("name", "partick");
        String name = (String) redisTemplate.opsForValue().get("name");
        LOG.info(name);
    }

    /**
     * 操作Redis的hash数据类型
     */
    public void HashTest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "partick");
        map.put("age", "24");
        redisTemplate.opsForHash().putAll("userInfo",map);
        redisTemplate.opsForHash().put("userInfo", "address", "Guangzhou");
        Map userInfo = redisTemplate.opsForHash().entries("userInfo");
        LOG.info(userInfo.toString());
    }
}
