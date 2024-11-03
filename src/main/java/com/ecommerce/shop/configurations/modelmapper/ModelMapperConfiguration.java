package com.ecommerce.shop.configurations.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.models.mappers.RoleMapper;
import com.ecommerce.shop.models.mappers.UserMapper;

@Configuration
public class ModelMapperConfiguration {

    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    ProductMapper productMapper() {
        return new ProductMapper(new ModelMapper());
    }

    @Bean
    UserMapper userMapper(){
        return new UserMapper(new ModelMapper());
    }

    @Bean
    RoleMapper rolemapper(){
        return new RoleMapper(new ModelMapper());
    }
}
