package org.example;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        final var props = new Properties();
        props.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "java-producer");
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, String.valueOf(64 * 1024 * 1024L));


//        try (var producer = new KafkaProducer<String, String>(props)) {
//                final var message = new ProducerRecord<>(
//                        "new-kafka-topic-01",     //topic name
//                        "key-" + "101",            // key
//                        "message: " + "toi ten la nva"        // value
//                );
//                producer.send(message);
//        }
//        try (var producer = new KafkaProducer<String, String>(props)) {
//            Gson gson = new Gson();
//            for (int i = 1; i < 100; i++) {
//                String json = gson.toJson(new Student(i, "Nguyen Van " + i, Math.random() * i));
//                final var message = new ProducerRecord<>(
//                        "new-kafka-topic-01",     //topic name
//                        "key-" + "101",            // key
//                        json        // value
//                );
//                producer.send(message);
//            }
//
//        }

        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Arrays.asList("new-kafka-topic-01"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record -> System.out.println("Received message: " + record.value()));
        }
    }
}