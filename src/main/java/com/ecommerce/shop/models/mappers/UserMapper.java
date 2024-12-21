package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.ecommerce.shop.models.DTO.users.UserDTO;
import com.ecommerce.shop.models.user.User;

public class UserMapper implements IObjectMapper<User, UserDTO> {

    ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Override
    public User mapDTOToEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDTO mapEntityToDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    @Override
    public void updateEntityFromDTO(UserDTO userDTO, User user) {
        modelMapper.map(userDTO, user);
    }
}
