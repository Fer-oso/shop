package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartBaseDTO;
import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;
import com.ecommerce.shop.models.entitys.shoppingcart.ShoppingCart;

public class ShoppingCartMapper implements IObjectMapper<ShoppingCart, ShoppingCartBaseDTO> {

    ModelMapper modelMapper;

    public ShoppingCartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCart mapDTOToEntity(ShoppingCartBaseDTO shoppingCartDTO) {
        return modelMapper.map(shoppingCartDTO, ShoppingCart.class);
    }

    @Override
    public ShoppingCartDTO mapEntityToDTO(ShoppingCart shoppingCart) {
        return modelMapper.map(shoppingCart, ShoppingCartDTO.class);
    }

    @Override
    public void updateEntityFromDTO(ShoppingCartBaseDTO shoppingCartDTO, ShoppingCart shoppingCart) {
        modelMapper.map(shoppingCartDTO, shoppingCart);
    }

}
