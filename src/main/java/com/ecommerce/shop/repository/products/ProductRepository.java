package com.ecommerce.shop.repository.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.entitys.products.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findProductsByName(@Param("name") String name);

    List<Product> findProductsByCategoryName(String category);

    List<Product> findProductsByBrand(String brand);

    List<Product> findProductsByBrandAndName(String brand, String name);

    List<Product> findProductsByCategoryNameAndBrand(String category, String brand);

    Long countProductsByBrandAndName(String brand, String name);
}

// NOTES

/*
 * THIS PERSONALIZED QUERY IS USED TO FIND PRODUCTS CONTAINS THE NAME
 * 
 * @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))"
 * )
 * List<Product> findProductsByName(@Param("name") String name);
 * 
 * THIS METHOD IS CREATED BY JPA
 * List<Product> findProductsByNameContainingIgnoreCase(String name);
 * 
 * THIS METHOD FIND PRODUCTS BUT NOT FILTER IF CONTAINS SOME WORD
 * List<Product> findProductsByName(String name);
 */
