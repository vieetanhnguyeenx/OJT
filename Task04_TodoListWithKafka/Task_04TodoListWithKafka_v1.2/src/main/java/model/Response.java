package model;

public class Response {
    public static final String SC_OK = "200 OK";
    public static final String SC_MOVED_PERMANENTLY = "301 Moved Permanently";
    public static final String SC_FOUND = "302 Found";

    private int socketId;

    private String url;
    private String method;
    private String statusCode;
    private String newUrl;
    private String htmlResponse;
    private String jsonData;

    private String textData;

    public Response(int socketId, String url, String method, String statusCode, String newUrl, String htmlResponse, String jsonData, String textData, String token) {
        this.socketId = socketId;
        this.url = url;
        this.method = method;
        this.statusCode = statusCode;
        this.newUrl = newUrl;
        this.htmlResponse = htmlResponse;
        this.jsonData = jsonData;
        this.textData = textData;
        this.token = token;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    private String token;

    public Response() {
    }

    public Response(int socketId, String url, String method, String statusCode, String newUrl, String htmlResponse, String jsonData, String token) {
        this.socketId = socketId;
        this.url = url;
        this.method = method;
        this.statusCode = statusCode;
        this.newUrl = newUrl;
        this.htmlResponse = htmlResponse;
        this.jsonData = jsonData;
        this.token = token;
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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    public String getHtmlResponse() {
        return htmlResponse;
    }

    public void setHtmlResponse(String htmlResponse) {
        this.htmlResponse = htmlResponse;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Response{" +
                "socketId=" + socketId +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", newUrl='" + newUrl + '\'' +
                ", htmlResponse='" + htmlResponse + '\'' +
                ", jsonData='" + jsonData + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
