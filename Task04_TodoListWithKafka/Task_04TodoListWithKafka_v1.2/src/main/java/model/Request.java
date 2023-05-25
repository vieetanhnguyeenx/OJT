package model;

public class Request {

    private int socketId;
    private String url;
    private String method;
    private String requestHeader;
    private String payload;

    public Request() {
    }

    public Request(int socketId, String url, String method, String requestHeader, String payload) {
        this.socketId = socketId;
        this.url = url;
        this.method = method;
        this.requestHeader = requestHeader;
        this.payload = payload;
    }

    public int getSocketId() {
        return socketId;
    }

    public void setSocketId(int socketId) {
        this.socketId = socketId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Request{" +
                "socketId=" + socketId +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
