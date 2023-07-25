package com.nva.RealTimeMessenger_v21.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MessageThread implements Runnable {
    private Map<Integer, String> tokenMap;

    private Date date;

    private int id;

    private int roomId;

    public MessageThread(Map<Integer, String> tokenMap, Date date, int id, int roomId) {
        this.tokenMap = tokenMap;
        this.date = date;
        this.id = id;
        this.roomId = roomId;
    }

    @Override
    public void run() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request;
        for (int i = 0; i < 1000; i++) {
            int page = i + 1;
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8888/getMessageByPage?token=" + tokenMap.get(id) + "&roomId=" + roomId + "&page=" + page + "&date=" + "2023-07-14%2014:52:13.924"))
                    //.uri(URI.create("http://localhost:8888/test"))
                    .build();
            System.out.println("Page: " + page + "UserId: " + id);
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
        }

    }
}
