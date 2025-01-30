package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.category.CategoryDTO;
import com.ecommerce.shop.models.entitys.category.Category;

public class CategoryMapper implements IObjectMapper<Category, CategoryDTO> {

    ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Category mapDTOToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    @Override
    public CategoryDTO mapEntityToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public void updateEntityFromDTO(CategoryDTO categoryDTO, Category category) {
        modelMapper.map(categoryDTO, category);
    }

}
