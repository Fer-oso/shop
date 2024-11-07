package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.ecommerce.shop.models.DTO.RoleDTO;
import com.ecommerce.shop.models.DTO.UserDTO;
import com.ecommerce.shop.models.user.Role;
import com.ecommerce.shop.models.user.User;

public class RoleMapper implements IObjectMapper<Role,RoleDTO>{

    ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Override
    public Role mapDTOToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    @Override
    public RoleDTO mapEntityToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
     public void updateEntityFromDTO(RoleDTO roleDTO, Role role) {
        modelMapper.map(roleDTO, role);
    }
}