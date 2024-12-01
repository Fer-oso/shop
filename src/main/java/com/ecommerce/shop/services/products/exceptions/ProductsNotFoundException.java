package com.ecommerce.shop.services.products.exceptions;

public class ProductsNotFoundException extends RuntimeException{

    public ProductsNotFoundException(String message){
        super(message);
    }
}
