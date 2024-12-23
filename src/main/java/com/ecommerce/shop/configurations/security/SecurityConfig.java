package com.ecommerce.shop.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ecommerce.shop.configurations.jwt.JwtAuthFilter;
import com.ecommerce.shop.configurations.jwt.utils.JwtUtils;
import com.ecommerce.shop.services.users.UserServiceImp;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private JwtUtils jwtUtils;

    public SecurityConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.addFilterBefore(new JwtAuthFilter(jwtUtils), BasicAuthenticationFilter.class);

       // httpSecurity.exceptionHandling(
       //         exceptionHandling -> exceptionHandling.authenticationEntryPoint(new UserAuthenticationEntryPoint()));

        httpSecurity.sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /*
         * httpSecurity.authorizeHttpRequests(httpRequest -> {
         * 
         * httpRequest.requestMatchers(HttpMethod.GET,
         * "/api/shop/users").hasAnyAuthority("CREATE", "UPDATE", "READ", "DELETE");
         * 
         * httpRequest.requestMatchers(HttpMethod.GET,
         * "/api/shop/users/*").hasAnyRole("ADMIN");
         * 
         * httpRequest.requestMatchers("/auth/home").permitAll();
         * 
         * httpRequest.requestMatchers("/auth/home-public").permitAll();
         * 
         * httpRequest.requestMatchers("/auth/home-secured").hasAnyAuthority("CREATE",
         * "UPDATE", "READ", "DELETE");
         * 
         * httpRequest.requestMatchers(HttpMethod.GET,
         * "/auth/home-secured2").hasRole("ADMIN");
         * 
         * });
         */

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public AuthenticationProvider authenticationProvider(UserServiceImp userServiceImp) {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userServiceImp);

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173/");
        configuration.addAllowedMethod("*"); // Permitir todos los métodos
        configuration.addAllowedHeader("*"); // Permitir todos los headers
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
