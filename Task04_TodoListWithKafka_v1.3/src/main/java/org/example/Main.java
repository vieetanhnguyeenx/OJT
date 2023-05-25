package org.example;

import core.KafkaListenerThread;
import core.KafkaListenerThread01;
import core.KafkaListenerThread02;
import core.ServerListenerThread;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try{
            firstProduce();
            KafkaListenerThread kafkaListenerThread = new KafkaListenerThread();
            kafkaListenerThread.start();

            KafkaListenerThread01 kafkaListenerThread01 = new KafkaListenerThread01();
            kafkaListenerThread01.start();

            KafkaListenerThread02 kafkaListenerThread02 = new KafkaListenerThread02();
            kafkaListenerThread02.start();

            ServerListenerThread serverListenerThread = new ServerListenerThread(8888,"localhost");
            serverListenerThread.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void firstProduce() {
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "add-task-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(512));
        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("add-task-serv", "test"));
        }

        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("change-status-serv", "test"));
        }

        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("delete-task-serv", "test"));
        }

        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("edit-task-serv", "test"));
        }

        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("load-data-serv", "test"));
        }

        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("login-request-serv", "test"));
        }

        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("todo-list-serv", "test"));
        }
    }
}