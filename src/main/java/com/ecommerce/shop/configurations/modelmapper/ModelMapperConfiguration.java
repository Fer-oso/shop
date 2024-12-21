package com.ecommerce.shop.configurations.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.shop.models.mappers.CategoryMapper;
import com.ecommerce.shop.models.mappers.ImageMapper;
import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.models.mappers.RoleMapper;
import com.ecommerce.shop.models.mappers.UserLoginResponseMapper;
import com.ecommerce.shop.models.mappers.UserMapper;

@Configuration
public class ModelMapperConfiguration {

    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

    @Bean
    ProductMapper productMapper() {
        return new ProductMapper(modelMapper());
    }

    @Bean
    UserMapper userMapper() {
        return new UserMapper(modelMapper());
    }

    @Bean
    RoleMapper roleMapper() {
        return new RoleMapper(modelMapper());
    }

    @Bean
    CategoryMapper categoryMapper() {
        return new CategoryMapper(modelMapper());
    }

    @Bean
    ImageMapper imageMapper() {
        return new ImageMapper(modelMapper());
    }

    @Bean
    UserLoginResponseMapper userLoginResponseMapper() {
        return new UserLoginResponseMapper(modelMapper());
    }
}
