package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.response.ResponseModel;
import com.ecommerce.shop.services.category.exceptions.CategoriesNotFoundException;
import com.ecommerce.shop.services.category.exceptions.CategoryNotFoundException;
import com.ecommerce.shop.services.category.exceptions.DuplicateCategoryException;
import com.ecommerce.shop.services.category.exceptions.NullCategoryRequestException;

@ControllerAdvice
public class GlobalCategoryExceptionHandler {

    @ExceptionHandler(NullCategoryRequestException.class)
    public ResponseEntity<?> handleNullCategoryRequestException(
            NullCategoryRequestException nullCategoryRequestException) {

        ResponseModel response = ResponseModel.builder()
                .status("BAD REQUEST")
                .code("400")
                .message(nullCategoryRequestException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {

        ResponseModel response = ResponseModel.builder()
                .status("NOT FOUND")
                .code("404")

                .message(categoryNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<?> handleDuplicateCategoryException(DuplicateCategoryException duplicateCategoryException){

        ResponseModel response = ResponseModel.builder()
        .status("CONFLICT")
        .code("409")
        .message(duplicateCategoryException.getMessage())
        .timestamp(LocalDateTime.now())
        .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(CategoriesNotFoundException.class)
    public ResponseEntity<?> handleCategoriesNotFoundException(CategoriesNotFoundException categoriesNotFoundException){

        ResponseModel response = ResponseModel.builder()
        .status("404")
        .code("NOT FOUND")
        .message(categoriesNotFoundException.getMessage())
        .timestamp(LocalDateTime.now())
        .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}