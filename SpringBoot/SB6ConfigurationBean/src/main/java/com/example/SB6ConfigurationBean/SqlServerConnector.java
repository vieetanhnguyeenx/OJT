package com.example.SB6ConfigurationBean;

public class SqlServerConnector extends DatabaseConnector {
    @Override
    public void connect() {
        System.out.println("Connected to SqlServer");
    }
}
