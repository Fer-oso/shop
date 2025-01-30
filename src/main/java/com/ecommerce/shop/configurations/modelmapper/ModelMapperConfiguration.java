package com.ecommerce.shop.configurations.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.shop.models.mappers.CategoryMapper;
import com.ecommerce.shop.models.mappers.ImageMapper;
import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.models.mappers.RoleMapper;
import com.ecommerce.shop.models.mappers.ShoppingCartMapper;
import com.ecommerce.shop.models.mappers.UserLoginResponseMapper;
import com.ecommerce.shop.models.mappers.UserMapper;
import com.ecommerce.shop.models.mappers.buyer.BuyerMapper;
import com.ecommerce.shop.models.mappers.product.ProductShoppingCartMapper;

@Configuration
public class ModelMapperConfiguration {

    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.LOOSE)
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(AccessLevel.PRIVATE);
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
    BuyerMapper buyerMapper() {
        return new BuyerMapper(modelMapper());
    }

    @Bean
    ProductShoppingCartMapper productShoppingCartMapper() {
        return new ProductShoppingCartMapper(modelMapper());
    }
    @Bean
    ShoppingCartMapper shoppingCartMapper() {
        return new ShoppingCartMapper(modelMapper());
    }

    @Bean
    UserLoginResponseMapper userLoginResponseMapper() {
        return new UserLoginResponseMapper(modelMapper());
    }

}
