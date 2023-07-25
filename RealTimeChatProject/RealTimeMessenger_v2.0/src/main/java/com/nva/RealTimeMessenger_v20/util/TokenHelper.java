package com.nva.RealTimeMessenger_v20.util;

import com.google.gson.Gson;

import java.util.Base64;

public class TokenHelper {
    public static Base64.Encoder encoder = Base64.getEncoder();

    public static Base64.Decoder decoder = Base64.getDecoder();

    public static Gson gson = new Gson();

    public static void main(String[] args) {
    }
}
