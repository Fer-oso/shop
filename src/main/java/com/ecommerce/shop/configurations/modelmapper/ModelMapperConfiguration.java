package com.ecommerce.shop.configurations.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.shop.models.mappers.CategoryMapper;
import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.models.mappers.RoleMapper;
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
        return new ProductMapper(new ModelMapper());
    }

    @Bean
    UserMapper userMapper(){
        return new UserMapper(new ModelMapper());
    }

    @Bean
    RoleMapper roleMapper(){
        return new RoleMapper(new ModelMapper());
    }

    @Bean
    CategoryMapper categoryMapper(){
        return new CategoryMapper(new ModelMapper());
    }
}
