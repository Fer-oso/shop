package com.ecommerce.shop.controllers.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.ecommerce.shop.configurations.jwt.utils.JWTExpirationException;
import com.ecommerce.shop.controllers.responsesModels.ResponseErrorModel;

@RestControllerAdvice
public class GlobalExceptions {

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<?> handleNullRequestBody(
                        HttpMessageNotReadableException httpMessageNotReadableException) {

                ResponseErrorModel response = ResponseErrorModel.builder()
                                .code(400)
                                .status("BAD REQUEST")
                                .message(httpMessageNotReadableException.getMessage()
                                                + httpMessageNotReadableException.getClass())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(MissingServletRequestPartException.class)
        public ResponseEntity<ResponseErrorModel> handleMissingServletRequestPartException(
                        MissingServletRequestPartException missingServletRequestPartException) {

                ResponseErrorModel response = ResponseErrorModel.builder()
                                .status("BAD REQUEST")
                                .code(400)
                                .message(missingServletRequestPartException.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public ResponseEntity<ResponseErrorModel> handleHttpMediaTypeNotSupportedException(
                        HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {

                ResponseErrorModel response = ResponseErrorModel.builder()
                                .status("UNSUPPORTED_MEDIA_TYPE")
                                .code(415)
                                .message(httpMediaTypeNotSupportedException.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
        }

        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ResponseErrorModel> handleMissingServletRequestParameterException(
                        MissingServletRequestParameterException missingServletRequestParameterException) {

                ResponseErrorModel response = ResponseErrorModel.builder()
                                .status("BAD REQUEST")
                                .code(400)
                                .message(missingServletRequestParameterException.fillInStackTrace().toString())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ResponseErrorModel> handleBadCredentials(
                        BadCredentialsException badCredentialsException) {

                ResponseErrorModel response = ResponseErrorModel.builder()
                                .status("BAD REQUEST")
                                .code(400)
                                .message(badCredentialsException.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(JWTExpirationException.class)
        public ResponseEntity<ResponseErrorModel> handleJWTExpiration(
                        JWTExpirationException jwtExpirationException) {

                ResponseErrorModel response = ResponseErrorModel.builder()
                                .status("UNAUTHORIZED")
                                .code(401)
                                .message(jwtExpirationException.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ResponseErrorModel> handleValidationErrors(
                        MethodArgumentNotValidException methodArgumentNotValidException) {

                // Recolecta todos los errores de validación
                Map<String, String> fieldErrors = new LinkedHashMap<>();

                methodArgumentNotValidException.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> fieldErrors.put(
                                                error.getField(),
                                                error.getDefaultMessage()));

                ResponseErrorModel response = ResponseErrorModel.builder()
                                .status("BAD_REQUEST")
                                .code(400)
                                .errors(fieldErrors)
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
}
