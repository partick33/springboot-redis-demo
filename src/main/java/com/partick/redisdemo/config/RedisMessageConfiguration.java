package com.partick.redisdemo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.partick.redisdemo.message.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author patrick_peng
 * @description Redis发布订阅配置类
 * @date 2023-01-31 17:29
 **/
@Configuration
public class RedisMessageConfiguration {
    /**
     * Redis消息监听器容器
     * 这个容器加载了RedisConnectionFactory和消息监听器
     * 可添加多个不同话题的redis监听器，需要将消息监听器和消息频道绑定，
     * 通过反射调用消息订阅处理器的相关方法进行业务处理
     *
     * @param  redisConnectionFactory   	连接工厂
     * @param  RedisMessageListener   	Redis消息监听
     * @return RedisMessageListenerContainer    消息监听容器
     */
    @Bean
    @SuppressWarnings("all")
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
                                                   RedisMessageListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        // 所有的订阅消息，都需要在这里进行注册绑定
        // 可以添加多个频道以及配置不同的频道
        for (String topic : listener.topicsToList()) {
            container.addMessageListener(listener, new PatternTopic(topic));
        }

        /**
         * 设置序列化对象
         * 特别注意：1. 发布的时候和订阅方都需要设置序列化
         *         2. 设置序列化对象必须放在 {加入消息监听器} 这步后面，不然接收器接收不到消息
         */
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        //对于Null值不输出
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        container.setTopicSerializer(jackson2JsonRedisSerializer);
        return container;
    }

}
