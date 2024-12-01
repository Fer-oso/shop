package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.responsesModels.ResponseErrorModel;
import com.ecommerce.shop.services.images.exceptions.ImageNotSelectedException;

@Order(3)
@ControllerAdvice
public class GlobalImageExceptionHandler {
    
    @ExceptionHandler(ImageNotSelectedException.class)
    public ResponseEntity<?> handleImageNotSelectedException(ImageNotSelectedException imageNotSelectedException){

        ResponseErrorModel response = ResponseErrorModel.builder()
        .status("BAD_REQUEST")
        .code("400")
        .message(imageNotSelectedException.getMessage())
        .timestamp(LocalDateTime.now())
        .build(); 

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
