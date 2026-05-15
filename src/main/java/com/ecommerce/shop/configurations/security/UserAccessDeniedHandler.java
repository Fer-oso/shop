package com.ecommerce.shop.configurations.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.ecommerce.shop.controllers.responsesModels.ResponseErrorModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class UserAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ObjectMapper mapper = new ObjectMapper();

        ResponseErrorModel error = ResponseErrorModel.builder()
                .status("FORBIDDEN")
                .code(400)
                .message("You don't have permission to access this resource.")
                .timestamp(LocalDateTime.now())
                .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        mapper.writeValue(response.getWriter(), error);
    }
}
