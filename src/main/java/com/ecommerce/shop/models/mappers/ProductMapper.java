package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.product.ProductDTO;

import com.ecommerce.shop.models.entitys.products.Bakeable;
import com.ecommerce.shop.models.entitys.products.Brownie;
import com.ecommerce.shop.models.entitys.products.Cookie;
import com.ecommerce.shop.models.entitys.products.Product;

public class ProductMapper implements IObjectMapper<Product, ProductDTO> {

    private ModelMapper modelMapper;

    private BakeableMapper bakeableMapper;

    private CookieMapper cookieMapper;

    private BrownieMapper brownieMapper;

    public ProductMapper(ModelMapper modelMapper, BakeableMapper bakeableMapper, CookieMapper cookieMapper,
            BrownieMapper brownieMapper) {
        this.modelMapper = modelMapper;
        this.bakeableMapper = bakeableMapper;
        this.cookieMapper = cookieMapper;
        this.brownieMapper = brownieMapper;
    }

    @Override
    public Product mapDTOToEntity(ProductDTO dto) {

        if (dto.getCategory() == null) {
            throw new IllegalArgumentException("Product category is required");
        }

        return switch (dto.getCategory().getName()) {
            case "Bakeable" -> modelMapper.map(dto, Bakeable.class);
            case "Cookies" -> modelMapper.map(dto, Cookie.class);
            case "Brownies" -> modelMapper.map(dto, Brownie.class);
            case "Trufas" -> modelMapper.map(dto, Cookie.class);
            default -> throw new IllegalArgumentException("Unsupported category: " + dto.getCategory().getName());
        };
    }

    @Override
    public ProductDTO mapEntityToDTO(Product product) {

        return switch (product.getCategory().getName()) {
            case "Bakeable" -> bakeableMapper.mapEntityToDTO((Bakeable) product);
            case "Cookies" -> cookieMapper.mapEntityToDTO((Cookie) product);
            case "Brownies" -> brownieMapper.mapEntityToDTO((Brownie) product);
            case "Trufas" -> cookieMapper.mapEntityToDTO((Cookie) product);
            default -> throw new IllegalArgumentException("Unsupported category: " + product.getCategory().getName());
        };
    }

}
