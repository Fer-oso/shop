package com.ecommerce.shop.services.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.CategoryDTO;
import com.ecommerce.shop.models.category.Category;
import com.ecommerce.shop.models.mappers.CategoryMapper;
import com.ecommerce.shop.repository.category.CategoryRepository;
import com.ecommerce.shop.services.category.exceptions.CategoryNotFoundException;
import com.ecommerce.shop.services.category.exceptions.DuplicateCategoryException;
import com.ecommerce.shop.services.category.exceptions.NullCategoryRequestException;

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

        return Optional.ofNullable(categoryDTO).map(dto -> {

            try {

                return categoryRepository.saveAndFlush(categoryMapper.mapDTOToEntity(dto));

            } catch (Exception e) {

              throw new DuplicateCategoryException("Category duplicated, try with other name");
            }
        })
        .map(category -> categoryMapper.mapEntityToDTO(category))
        .orElseThrow(() -> new NullCategoryRequestException("Category cant be null"));
    }

    @Override
    public CategoryDTO findByName(String name) {
        return categoryRepository.findByName(name).map(category -> categoryMapper.mapEntityToDTO(category)).orElseThrow(()-> new CategoryNotFoundException("category dont exist whit the name: "+ name));
    }

    @Override
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id).map(category -> categoryMapper.mapEntityToDTO(category))
                .orElseThrow(() -> new CategoryNotFoundException("category dont exist whit the name: " + id));
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("category dont exist whit that id: " + id));
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category nont exists with the name: " + name));
    }
}
