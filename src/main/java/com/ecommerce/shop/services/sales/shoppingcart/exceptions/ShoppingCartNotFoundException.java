package com.ecommerce.shop.services.sales.shoppingcart.exceptions;

public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}
