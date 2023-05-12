package org.example;

import helper.HTTPServerHelper;
import helper.KafkaHelper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            List<String> header = null;

            InputStream inputStream = null;
            OutputStream outputStream = null;
            BufferedReader bufferedReader = null;
            PrintWriter writer = null;

            while (true) {
                // Connect and print log
                Socket socket = serverSocket.accept();
                System.out.println("[Server_log]: Connected" + LocalDateTime.now());

                // Get Reader
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                writer = new PrintWriter(outputStream, true);
                header = new ArrayList<>();

                // Get Header request
                String hederLine = null;
                while ((hederLine = bufferedReader.readLine()).length() != 0) {
                    System.out.println(hederLine);
                    header.add(hederLine);
                }

                // Get payload for doPost()
                StringBuilder payload = new StringBuilder();
                while (bufferedReader.ready()) {
                    payload.append((char) bufferedReader.read());
                }

                // Get method, url, param string
                String method = HTTPServerHelper.getMethod(header.get(0));
                String url = HTTPServerHelper.getUrl(header.get(0));
                if (url.equalsIgnoreCase("/login")|| url.equalsIgnoreCase("/")) {
                    if (method.equalsIgnoreCase("get")) {
                        KafkaHelper.produceLoginMessage(url, method, header.get(0), payload.toString());
                    } else {
                        KafkaHelper.produceLoginMessage(url, method, header.get(0), payload.toString());
                    }
                }

                inputStream.close();
                outputStream.close();
                bufferedReader.close();
                writer.close();
                socket.close();
                System.out.println("[Server_log]: Closed" + LocalDateTime.now());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}