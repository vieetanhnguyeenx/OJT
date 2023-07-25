package com.nva.RealTimeMessenger_v21.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class RedisConfig {

    @Bean
    public Jedis jedis() {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        return jedisPool.getResource();
    }

}
