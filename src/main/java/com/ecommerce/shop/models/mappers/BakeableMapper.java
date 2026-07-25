package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.product.BakeableDTO;
import com.ecommerce.shop.models.entitys.products.Bakeable;

public class BakeableMapper implements IObjectMapper<Bakeable, BakeableDTO> {

    ModelMapper modelMapper;

    public BakeableMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Bakeable mapDTOToEntity(BakeableDTO bakeableDTO) {
        return modelMapper.map(bakeableDTO, Bakeable.class);
    }

    @Override
    public BakeableDTO mapEntityToDTO(Bakeable bakeable) {
        return modelMapper.map(bakeable, BakeableDTO.class);
    }

}
