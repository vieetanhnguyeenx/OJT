package core;

import context.UserDAO;
import controller.BaseController;
import controller.ControllerFactory;
import helper.HTTPServerHelper;
import helper.TokenHelper;
import model.Response;
import model.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpConnectionWorkerThread extends Thread{
    private int socketId;
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket, int socketID) {
        this.socket = socket;
        this.socketId = socketID;
    }

    @Override
    public void run() {
        try {
            System.out.println(socketId);
            List<String> header = null;

            InputStream inputStream = null;
            OutputStream outputStream = null;
            BufferedReader bufferedReader = null;
            PrintWriter writer = null;

            System.out.println("[Server_log]: Connected" + socketId);

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

            ControllerFactory controllerFactor = new ControllerFactory();
            BaseController baseController = controllerFactor.getController(url);
            baseController.sendMessage(socketId, method, url,  header.get(0), payload.toString());

            System.out.println("[Server_log]: Waiting for consume" + socketId);
            while (true) {
                if (SharedData.data.containsKey(socketId)) {
                    Response response = SharedData.data.get(socketId);

                    if (response.getStatusCode().equalsIgnoreCase("200 OK")) {
                        if (response.getHtmlResponse() != null) {
                            HTTPServerHelper.forwardHtml(writer, outputStream, response.getHtmlResponse());
                        } else if (response.getJsonData() != null) {
                            HTTPServerHelper.forwardJson(writer, outputStream, response.getJsonData());
                        } else if (response.getTextData() != null) {
                            HTTPServerHelper.responeString(writer, outputStream, response.getTextData());
                        }
                    } else if (response.getStatusCode().equalsIgnoreCase("302 Found")) {
                        if (response.getNewUrl() != null) {
                            HTTPServerHelper.sendRedirect(response.getNewUrl(), outputStream);
                        }
                    }

                    inputStream.close();
                    outputStream.close();
                    bufferedReader.close();
                    writer.close();
                    socket.close();
                    System.out.println("[Server_log]: CLose" + socketId);
                    return;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
