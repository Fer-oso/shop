package com.ecommerce.shop.services.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.category.CategoryDTO;
import com.ecommerce.shop.models.entitys.category.Category;
import com.ecommerce.shop.models.mappers.CategoryMapper;
import com.ecommerce.shop.repository.category.CategoryRepository;
import com.ecommerce.shop.services.category.exceptions.CategoriesNotFoundException;
import com.ecommerce.shop.services.category.exceptions.CategoryNotFoundException;
import com.ecommerce.shop.services.category.exceptions.DuplicateCategoryException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryServiceImp implements ICategoryService {

    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    public CategoryServiceImp(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {

        return Optional.of(categoryDTO).filter( category -> !categoryRepository.existsByName(category.getName()))
        .map(category -> {
             return  categoryMapper.mapEntityToDTO(categoryRepository.saveAndFlush(categoryMapper.mapDTOToEntity(category)));
        }).orElseThrow(()-> new DuplicateCategoryException("Category already exists"));
    }

    @Override
    public CategoryDTO findByName(String name) {
        return categoryRepository.findByName(name).map(category -> categoryMapper.mapEntityToDTO(category)).orElseThrow(()-> new CategoryNotFoundException("category dont exist whit the name: "+ name));
    }

    @Override
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id).map(category -> categoryMapper.mapEntityToDTO(category))
                .orElseThrow(() -> new CategoryNotFoundException("category dont exist whit the id: " + id));
    }

    @Override
    public CategoryDTO update(CategoryDTO t, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public String deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public List<CategoryDTO> findAll() {
       List<Category> categoriesList = categoryRepository.findAll();

       if (categoriesList.size() == 0) {
        throw new CategoriesNotFoundException("No categories created");
       }

       return categoriesList.stream().map(category -> categoryMapper.mapEntityToDTO(category)).toList();
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("category dont exist whit that id: " + id));
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category dont exists with the name: " + name + ". First create the category"));
    }

    
}
