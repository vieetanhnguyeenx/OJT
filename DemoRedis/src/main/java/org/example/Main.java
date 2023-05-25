package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.ietf.jgss.GSSContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Gson gson = new Gson();
        try(Jedis jedis = jedisPool.getResource()) {
//            Student s = new Student(1, "nva", 12, LocalDate.now());
//            Student s1 = new Student(2, "nva2", 18, LocalDate.now());
//            Student s2 = new Student(3, "nva3", 132, LocalDate.now());
//            int i = 1;
//            Map<String, String> stringStudentMap = new HashMap<>();
//
//            stringStudentMap.put(String.valueOf(s.getId()), JsonHelper.stringgify(JsonHelper.toJson(s)));
//            stringStudentMap.put(String.valueOf(s1.getId()), JsonHelper.stringgify(JsonHelper.toJson(s1)));
//            stringStudentMap.put(String.valueOf(s2.getId()), JsonHelper.stringgify(JsonHelper.toJson(s2)));
//
//            for (Map.Entry<String, String> entry: stringStudentMap.entrySet()) {
//                System.out.println(entry.getValue());
//                jedis.hset("student", entry.getKey(), entry.getValue());
//            }
            System.out.println(jedis.hlen("student"));
            Map<String, String> stringStudentMap = jedis.hgetAll("student");
            for (Map.Entry<String, String> entry: stringStudentMap.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
//                Student s = JsonHelper.fromJson(JsonHelper.parse(entry.getValue()), Student.class);
//                System.out.println(s);
            }
        }
    }
}