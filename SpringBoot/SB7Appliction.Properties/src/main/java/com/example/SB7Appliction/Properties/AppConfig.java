package com.example.SB7Appliction.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${mysql.url}")
    private String mySqlUrl;

    @Bean("mysqlConnector")
    DatabaseConnector mySqlConfig() {
        DatabaseConnector mySqlConnector = new MySqlConnector();
        System.out.println("Config Mysql Url: " + mySqlUrl);
        mySqlConnector.setUrl(mySqlUrl);

        return mySqlConnector;
    }
}
