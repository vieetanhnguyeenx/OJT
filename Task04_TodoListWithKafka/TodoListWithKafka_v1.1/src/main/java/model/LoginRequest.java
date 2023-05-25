package model;

public class LoginRequest {
    private String url;
    private String method;
    private String requestHeader;
    private String payload;

    public LoginRequest() {
    }

    public LoginRequest(String url, String method, String requestHeader, String payload) {
        this.url = url;
        this.method = method;
        this.requestHeader = requestHeader;
        this.payload = payload;
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
        return "LoginRequest{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
