package com.ecommerce.shop.services.users.exceptions;

public class RoleNotFoundException extends RuntimeException{
    
    public RoleNotFoundException(String message){
        
        super(message);
    }
}
