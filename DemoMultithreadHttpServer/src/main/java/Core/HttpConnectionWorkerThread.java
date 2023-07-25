package Core;

import helper.HTTPServerHelper;

import java.io.*;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private int socketId;
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket, int socketID) {
        this.socket = socket;
        this.socketId = socketID;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(outputStream, true);
            System.out.println(socketId);
            while (true) {
                if (SharedData.data.containsKey(socketId)) {
                    HTTPServerHelper.forwardHtml(writer, outputStream, "login.html");
                    inputStream.close();
                    outputStream.close();
                    bufferedReader.close();
                    writer.close();
                    socket.close();
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
