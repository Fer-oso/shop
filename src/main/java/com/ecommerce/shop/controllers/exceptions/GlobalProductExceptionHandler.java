package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.responsesModels.ResponseErrosModel;
import com.ecommerce.shop.services.products.exceptions.NoProductsFoundException;
import com.ecommerce.shop.services.products.exceptions.NullProductRequestException;
import com.ecommerce.shop.services.products.exceptions.ProductNotFoundException;

@ControllerAdvice
public class GlobalProductExceptionHandler {

    @ExceptionHandler(NullProductRequestException.class)
    public ResponseEntity<?> handleNullProductRequestException(
            NullProductRequestException nullProductRequestException) {

        ResponseErrosModel response = ResponseErrosModel.builder()
                .status("400")
                .code("BAD REQUEST")
                .message(nullProductRequestException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {

        ResponseErrosModel response = ResponseErrosModel.builder()
                .status("404")
                .code("NOT FOUND")
                .message(productNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoProductsFoundException.class)
    public ResponseEntity<?> handleNoProductsFoundException(NoProductsFoundException noProductsFoundException) {

        ResponseErrosModel response = ResponseErrosModel.builder()
                .status("404")
                .code("NOT FOUND")
                .message(noProductsFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
