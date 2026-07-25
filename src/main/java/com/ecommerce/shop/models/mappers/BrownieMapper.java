package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.product.BrownieDTO;
import com.ecommerce.shop.models.entitys.products.Brownie;

public class BrownieMapper implements IObjectMapper<Brownie, BrownieDTO> {

    ModelMapper modelMapper;

    public BrownieMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Brownie mapDTOToEntity(BrownieDTO brownieDTO) {
        return modelMapper.map(brownieDTO, Brownie.class);
    }

    @Override
    public BrownieDTO mapEntityToDTO(Brownie brownie) {
        return modelMapper.map(brownie, BrownieDTO.class);
    }

}
