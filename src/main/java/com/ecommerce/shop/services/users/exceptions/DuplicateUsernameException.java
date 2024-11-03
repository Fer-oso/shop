package com.ecommerce.shop.services.users.exceptions;

public class DuplicateUsernameException extends RuntimeException{
    public DuplicateUsernameException(String message){
        super(message);
    }
}
