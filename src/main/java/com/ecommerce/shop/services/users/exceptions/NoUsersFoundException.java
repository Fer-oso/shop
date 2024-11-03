package com.ecommerce.shop.services.users.exceptions;

public class NoUsersFoundException extends RuntimeException{
    public NoUsersFoundException(String message){
        super(message);
    }
}
