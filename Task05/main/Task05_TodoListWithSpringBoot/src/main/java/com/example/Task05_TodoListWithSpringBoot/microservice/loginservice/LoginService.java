//package com.example.Task05_TodoListWithSpringBoot.microservice.loginservice;
//
//import com.example.Task05_TodoListWithSpringBoot.model.Request;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class LoginService {
//    public static Map<String, Request> data = new HashMap<>();
//
//    public static void main(String[] args) {
//        LoginConsumer loginServiceConsumer = new LoginConsumer();
//        loginServiceConsumer.start();
//
//        LoginProducer loginProducer = new LoginProducer();
//        loginProducer.start();
//    }
//}
