package com.nva.Task05_TodoListWithSpring_Edit.message;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis {
    public static JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
    public static Jedis jedis = jedisPool.getResource();

}
