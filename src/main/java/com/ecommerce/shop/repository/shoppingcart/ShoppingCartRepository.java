package com.ecommerce.shop.repository.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shop.models.shoppingcart.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

}
