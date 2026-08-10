package com.ecommerce.shop.repository.shoppingcart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shop.models.entitys.shoppingcart.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByShoppingCartId(String shoppingCartId);
}
