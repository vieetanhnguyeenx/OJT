package com.nva.RealTimeMessenger_v21.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.TimeZone;

@Configuration
public class GsonConfig {
    @Bean
    public Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SS").create();
    }

}
