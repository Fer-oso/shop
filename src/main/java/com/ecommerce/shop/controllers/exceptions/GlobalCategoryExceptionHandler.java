package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.response.ResponseModel;
import com.ecommerce.shop.services.category.exceptions.CategoryNotFoundException;
import com.ecommerce.shop.services.category.exceptions.NullCategoryRequestException;

@ControllerAdvice
public class GlobalCategoryExceptionHandler {
    
    
    @ExceptionHandler(NullCategoryRequestException.class)
    public ResponseEntity<?> handleNullCategoryRequestException(NullCategoryRequestException nullCategoryRequestException){

        ResponseModel response = ResponseModel.builder()
        .code("400")
        .status("BAD REQUEST")
        .message(nullCategoryRequestException.getMessage())
        .timestamp( LocalDateTime.now())
        .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException){

        ResponseModel response = ResponseModel.builder()
        .code("404")
        .status("NOT FOUND")
        .message(categoryNotFoundException.getMessage())
        .timestamp(LocalDateTime.now())
        .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
