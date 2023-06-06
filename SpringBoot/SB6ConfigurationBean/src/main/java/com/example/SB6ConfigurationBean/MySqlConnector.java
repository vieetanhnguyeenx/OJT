package com.example.SB6ConfigurationBean;

public class MySqlConnector extends DatabaseConnector{
    @Override
    public void connect() {
        System.out.println("Connected to Mysql");
    }
}
