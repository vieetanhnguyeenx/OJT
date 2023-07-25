package com.example.DemoJPA;

import com.example.DemoJPA.model.User;
import com.example.DemoJPA.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoJpaApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoJpaApplication.class, args);
        UserRespository userRespository = context.getBean(UserRespository.class);

        userRespository.findAll().forEach(System.out::println);
//		User user = userRespository.save(new User());
//		System.out.println(user.getId());
//		user.setAgi(100);
//		userRespository.save(user);

//		List<User> user = userRespository.findAllByAtk(98);
//		System.out.println(user);

    }

}
