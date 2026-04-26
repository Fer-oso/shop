package com.ecommerce.shop.configurations.jwt.utils;

public class JWTExpirationException extends RuntimeException {

    public JWTExpirationException(String message) {
        super(message);
    }
}
