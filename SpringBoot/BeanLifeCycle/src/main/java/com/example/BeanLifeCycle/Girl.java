package com.example.BeanLifeCycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class Girl {
    @PostConstruct
    public void postConstruct() {
        System.out.println("Post Construct");
    }
    @PreDestroy
    public void preDestroy() {
        System.out.println("Pre Destroy");
    }
}
