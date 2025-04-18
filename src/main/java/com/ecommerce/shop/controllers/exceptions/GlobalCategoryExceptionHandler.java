package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.responsesModels.ResponseErrorModel;
import com.ecommerce.shop.services.category.exceptions.CategoriesNotFoundException;
import com.ecommerce.shop.services.category.exceptions.CategoryNotFoundException;
import com.ecommerce.shop.services.category.exceptions.DuplicateCategoryException;
import com.ecommerce.shop.services.category.exceptions.NullCategoryRequestException;

@Order(2)
@ControllerAdvice
public class GlobalCategoryExceptionHandler {

    @ExceptionHandler(NullCategoryRequestException.class)
    public ResponseEntity<?> handleNullCategoryRequestException(
            NullCategoryRequestException nullCategoryRequestException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("BAD REQUEST")
                .code("400")
                .message(nullCategoryRequestException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("NOT FOUND")
                .code("404")
                .message(categoryNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<?> handleDuplicateCategoryException(DuplicateCategoryException duplicateCategoryException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("CONFLICT")
                .code("409")
                .message(duplicateCategoryException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(CategoriesNotFoundException.class)
    public ResponseEntity<?> handleCategoriesNotFoundException(
            CategoriesNotFoundException categoriesNotFoundException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("404")
                .code("NOT FOUND")
                .message(categoriesNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}