package com.ecommerce.shop.controllers.categorys;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.CategoryDTO;
import com.ecommerce.shop.services.category.ICategoryService;

@RestController
@RequestMapping("/api/shop/category")
public class CategoryController {
    
    ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(id));
    }
    
    @GetMapping("/categories/{name}")
    public ResponseEntity<CategoryDTO> findCategoryByName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(name));
    }
}
