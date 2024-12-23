package com.ecommerce.shop.services.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.configurations.jwt.utils.JwtUtils;

@Service
public class AuthService {

    JwtUtils jwtUtils;


    public AuthService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    private final Map<String, String> userTokenStorage = new HashMap<>();
    
    private void storeToken(String username, String token){

        userTokenStorage.put(username, token);

        System.out.println("Token stored: " + token + " for username: " + username);

        System.out.println("");
    }

    public boolean isValidToken(String token) {

        String username = jwtUtils.extractUsername(jwtUtils.validateToken(token));

        storeToken(username, token);

        String storedToken = userTokenStorage.get(username);

        System.out.println("Validating token. Provided: " + token + ", Stored: " + storedToken);

        return token.equals(userTokenStorage.get(username));
    }
}
