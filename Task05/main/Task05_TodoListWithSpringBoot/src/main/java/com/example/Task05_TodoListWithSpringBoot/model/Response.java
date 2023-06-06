package com.example.Task05_TodoListWithSpringBoot.model;

public class Response {
    private String id;
    private String url;
    private String method;
    private String statusCode;
    private String newUrl;
    private String htmlResponse;
    private String jsonData;
    private String textData;

    public Response() {
    }

    public Response(String id, String url, String method, String statusCode, String newUrl, String htmlResponse, String jsonData, String textData) {
        this.id = id;
        this.url = url;
        this.method = method;
        this.statusCode = statusCode;
        this.newUrl = newUrl;
        this.htmlResponse = htmlResponse;
        this.jsonData = jsonData;
        this.textData = textData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", newUrl='" + newUrl + '\'' +
                ", htmlResponse='" + htmlResponse + '\'' +
                ", jsonData='" + jsonData + '\'' +
                ", textData='" + textData + '\'' +
                '}';
    }
}
