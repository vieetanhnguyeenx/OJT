package controller;

public interface BaseController {

    public void sendMessage(int socketId, String method, String url,  String header, String payload);
}
