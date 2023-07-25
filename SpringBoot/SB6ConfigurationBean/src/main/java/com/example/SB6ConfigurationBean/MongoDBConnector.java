package com.example.SB6ConfigurationBean;

public class MongoDBConnector extends DatabaseConnector {
    @Override
    public void connect() {
        System.out.println("Connected to MongoDB");
    }
}
