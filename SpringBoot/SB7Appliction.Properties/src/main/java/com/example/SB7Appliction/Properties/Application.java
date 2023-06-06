package com.example.SB7Appliction.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	@Value("${mysql.url}")
	private static String url;
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		DatabaseConnector databaseConnector = (DatabaseConnector) context.getBean("mysqlConnector");
		databaseConnector.connect();
	}

}
