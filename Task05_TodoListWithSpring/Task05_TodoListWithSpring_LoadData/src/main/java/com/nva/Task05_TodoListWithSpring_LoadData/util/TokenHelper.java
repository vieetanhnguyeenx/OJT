package com.nva.Task05_TodoListWithSpring_LoadData.util;

import com.google.gson.Gson;
import com.nva.Task05_TodoListWithSpring_LoadData.model.Response;
import org.springframework.http.HttpStatus;

import java.util.Base64;

public class TokenHelper {
    public static Base64.Encoder encoder = Base64.getEncoder();

    public static Base64.Decoder decoder = Base64.getDecoder();

    public static Gson gson = new Gson();

    public static void main(String[] args) {
        Response response = new Response("doiden", "nho", HttpStatus.SEE_OTHER,
                "http://localhost:8888/todolist?token",
                null, null, null);
        String json = gson.toJson(response);
        System.out.println(json);

        response = gson.fromJson(json, Response.class);
        System.out.println(response);
        System.out.println(response.getStatusCode());
    }
}
