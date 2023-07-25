package org.example;

import Core.KafkaListenerThread;
import Core.ServerListenerThread;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            KafkaListenerThread kafkaListenerThread = new KafkaListenerThread();
            kafkaListenerThread.start();
            ServerListenerThread serverListenerThread = new ServerListenerThread(8888, "localhost");
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}