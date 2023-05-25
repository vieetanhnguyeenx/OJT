package org.example;

import com.google.gson.Gson;
import core.KafkaListenerThread;
import core.KafkaListenerThread01;
import core.KafkaListenerThread02;
import core.ServerListenerThread;
import model.Request;
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
        Gson gson = new Gson();
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "add-task-producer");
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(512));
        Request request = new Request(-2, "/addNewTask",  "POST", "POST /addNewTask HTTP/1.1", "token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==&title=123");
        String json = gson.toJson(request);

        //http://localhost:8888/addNewTask   POST /addNewTask HTTP/1.1   token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==&title=123
        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("add-task-serv", json));
        }

        request = new Request(-3, "/changeStatus", "POST", "POST /changeStatus HTTP/1.1", "token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==&id=245" );
        json = gson.toJson(request);

        //http://localhost:8888/changeStatus POST /changeStatus HTTP/1.1 token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==&id=233
        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("change-status-serv", json));
        }


        request = new Request(-4, "/deleteTask", "POST", "POST /deleteTask HTTP/1.1", "token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==&id=1" );
        json = gson.toJson(request);

        //http://localhost:8888/deleteTask POST /deleteTask HTTP/1.1 token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==&id=233
        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("delete-task-serv", json));
        }

        request = new Request(-5, "/editTask", "POST", "POST /editTask HTTP/1.1", "token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==&id=245&title=33123" );
        json = gson.toJson(request);

        //http://localhost:8888/editTask POST /editTask HTTP/1.1 token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==&id=234&title=33123
        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("edit-task-serv", json));
        }

        request = new Request(-1, "/loaddata", "POST", "POST /loaddata HTTP/1.1", "token=eyJpZCI6NCwibmFtZSI6ImRvaWRlbiIsInBhc3N3b3JkIjpudWxsfQ==" );
        json = gson.toJson(request);
        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("load-data-serv", json));
        }


        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("login-request-serv", "test"));
        }

        try(KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties)){
            producer.send(new ProducerRecord<>("todo-list-serv", "test"));
        }
    }
}