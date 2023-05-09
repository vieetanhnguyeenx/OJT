package org.example;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        final var props = new Properties();
        props.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "java-producer");
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, String.valueOf(64 * 1024 * 1024L));

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

                try (var producer = new KafkaProducer<String, String>(props)) {
                final var message = new ProducerRecord<>(
                        "new-kafka-topic-01",     //topic name
                        "key-" + "101",            // key
                        "hello mtfk"        // value
                );
                producer.send(message);
        }
    }
}
