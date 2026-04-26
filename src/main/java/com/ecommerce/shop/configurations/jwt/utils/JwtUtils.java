package com.ecommerce.shop.configurations.jwt.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public final class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.private}")
    private String userGenerator;

    @Value("${security.jwt.refresh.private}")
    private String refreshTokenKey;

    private static final long ACCESS_TOKEN_EXPIRATION = 7 * 60 * 1000; // 15 minutos
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 días

    public String createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        String jwtToken = JWT.create()
                .withIssuer(userGenerator)
                .withSubject(authentication.getName())
                .withClaim("authorities", authorities)
                .withClaim("type", "access")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        return jwtToken;
    }

    public DecodedJWT validateToken(String token) {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(privateKey)).withIssuer(userGenerator)
                .withClaim("type", "access").build();

        return verifier.verify(token);
    }

    public String createRefreshToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(authentication.getName())
                .withClaim("authorities", authorities)
                .withClaim("type", "refresh")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(refreshTokenKey));
    }

    public String refreshAccessToken(String refreshToken) {

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(refreshTokenKey))
                    .withClaim("type", "refresh").build();

            DecodedJWT decoded = verifier.verify(refreshToken);

            String username = extractUsername(decoded);

            var authorities = getSpecificClaim(decoded, "authorities").asString();

            return JWT.create()
                    .withIssuer(userGenerator)
                    .withSubject(username)
                    .withClaim("authorities", authorities)
                    .withClaim("type", "access")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(Algorithm.HMAC256(privateKey));

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid or expired refresh token");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {

        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {

        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
