package com.ecommerce.shop.controllers.users;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.users.UserDTO;
import com.ecommerce.shop.services.auth.AuthService;
import com.ecommerce.shop.services.users.IUserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/shop")
public class UserController {

    IUserService userService;
    AuthService authService;


    public UserController(IUserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        if (!authService.isValidToken(token)) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
     }

        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());

    }
}
