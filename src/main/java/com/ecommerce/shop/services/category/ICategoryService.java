package com.ecommerce.shop.services.category;

import com.ecommerce.shop.models.DTO.CategoryDTO;
import com.ecommerce.shop.models.category.Category;
import com.ecommerce.shop.services.interfaces.ICrudService;

public interface ICategoryService extends ICrudService<CategoryDTO, Long>{
    
    Category findCategoryByName(String name);

    Category findCategoryById(Long id);

    CategoryDTO findByName(String name);
}
