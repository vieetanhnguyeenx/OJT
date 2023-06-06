package model;

import java.util.Map;

public class RequestKafka {
    private String name;
    private Map<String, String> requestHeader;

    public RequestKafka() {
    }

    public RequestKafka(String name, Map<String, String> requestHeader) {
        this.name = name;
        this.requestHeader = requestHeader;
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

}
