package com.ecommerce.shop.models.mappers.buyer;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.buyer.BuyerDTO;
import com.ecommerce.shop.models.entitys.buyer.Buyer;
import com.ecommerce.shop.models.mappers.IObjectMapper;

public class BuyerMapper implements IObjectMapper<Buyer, BuyerDTO>{

    ModelMapper modelMapper;

    public BuyerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Buyer mapDTOToEntity(BuyerDTO buyerDTO) {
            return modelMapper.map(buyerDTO, Buyer.class);
    }

    @Override
    public BuyerDTO mapEntityToDTO(Buyer buyer) {
       return modelMapper.map(buyer, BuyerDTO.class);
    }

    @Override
    public void updateEntityFromDTO(BuyerDTO buyerDTO, Buyer buyer) {
        modelMapper.map(buyerDTO,buyer);
    }
    
}
