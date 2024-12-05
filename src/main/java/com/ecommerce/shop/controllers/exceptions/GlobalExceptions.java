package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.ecommerce.shop.controllers.responsesModels.ResponseErrorModel;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleNullRequestBody(HttpMessageNotReadableException httpMessageNotReadableException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("BAD REQUEST")
                .code("400")
                .message(httpMessageNotReadableException.getMessage() + httpMessageNotReadableException.getClass())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ResponseErrorModel> handleMissingServletRequestPartException(
            MissingServletRequestPartException missingServletRequestPartException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("400")
                .code("BAD_REQUEST")
                .message(missingServletRequestPartException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseErrorModel> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("415")
                .code("UNSUPPORTED_MEDIA_TYPE")
                .message(httpMediaTypeNotSupportedException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseErrorModel> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException missingServletRequestParameterException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("400")
                .code("BAD_REQUEST")
                .message(missingServletRequestParameterException.fillInStackTrace().toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
