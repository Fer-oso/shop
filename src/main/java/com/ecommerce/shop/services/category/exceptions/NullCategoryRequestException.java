package com.ecommerce.shop.services.category.exceptions;

public class NullCategoryRequestException extends RuntimeException{
    
    public NullCategoryRequestException(String message){
        super(message);
    }
}
