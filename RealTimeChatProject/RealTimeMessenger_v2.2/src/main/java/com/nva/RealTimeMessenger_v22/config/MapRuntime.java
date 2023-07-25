package com.nva.RealTimeMessenger_v22.config;

import com.nva.RealTimeMessenger_v22.entity.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MapRuntime {
    public static Map<Integer, Message> integerMessageMap = new HashMap<>();

    @Bean
    public Map<Integer, Message> getIntegerMessageMap() {
        return integerMessageMap;
    }
}
