package com.example.SB6ConfigurationBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean("mysqlConnector")
    public MySqlConnector mySqlConnector() {
        MySqlConnector mySqlConnector = new MySqlConnector();
        mySqlConnector.setUrl("doiden:8080:mysql");
        return mySqlConnector;
    }

    @Bean("sqlServerConnector")
    public SqlServerConnector sqlServerConnector() {
        SqlServerConnector sqlServerConnector = new SqlServerConnector();
        sqlServerConnector.setUrl("doibuon:8081:sqlServer");
        return sqlServerConnector;
    }
    @Bean("mongoDBConnector")
    public MongoDBConnector mongoDBConnector() {
        MongoDBConnector mongoDBConnector = new MongoDBConnector();
        mongoDBConnector.setUrl("thitcho:9231:mongoDB");
        return mongoDBConnector;
    }
}
