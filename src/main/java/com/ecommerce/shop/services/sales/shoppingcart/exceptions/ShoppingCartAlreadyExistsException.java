package com.ecommerce.shop.services.sales.shoppingcart.exceptions;

public class ShoppingCartAlreadyExistsException extends RuntimeException {
    public ShoppingCartAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShoppingCartAlreadyExistsException(String message) {
        super(message);
    }
}
