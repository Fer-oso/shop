package com.ecommerce.shop.services.products.exceptions;

public class NoProductsFoundException extends RuntimeException{

    public NoProductsFoundException(String message){
        super(message);
    }
}
