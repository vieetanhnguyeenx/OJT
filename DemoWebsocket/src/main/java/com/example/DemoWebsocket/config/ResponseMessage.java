package com.example.DemoWebsocket.config;

public class ResponseMessage {

    private String messageContent;

    public ResponseMessage(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageContent='" + messageContent + '\'' +
                '}';
    }

}
