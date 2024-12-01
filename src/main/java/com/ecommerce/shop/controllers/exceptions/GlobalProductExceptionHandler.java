package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.responsesModels.ResponseErrorModel;
import com.ecommerce.shop.services.products.exceptions.ProductsNotFoundException;
import com.ecommerce.shop.services.products.exceptions.NullProductRequestException;
import com.ecommerce.shop.services.products.exceptions.ProductNotFoundException;

@Order(1)
@ControllerAdvice
public class GlobalProductExceptionHandler {

    @ExceptionHandler(NullProductRequestException.class)
    public ResponseEntity<ResponseErrorModel> handleNullProductRequestException(
            NullProductRequestException nullProductRequestException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("400")
                .code("BAD REQUEST")
                .message(nullProductRequestException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseErrorModel> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("404")
                .code("NOT FOUND")
                .message(productNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ProductsNotFoundException.class)
    public ResponseEntity<ResponseErrorModel> handleProductsNotFoundException(ProductsNotFoundException productsNotFoundException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("404")
                .code("NOT FOUND")
                .message(productsNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
