package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.response.ResponseModel;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleNullRequestBody(HttpMessageNotReadableException httpMessageNotReadableException) {

        ResponseModel response = ResponseModel.builder()
                .code("400")
                .status("BAD REQUEST")
                .message(httpMessageNotReadableException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
