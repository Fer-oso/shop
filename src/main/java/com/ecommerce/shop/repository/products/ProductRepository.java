package com.ecommerce.shop.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.products.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
