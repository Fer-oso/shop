package com.ecommerce.shop.configurations.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        if (response.isCommitted()) {
            return;
        }

        System.out.println("=== AUTH ENTRY POINT DISPARADO ===");
        System.out.println("URL: " + request.getRequestURI());
        System.out.println("Excepción: " + authException.getMessage());

        // ✅ Si la respuesta ya fue commited, no hacer nada

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"status\": 401, \"message\": \"Please log in to access this resource.\"}");
    }

}
