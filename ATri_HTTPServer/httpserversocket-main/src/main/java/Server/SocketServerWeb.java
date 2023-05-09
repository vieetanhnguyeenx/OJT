package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketServerWeb {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("http://localhost:8080");

        while (true) {
            Socket client = server.accept();
            OutputStream outputStream = client.getOutputStream();
            InputStream inputStream = client.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(outputStream, true);

            String line = bufferedReader.readLine();
            System.out.println("Request: " + line);

            String name = getName(line);
            String response = "";
            System.out.println(getCurrentTime());
            if (name != null) {
                response = "<div style=\"text-align: center; font-size: 30px\"> Chào mừng " + name + " </div>" +
                        "<div style=\"text-align: center;font-size: 30px\">" + "Thời gian trên server hiện tại là " + getCurrentTime() + "</div>";
            } else {
                response = getHtml("index.html");
            }

            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html;charset=utf-8");
            writer.println("Content-Length: " + response.getBytes(StandardCharsets.UTF_8).length);
            writer.println();
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));

            bufferedReader.close();
            writer.close();
            client.close();
        }
    }

    private static String getName(String request) {
        int index = request.indexOf("name=") + 5;
        int end = request.indexOf(" ", index);
        if (index == 4 || end == -1) {
            return null;
        }
        return request.substring(index, end);
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(new Date());
    }

    private static String getHtml(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), StandardCharsets.UTF_8));
        StringBuilder buf = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            buf.append(line);
        }
        String response = buf.toString();
        return response;
    }
}