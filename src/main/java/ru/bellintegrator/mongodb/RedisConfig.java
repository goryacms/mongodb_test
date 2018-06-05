package ru.bellintegrator.mongodb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;


@Configuration
public class RedisConfig {
    @Autowired
    private RedisConfigSerializer redisConfigSerializer;


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory js = new JedisConnectionFactory();
        js.setUsePool(true);
        js.setHostName("localhost");
        js.setPort(6379);
        return js;
    }

    @Bean
    public RedisTemplate<String, RedisVO> redisTemplate(){
        RedisTemplate<String, RedisVO> rt = new RedisTemplate<>();
        rt.setConnectionFactory(jedisConnectionFactory());
        rt.setKeySerializer(new StringRedisSerializer());
        rt.setValueSerializer(redisConfigSerializer);
        return rt;
    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate());
        rcm.setDefaultExpiration(60);
        rcm.setTransactionAware(true);
        return rcm;
    }

    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object arg0, Method arg1, Object... arg2) {
                String key = "";

                for (Object oc: arg2){
                    if(oc instanceof RedisVO){
                        RedisVO rv = (RedisVO) oc;
                        key = String.valueOf(rv.getKeyID());
                    }
                    if(oc instanceof Integer){
                        key = String.valueOf(oc);
                    }
                }
                return key;
            }
        };
    }
}
