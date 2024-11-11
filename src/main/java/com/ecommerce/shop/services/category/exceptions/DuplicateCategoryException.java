package com.ecommerce.shop.services.category.exceptions;

public class DuplicateCategoryException extends RuntimeException{

    public DuplicateCategoryException(String message){
        super(message);
    }
}
