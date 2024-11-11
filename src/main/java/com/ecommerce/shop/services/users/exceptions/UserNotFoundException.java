package com.ecommerce.shop.services.users.exceptions;

public class UserNotFoundException extends RuntimeException{
    
    public UserNotFoundException(String message){
        super(message);
    }
}
