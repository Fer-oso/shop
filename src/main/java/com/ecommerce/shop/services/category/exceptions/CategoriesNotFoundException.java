package com.ecommerce.shop.services.category.exceptions;

public class CategoriesNotFoundException extends RuntimeException{
    public CategoriesNotFoundException(String message){
        super(message);
    }
}
