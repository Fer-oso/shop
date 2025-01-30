package com.ecommerce.shop.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.entitys.products.ProductShoppingCart;

@Repository
public interface ProductShoppingCartRepository extends JpaRepository<ProductShoppingCart, Long> {
    
}
