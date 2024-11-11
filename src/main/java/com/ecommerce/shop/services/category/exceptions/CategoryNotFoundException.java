package com.ecommerce.shop.services.category.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
