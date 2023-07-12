package com.ini.interfaceAPI.util;

import com.ini.interfaceAPI.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Base64;

public class GenerateKey {
    public static String generate(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[64]; // 64 bytes = 512 bits
        secureRandom.nextBytes(keyBytes);
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        return base64Key;
    }
}
