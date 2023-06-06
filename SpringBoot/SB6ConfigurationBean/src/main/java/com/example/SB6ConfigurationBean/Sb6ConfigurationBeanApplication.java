package com.example.SB6ConfigurationBean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Sb6ConfigurationBeanApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Sb6ConfigurationBeanApplication.class, args);

		DatabaseConnector mysql = (DatabaseConnector) context.getBean("mysqlConnector");
		mysql.connect();
		System.out.println(mysql);
		DatabaseConnector mysql1 = (DatabaseConnector) context.getBean("mysqlConnector");
		mysql.connect();
		System.out.println(mysql1);
		DatabaseConnector sqlServer = (DatabaseConnector) context.getBean("sqlServerConnector");
		sqlServer.connect();

		DatabaseConnector mongoDB = (DatabaseConnector) context.getBean("mongoDBConnector");
		mongoDB.connect();
	}

}
