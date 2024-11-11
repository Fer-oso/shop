package com.ecommerce.shop.repository.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.products.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByCategoryName(String category);
    
    List<Product> findProductsByBrand(String brand);

    List<Product> findProductsByBrandAndName(String brand, String name);

    List<Product> findProductsByCategoryNameAndBrand(String category, String brand);

    Long countProductsByBrandAndName(String brand, String name);
}
