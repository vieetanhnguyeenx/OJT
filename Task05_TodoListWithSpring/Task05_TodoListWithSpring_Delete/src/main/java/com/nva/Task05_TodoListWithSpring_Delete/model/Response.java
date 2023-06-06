package com.nva.Task05_TodoListWithSpring_Delete.model;

import org.springframework.http.HttpStatus;

public class Response {
    private String id;
    private String url;
    private HttpStatus statusCode;
    private String newUrl;
    private String htmlResponse;
    private String jsonData;
    private String textData;

    public Response() {
    }

    public Response(String id, String url, HttpStatus statusCode, String newUrl, String htmlResponse, String jsonData, String textData) {
        this.id = id;
        this.url = url;
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

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
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
                ", statusCode=" + statusCode +
                ", newUrl='" + newUrl + '\'' +
                ", htmlResponse='" + htmlResponse + '\'' +
                ", jsonData='" + jsonData + '\'' +
                ", textData='" + textData + '\'' +
                '}';
    }
}
