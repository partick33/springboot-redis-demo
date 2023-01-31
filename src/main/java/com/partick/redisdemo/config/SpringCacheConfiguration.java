package com.partick.redisdemo.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author partick_peng
 */
@Configuration
public class SpringCacheConfiguration {

    /**
     * @param factory
     * @Primary  设置默认的CacheManager
     * @return
     */
    @Bean
    @Primary
    public CacheManager cacheManagerMinutes(LettuceConnectionFactory factory) {

        //加载默认Spring Cache配置信息
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        config = config
                //设置有效期为30分钟
                .entryTtl(Duration.ofMinutes(30))
                //设置缓存Key用单冒号进行分割
                .computePrefixWith(cacheName -> cacheName + ":")
                //Key采用String直接存储
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))
                //Value采用JSON存储
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                //不缓存Null值对象
                .disableCachingNullValues();

        //创建Redis缓存管理器
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(factory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    @Bean
    public CacheManager cacheManagerOneHour(LettuceConnectionFactory factory) {

        //加载默认Spring Cache配置信息
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        config = config
                //设置有效期为30分钟
                .entryTtl(Duration.ofHours(1))
                //设置缓存Key用单冒号进行分割
                .computePrefixWith(cacheName -> cacheName + ":")
                //Key采用String直接存储
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))
                //Value采用JSON存储
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                //不缓存Null值对象
                .disableCachingNullValues();

        //创建Redis缓存管理器
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(factory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }
}
