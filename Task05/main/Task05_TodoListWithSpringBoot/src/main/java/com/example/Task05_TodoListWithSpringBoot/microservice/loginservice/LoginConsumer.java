//package com.example.Task05_TodoListWithSpringBoot.microservice.loginservice;
//
//import com.example.Task05_TodoListWithSpringBoot.model.Request;
//import com.google.gson.Gson;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Properties;
//
//public class LoginConsumer extends Thread {
//
//    private RedisTemplate template;
//
//    @Override
//    public void run() {
//        System.out.println("hihihi");
//        Gson gson = new Gson();
//        Properties consumerProperties = new Properties();
//        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "login-consumer");
//        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
//        consumer.subscribe(Arrays.asList("login-request-serv"));
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(100);
//            records.forEach(r -> {
//                System.out.println("hi");
//                Request request = gson.fromJson(r.value(), Request.class);
//                System.out.println(request);
//                template.opsForHash().put("loginsevice", request.getId(), r.value());
//            });
//        }
//    }
//}
