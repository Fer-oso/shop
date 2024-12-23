package com.ecommerce.shop.services.login;

import java.nio.CharBuffer;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.shop.configurations.jwt.utils.JwtUtils;
import com.ecommerce.shop.models.DTO.RoleDTO;
import com.ecommerce.shop.models.DTO.users.CredentialsUser;
import com.ecommerce.shop.models.DTO.users.UserLoginResponseDTO;
import com.ecommerce.shop.models.mappers.RoleMapper;
import com.ecommerce.shop.models.mappers.UserLoginResponseMapper;
import com.ecommerce.shop.models.user.User;
import com.ecommerce.shop.services.auth.AuthService;
import com.ecommerce.shop.services.users.IUserService;

@Service
public class LoginServiceImp implements ILoginService {

    PasswordEncoder passwordEncoder;

    IUserService userService;

    AuthService authService;

    UserLoginResponseMapper userLoginResponseMapper;

    RoleMapper roleMapper;

    JwtUtils jwtUtils;

    public LoginServiceImp(PasswordEncoder passwordEncoder, IUserService userService, AuthService authService,
            UserLoginResponseMapper userLoginResponseMapper, RoleMapper roleMapper, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authService = authService;
        this.userLoginResponseMapper = userLoginResponseMapper;
        this.roleMapper = roleMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserLoginResponseDTO loginWithUsernameAndPassword(CredentialsUser credentialsUser) {

         User user = (User) userService.loadUserByUsername(credentialsUser.getUsername());

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsUser.getPassword()), user.getPassword())) {

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtils.createToken(authentication);

             Set<RoleDTO> roles = Set.copyOf(user.getRoles().stream().map(role ->roleMapper.mapEntityToDTO(role)).toList());

             UserLoginResponseDTO userLoginResponseDTO = UserLoginResponseDTO.builder()
             .id(user.getId())
             .username(user.getUsername())
             .password(user.getPassword())
             .roles(roles)
             .token(token)
             .build();
             
            return userLoginResponseDTO;
        }

        throw new RuntimeException("Invalid password");
    }

}
