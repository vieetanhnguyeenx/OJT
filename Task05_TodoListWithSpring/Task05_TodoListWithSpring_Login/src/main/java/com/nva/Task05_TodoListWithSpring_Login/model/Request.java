package com.nva.Task05_TodoListWithSpring_Login.model;

import java.util.Map;

public class Request {
    private String id;
    private String url;
    private Map<String, String> parameter;
    private String token;

    public Request() {
    }

    public Request(String id, String url, Map<String, String> parameter, String token) {
        this.id = id;
        this.url = url;
        this.parameter = parameter;
        this.token = token;
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

    public Map<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", parameter=" + parameter +
                ", token='" + token + '\'' +
                '}';
    }
}
