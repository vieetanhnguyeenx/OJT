package com.nva.RealTimeMessenger_v22;

import com.nva.RealTimeMessenger_v22.util.TokenHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HexFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {

        ApplicationContext context = SpringApplication.run(Application.class, args);
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTY4OTg2MzU5NiwiaWQiOjF9.5KSTDlNnY8F2kWRyOQYe0xeyMbpCs3sWsHt2LImrkDk";
        String[] chunks = token.split("\\.");

        String header = new String(TokenHelper.decoder.decode(chunks[0]));
        String payload = new String(TokenHelper.decoder.decode(chunks[1]));
        String tokenWithoutSignature = header + "." + payload;

        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec("doibuon".getBytes(), sa.getJcaName());
        Mac sha256 = Mac.getInstance("HmacSHA256");
        sha256.init(secretKeySpec);

    }

    public static Date between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}




