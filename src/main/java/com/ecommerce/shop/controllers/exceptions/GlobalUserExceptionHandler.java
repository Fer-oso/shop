package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.response.ResponseModel;
import com.ecommerce.shop.services.users.exceptions.DuplicateUsernameException;
import com.ecommerce.shop.services.users.exceptions.NoUsersFoundException;
import com.ecommerce.shop.services.users.exceptions.NullUserRequestException;
import com.ecommerce.shop.services.users.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalUserExceptionHandler {
    
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<?> handleDuplicateUsername(DuplicateUsernameException duplicateUsernameException){

        ResponseModel response = ResponseModel.builder()
                .code("409")
                .status("CONFLICT")
                .message(duplicateUsernameException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(NullUserRequestException.class)
    public ResponseEntity<?> handleNullUserRequestException(NullUserRequestException nullUserRequestException){
       
        ResponseModel response = ResponseModel.builder()
        .code("400")
        .status("BAD REQUEST")
        .message("User cant be null")
        .timestamp(LocalDateTime.now())
        .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        ResponseModel response = ResponseModel.builder()
        .code("404")
        .status("NOT FOUND")
        .message(userNotFoundException.getMessage())
        .timestamp(LocalDateTime.now())
        .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<?> handleNoUsersFound(NoUsersFoundException noUsersFoundException){
       
        ResponseModel response = ResponseModel.builder()
        .code("404")
        .status("NOt FOUND")
        .message(noUsersFoundException.getMessage())
        .timestamp(LocalDateTime.now())
        .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
