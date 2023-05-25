package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{
    private static int socketId = 0;
    private int port;
    private String webroot;

    private ServerSocket serverSocket;


    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                HttpConnectionWorkerThread httpConnectionWorkerThread = new HttpConnectionWorkerThread(socket, socketId++);
                httpConnectionWorkerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
