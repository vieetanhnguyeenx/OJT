package com.example.httpservertest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class HttpServerTestApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(HttpServerTestApplication.class, args);


        File file = new File(
                "D:\\OJT\\OJT\\DemoHttpClientJava\\token1.txt");

        Gson gson = new Gson();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String token = "";
        while ((st = br.readLine()) != null) {
            System.out.println(st);
            token += st;
        }
        System.out.println(token);
        Map<Integer, String> tokenMap = gson.fromJson(token, new TypeToken<Map<Integer, String>>() {
        }.getType());
        int roomId = 9;
        Date date = new Date();

        int corePoolSize = 5;
        int maximumPoolSize = 100;
        long keepAliveTime = 500;
        TimeUnit unit = TimeUnit.SECONDS;

        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);

        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize, keepAliveTime, unit, workQueue, handler);


        int user = 1000;

        for (int i = 0; i < user; i++) {
            threadPoolExecutor.execute(new MessageThread(tokenMap, date, i + 10, roomId));
        }
    }

}
