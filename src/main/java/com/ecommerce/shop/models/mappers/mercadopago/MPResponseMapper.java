package com.ecommerce.shop.models.mappers.mercadopago;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.mercadopago.MPResponseDTO;
import com.ecommerce.shop.models.mappers.IObjectMapper;
import com.mercadopago.net.MPResponse;

public class MPResponseMapper implements IObjectMapper<MPResponse, MPResponseDTO> {

    ModelMapper modelMapper;

    public MPResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MPResponse mapDTOToEntity(MPResponseDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapDTOToEntity'");
    }

    @Override
    public MPResponseDTO mapEntityToDTO(MPResponse entity) {
        return modelMapper.map(entity, MPResponseDTO.class);
    }

    @Override
    public void updateEntityFromDTO(MPResponseDTO dto, MPResponse entity) {

    }

}
