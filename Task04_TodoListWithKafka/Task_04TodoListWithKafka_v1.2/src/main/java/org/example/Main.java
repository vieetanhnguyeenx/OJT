package org.example;

import core.KafkaListenerThread;
import core.KafkaListenerThread01;
import core.KafkaListenerThread02;
import core.ServerListenerThread;

public class Main {
    public static void main(String[] args) {
        try {
            KafkaListenerThread kafkaListenerThread = new KafkaListenerThread();
            kafkaListenerThread.start();

            KafkaListenerThread01 kafkaListenerThread01 = new KafkaListenerThread01();
            kafkaListenerThread01.start();

            KafkaListenerThread02 kafkaListenerThread02 = new KafkaListenerThread02();
            kafkaListenerThread02.start();

            ServerListenerThread serverListenerThread = new ServerListenerThread(8888, "localhost");
            serverListenerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}