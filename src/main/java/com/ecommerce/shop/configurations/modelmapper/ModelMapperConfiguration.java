package com.ecommerce.shop.configurations.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.shop.models.mappers.ProductMapper;

@Configuration
public class ModelMapperConfiguration {

    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    ProductMapper productMapper() {
        return new ProductMapper(new ModelMapper());
    }

}
