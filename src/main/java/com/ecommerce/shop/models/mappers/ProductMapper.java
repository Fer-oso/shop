package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.product.ProductDTO;
import com.ecommerce.shop.models.entitys.products.Product;

public class ProductMapper implements IObjectMapper<Product, ProductDTO> {

    ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
