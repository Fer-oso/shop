package com.ecommerce.shop.services.users.exceptions;

public class NullUserRequestException extends RuntimeException{
    
    public NullUserRequestException(String message){
        super(message);
    }
}
