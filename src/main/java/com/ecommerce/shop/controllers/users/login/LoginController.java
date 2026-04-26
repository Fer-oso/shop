package com.ecommerce.shop.controllers.users.login;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cloudinary.http44.api.Response;
import com.ecommerce.shop.configurations.jwt.utils.JwtUtils;
import com.ecommerce.shop.models.DTO.users.CredentialsUser;
import com.ecommerce.shop.models.DTO.users.UserLoginResponseDTO;
import com.ecommerce.shop.services.login.ILoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/shop/auth")
public class LoginController {

        private ILoginService loginService;

        private JwtUtils jwtUtils;

        public LoginController(ILoginService loginService, JwtUtils jwtUtils) {
                this.loginService = loginService;
                this.jwtUtils = jwtUtils;
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody CredentialsUser credentialsUser, HttpServletResponse response) {

                var loginResponse = loginService.loginWithUsernameAndPassword(credentialsUser);

                ResponseCookie cookie = ResponseCookie
                                .from("refresToken", loginResponse.getRefreshToken())
                                .httpOnly(true)
                                .secure(false)
                                .path("/api/shop/auth/refresh")
                                .maxAge((7 * 24 * 60 * 60))
                                .sameSite("Lax")
                                .build();

                response.addHeader("Set-Cookie", cookie.toString());

                response.setStatus(200);

                return ResponseEntity.status(HttpStatus.OK).body(
                                UserLoginResponseDTO.builder()
                                                .id(loginResponse.getId())
                                                .username(loginResponse.getUsername())
                                                .roles(loginResponse.getRoles())
                                                .token(loginResponse.getToken())
                                                .build());
        }

        @PostMapping("/refresh")
        public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {

                Cookie[] cookies = request.getCookies();

                if (cookies == null) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(Map.of("message", "No refresh token found"));
                }

                String refreshToken = Arrays.stream(cookies)
                                .filter(c -> c.getName().equals("refreshToken"))
                                .findFirst()
                                .map(Cookie::getValue)
                                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

                // Validar y generar nuevo access token
                String newAccessToken = jwtUtils.refreshAccessToken(refreshToken);

                if (newAccessToken != null) {

                        // newAccessToken = newAccessToken.substring(7);

                        DecodedJWT decodedJWT = jwtUtils.validateToken(newAccessToken);

                        String username = jwtUtils.extractUsername(decodedJWT);

                        String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

                        Collection<? extends GrantedAuthority> listAuthorities = AuthorityUtils
                                        .commaSeparatedStringToAuthorityList(authorities);

                        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                                        listAuthorities);

                        String newRefreshToken = jwtUtils.createRefreshToken(authentication);

                        System.out.println(newRefreshToken);

                        response.addHeader("Set-Cookie",
                                        "refreshToken=" + newRefreshToken +
                                                        "; Path=/api/shop/auth/refresh" +
                                                        "; Max-Age=" + (7 * 24 * 60 * 60) +
                                                        "; HttpOnly" +
                                                        // "; Secure"+
                                                        "; SameSite=Lax");

                        response.setStatus(200);
                }

                return ResponseEntity.ok(Map.of("token", newAccessToken));
        }
}
