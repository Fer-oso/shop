package com.ecommerce.shop.services.utils.users;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ecommerce.shop.models.entitys.user.User;
import com.ecommerce.shop.services.users.IUserService;

@Component
public final class UserUtils {

    IUserService userService;

    public UserUtils(IUserService userService) {

        this.userService = userService;
    }

    public Optional<User> checkExistenceByUsername(String username) {
        return Optional.ofNullable((User) userService.loadUserByUsername(username));

    }
}
