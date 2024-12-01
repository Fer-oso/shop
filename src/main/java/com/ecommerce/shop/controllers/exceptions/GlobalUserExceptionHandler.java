package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.shop.controllers.responsesModels.ResponseErrorModel;
import com.ecommerce.shop.services.users.exceptions.DuplicateUsernameException;
import com.ecommerce.shop.services.users.exceptions.NoUsersFoundException;
import com.ecommerce.shop.services.users.exceptions.NullUserRequestException;
import com.ecommerce.shop.services.users.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalUserExceptionHandler {

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<?> handleDuplicateUsername(DuplicateUsernameException duplicateUsernameException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("CONFLICT")
                .code("409")
                .message(duplicateUsernameException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(NullUserRequestException.class)
    public ResponseEntity<?> handleNullUserRequestException(NullUserRequestException nullUserRequestException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("BAD REQUEST")
                .code("400")
                .message("User cant be null")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("NOT FOUND")
                .code("404")
                .message(userNotFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<?> handleNoUsersFound(NoUsersFoundException noUsersFoundException) {

        ResponseErrorModel response = ResponseErrorModel.builder()
                .status("NOt FOUND")
                .code("404")
                .message(noUsersFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
