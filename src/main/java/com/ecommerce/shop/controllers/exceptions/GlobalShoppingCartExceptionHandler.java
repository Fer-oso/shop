package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.responsesModels.ResponseErrorModel;
import com.ecommerce.shop.services.sales.shoppingcart.exceptions.ShoppingCartAlreadyExistsException;
import com.ecommerce.shop.services.sales.shoppingcart.exceptions.ShoppingCartNotFoundException;
import com.ecommerce.shop.services.sales.shoppingcart.exceptions.ShoppingCartPersistenceException;

public class GlobalShoppingCartExceptionHandler {

    @ExceptionHandler(ShoppingCartPersistenceException.class)
    public ResponseEntity<ResponseErrorModel> handleCartPersistenceException(
            ShoppingCartPersistenceException shoppingCartPersistenceException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .code(500)
                .status("INTERNAL SERVER ERROR")
                .message(shoppingCartPersistenceException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ShoppingCartAlreadyExistsException.class)
    public ResponseEntity<ResponseErrorModel> handleShoppingCartAlreadyExistsException(
            ShoppingCartAlreadyExistsException shoppingCartAlreadyExistsException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .code(409)
                .status("CONFLICT")
                .message(shoppingCartAlreadyExistsException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ShoppingCartNotFoundException.class)
    public ResponseEntity<ResponseErrorModel> handleShoppingCartNotFoundException(
            ShoppingCartNotFoundException shoppingCartNotFoundException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .code(404)
                .status("NOT FOUND")
                .message(shoppingCartNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
