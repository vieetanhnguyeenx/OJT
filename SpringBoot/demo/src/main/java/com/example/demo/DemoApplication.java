package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		Outfit outfit = (Outfit) context.getBean("bikini");
		outfit.wear();
		Girl girl = context.getBean(Girl.class);
		girl.outfit.wear();
	}

}
