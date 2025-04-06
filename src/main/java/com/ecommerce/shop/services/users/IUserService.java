package com.ecommerce.shop.services.users;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.DTO.users.UserDTO;

public interface IUserService extends UserDetailsService {

    UserDTO save(UserDTO userDTO, List<MultipartFile> files);

    UserDTO findById(Long id);

    UserDTO update(UserDTO userDTO, Long id);

    String deleteById(Long id);

    List<UserDTO> findAll();
}
