package com.example.SB7Appliction.Properties;

public class MySqlConnector extends DatabaseConnector{
    @Override
    public void connect() {
        System.out.println("Connected to mysql: " + getUrl());
    }
}
