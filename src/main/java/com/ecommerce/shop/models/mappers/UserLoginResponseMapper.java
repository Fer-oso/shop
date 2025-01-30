package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.users.UserLoginResponseDTO;
import com.ecommerce.shop.models.entitys.user.User;

public class UserLoginResponseMapper implements IObjectMapper<User, UserLoginResponseDTO> {

    ModelMapper modelMapper;

    public UserLoginResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User mapDTOToEntity(UserLoginResponseDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapDTOToEntity'");
    }

    @Override
    public UserLoginResponseDTO mapEntityToDTO(User entity) {
        return modelMapper.map(entity, UserLoginResponseDTO.class);
    }

    @Override
    public void updateEntityFromDTO(UserLoginResponseDTO dto, User entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEntityFromDTO'");
    }

}
