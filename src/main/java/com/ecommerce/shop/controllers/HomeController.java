package com.ecommerce.shop.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class HomeController {

    @GetMapping("/home")
    @PreAuthorize("hasAuthority('READ')")
    public String home() {
        return "welcome home";
    }

    @GetMapping("/home-secured")
    @PreAuthorize("hasAnyAuthority('READ','UPDATE')")
    public String homeSecured() {
        return "welcome home secured";
    }

    @GetMapping("/home-secured2")
    @PreAuthorize("hasRole('ADMIN')")
    public String homeSecured2() {
        return "welcome home secured2";
    }
}