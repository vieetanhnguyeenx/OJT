//package com.example.Task05_TodoListWithSpringBoot.microservice.loginservice;
//
//import com.example.Task05_TodoListWithSpringBoot.model.Request;
//import com.google.gson.Gson;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.security.auth.Login;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.Properties;
//
//public class LoginProducer extends Thread {
//
//    @Autowired
//    private RedisTemplate template;
//
//    @Override
//    public void run() {
//        Properties producerProperties = new Properties();
//        Gson gson = new Gson();
//        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "login-producer");
//        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        producerProperties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(5 * 1024));
//        System.out.println("hi1");
//        while (true) {
//            if (template.opsForHash().size("loginsevice") > 0) {
//                System.out.println(template.opsForHash().size("loginsevice"));
//                Map<String, String> map = template.opsForHash().entries("loginsevice");
//                for (Map.Entry<String, String> data : map.entrySet()) {
//                    System.out.println(data.getKey());
//                    System.out.println(data.getValue());
//                    template.opsForHash().delete("loginsevice", data.getKey());
//                }
//
//            }
//        }
//    }
//}
