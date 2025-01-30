package com.ecommerce.shop.models.mappers.product;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ecommerce.shop.models.DTO.product.ProductShoppingCartDTO;
import com.ecommerce.shop.models.entitys.products.ProductShoppingCart;
import com.ecommerce.shop.models.mappers.IObjectMapper;

@Component
public class ProductShoppingCartMapper implements IObjectMapper<ProductShoppingCart, ProductShoppingCartDTO> {

    ModelMapper modelMapper;

    public ProductShoppingCartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductShoppingCart mapDTOToEntity(ProductShoppingCartDTO productShoppingCartDTO) {
        
        return modelMapper.map(productShoppingCartDTO, ProductShoppingCart.class);
    }

    @Override
    public ProductShoppingCartDTO mapEntityToDTO(ProductShoppingCart productShoppingCart) {
        return modelMapper.map(productShoppingCart, ProductShoppingCartDTO.class);
    }

    @Override
    public void updateEntityFromDTO(ProductShoppingCartDTO dto, ProductShoppingCart entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEntityFromDTO'");
    }


}
