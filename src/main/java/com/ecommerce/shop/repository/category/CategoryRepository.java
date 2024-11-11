package com.ecommerce.shop.repository.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    Optional<Category> findByName(String name);
}
