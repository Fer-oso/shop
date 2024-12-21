package com.ecommerce.shop.services.login;

import java.nio.CharBuffer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.users.CredentialsUser;
import com.ecommerce.shop.models.DTO.users.UserLoginResponseDTO;
import com.ecommerce.shop.models.mappers.UserLoginResponseMapper;
import com.ecommerce.shop.models.user.User;
import com.ecommerce.shop.services.users.IUserService;

@Service
public class LoginServiceImp implements ILoginService {

    PasswordEncoder passwordEncoder;

    IUserService userService;

    UserLoginResponseMapper userLoginResponseMapper;

    public LoginServiceImp(PasswordEncoder passwordEncoder, IUserService userService,
            UserLoginResponseMapper userLoginResponseMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userLoginResponseMapper = userLoginResponseMapper;
    }

    @Override
    public UserLoginResponseDTO loginWithUsernameAndPassword(CredentialsUser credentialsUser) {

        User user = (User) userService.loadUserByUsername(credentialsUser.getUsername());

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsUser.getPassword()), user.getPassword())) {

            // Set<RoleDTO> roles = Set.copyOf(user.getRoles().stream().map(role ->
            // roleMapper.mapEntityToDTO(role)).toList());

            /*
             * UserLoginResponseDTO userLoginResponseDTO = UserLoginResponseDTO.builder()
             * .id(user.getId())
             * .username(user.getUsername())
             * .password(user.getPassword())
             * .roles(roles)
             * .status("authenticated")
             * .build();
             */

            return userLoginResponseMapper.mapEntityToDTO(user);
        }

        throw new RuntimeException("Invalid password");
    }

}
