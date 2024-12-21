package com.ecommerce.shop.controllers.users.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.users.CredentialsUser;
import com.ecommerce.shop.services.login.ILoginService;

@RestController
@RequestMapping("/api/shop/auth")
public class LoginController {

    private ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialsUser credentialsUser) {

        return ResponseEntity.status(HttpStatus.OK).body(loginService.loginWithUsernameAndPassword(credentialsUser));
    }
}
