package com.nva.WebSocketChatApp.model;

public class Message {

    private String message;
    private String fromLogin;

    public Message(String message, String fromLogin) {
        this.message = message;
        this.fromLogin = fromLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", fromLogin='" + fromLogin + '\'' +
                '}';
    }
}
