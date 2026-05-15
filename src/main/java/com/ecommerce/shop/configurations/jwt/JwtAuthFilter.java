package com.ecommerce.shop.configurations.jwt;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.shop.configurations.jwt.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

    JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 👇 Log temporal
        System.out.println("=== JWT FILTER ===");
        System.out.println("URL: " + request.getRequestURI());
        System.out.println("Token presente: " + (jwtToken != null));

        if (jwtToken != null) {

            try {

                jwtToken = jwtToken.substring(7);

                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

                String username = jwtUtils.extractUsername(decodedJWT);

                String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

                // 👇 Log temporal
                System.out.println("Username: " + username);
                System.out.println("Authorities: " + authorities);

                Collection<? extends GrantedAuthority> listAuthorities = AuthorityUtils
                        .commaSeparatedStringToAuthorityList(authorities);

                SecurityContext context = SecurityContextHolder.getContext();

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                        listAuthorities);

                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);

            } catch (Exception e) {
                // 👇 Si el token falla, logeá el error en lugar de silenciarlo
                System.out.println("Error validando token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // 👈 Corta el filtro y devuelve 401
            }

        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/api/shop/auth/login")
                || path.equals("/api/shop/auth/refresh")
                || path.equals("/api/shop/mercadopago/create-preference");
    }

}
