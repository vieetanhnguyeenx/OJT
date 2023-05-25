package model;

public class LoginResponse {
    public static final String SC_OK = "200 OK";
    public static final String SC_MOVED_PERMANENTLY = "301 Moved Permanently";
    public static final String SC_FOUND = "302 Found";
    private String statusCode;
    private String newUrl;
    private String htmlResponse;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String statusCode, String newUrl, String htmlResponse, String token) {
        this.statusCode = statusCode;
        this.newUrl = newUrl;
        this.htmlResponse = htmlResponse;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", newUrl='" + newUrl + '\'' +
                ", htmlResponse='" + htmlResponse + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
