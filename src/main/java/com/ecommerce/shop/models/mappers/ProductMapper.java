package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.models.products.Product;

public class ProductMapper implements IObjectMapper<Product, ProductDTO> {

    ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Override
    public Product mapDTOToEntity(ProductDTO dto) {
        return modelMapper.map(dto, Product.class);
    }

    @Override
    public ProductDTO mapEntityToDTO(Product entity) {
        return modelMapper.map(entity, ProductDTO.class);
    }

    @Override
    public void updateEntityFromDTO(ProductDTO productDTO, Product product) {
        modelMapper.map(productDTO, product);
    }

}
