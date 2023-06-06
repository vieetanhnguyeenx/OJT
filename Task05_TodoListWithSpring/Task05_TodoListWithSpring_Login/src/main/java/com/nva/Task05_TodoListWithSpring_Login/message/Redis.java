package com.nva.Task05_TodoListWithSpring_Login.message;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Base64;

public class Redis {
    public static JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);

    public static Jedis jedis = jedisPool.getResource();



}
