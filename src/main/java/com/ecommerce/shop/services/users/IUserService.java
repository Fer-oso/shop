package com.ecommerce.shop.services.users;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ecommerce.shop.models.DTO.users.UserDTO;
import com.ecommerce.shop.services.interfaces.ICrudService;

public interface IUserService extends ICrudService<UserDTO, Long>, UserDetailsService {

}
