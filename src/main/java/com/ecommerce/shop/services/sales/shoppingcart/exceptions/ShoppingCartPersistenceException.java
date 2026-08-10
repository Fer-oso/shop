package com.ecommerce.shop.services.sales.shoppingcart.exceptions;

public class ShoppingCartPersistenceException extends RuntimeException {
    public ShoppingCartPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShoppingCartPersistenceException(String message) {
        super(message);
    }
}
