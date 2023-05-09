package org.example;

import controller.HttpBaseController;
import controller.HttpFactory;
import helper.HTTPServerHelper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            List<String> header = null;
            HttpFactory httpFactory = new HttpFactory();

            InputStream inputStream = null;
            OutputStream outputStream = null;
            BufferedReader bufferedReader = null;
            PrintWriter writer = null;

            while (true) {
                // Connect and print log
                Socket socket = serverSocket.accept();
                System.out.println("[Server_log]: Connected");

                // Get Reader
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                writer = new PrintWriter(outputStream, true);
                header = new ArrayList<>();

                // Get Header request
                String hederLine = null;
                while ((hederLine = bufferedReader.readLine()).length() != 0) {
                    //System.out.println(hederLine);
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

                // Redirect
                HttpBaseController httpBaseController = httpFactory.getController(url);
                if (method.equals("get")) {
                    httpBaseController.doGet(writer, outputStream, header.get(0));
                } else if (method.equals("post")) {
                    httpBaseController.doPost(writer, outputStream, payload.toString());
                } else {
                    HTTPServerHelper.forwardHtml(writer, outputStream, "404NotFound.html");
                }


                inputStream.close();
                outputStream.close();
                bufferedReader.close();
                writer.close();
                socket.close();

                System.out.println("[Server_log]: Closed");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}