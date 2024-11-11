package com.ecommerce.shop.services.products.exceptions;

public class NullProductRequestException extends RuntimeException{

    public NullProductRequestException(String message){
        super(message);
    }
}
