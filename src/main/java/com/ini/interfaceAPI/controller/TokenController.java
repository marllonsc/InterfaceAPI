package com.ini.interfaceAPI.controller;

import com.ini.interfaceAPI.entity.User;
import com.ini.interfaceAPI.service.UserService;
import com.ini.interfaceAPI.util.GenerateKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("/api")
public class TokenController {
    private static String SECRET_KEY = "";

    //long expirationTime = 3_600_000; // 1 hour in milliseconds
    // long expirationTime = 60_000; // 1 minute in milliseconds
    private static final long EXPIRATION_TIME = 30_000; // 10 days

    @Autowired
    UserService service;

    @GetMapping("/generate-token")
    public ResponseEntity<String> generateToken(HttpServletRequest request) {

        String login = request.getHeader("login");
        String password = request.getHeader("password");
        User user = service.logar(login,password);

        if(user!=null) {

            //byte[] secretKeyBytes = SECRET_KEY.getBytes();

            if(!StringUtils.isEmpty(user.getCurrentToken()) && verifyToken(user.getCurrentToken(),user.getCurrentKey())){
                return ResponseEntity.ok(user.getCurrentToken());
            }

            if(StringUtils.isEmpty(user.getCurrentKey())){
                SECRET_KEY = GenerateKey.generate();
                user.setCurrentKey(SECRET_KEY);
            }else{
                SECRET_KEY = user.getCurrentKey();
            }

            byte[] secretKeyBytes = SECRET_KEY.getBytes();
            Key signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

            String token = Jwts.builder()
                    .setSubject("user-id") // Set any desired subject for the token
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(signingKey, SignatureAlgorithm.HS512)
                    .compact();

            user.setCurrentToken(token);
            service.createUser(user);
            return ResponseEntity.ok(token);
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden Access");
        }

    }

    @GetMapping("/verify-token")
    public boolean verifyToken(HttpServletRequest request) {
        String login = request.getHeader("login");
        String password = request.getHeader("password");
        User user = service.logar(login,password);

       return verifyToken(user.getCurrentToken(),user.getCurrentKey());
    }


    private boolean verifyToken(String token, String key) {
        byte[] secretKeyBytes = key.getBytes();
        Key signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            // Token is valid, you can access the claims or perform any additional checks
            // For example, to get the subject of the token:
            String subject = claims.getBody().getSubject();
            System.out.println("Token Subject: " + subject);
            
            return true;
        } catch (Exception e) {
            // Token is invalid or has expired
            e.printStackTrace();
            return false;
        }
    }

}
