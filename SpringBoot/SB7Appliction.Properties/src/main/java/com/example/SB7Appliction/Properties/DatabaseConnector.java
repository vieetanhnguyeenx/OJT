package com.example.SB7Appliction.Properties;

public abstract class DatabaseConnector {
    private String url;

    public abstract void connect();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
