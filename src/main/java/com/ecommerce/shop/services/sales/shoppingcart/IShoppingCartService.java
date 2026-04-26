package com.ecommerce.shop.services.sales.shoppingcart;

import java.util.List;

import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;

public interface IShoppingCartService {

    ShoppingCartDTO save(ShoppingCartDTO orderDTO);

    ShoppingCartDTO findByShoppingCartId(String id);

    ShoppingCartDTO update(ShoppingCartDTO shoppingCartDTO, String id);

    String deleteById(String id);

    List<ShoppingCartDTO> findAll();
}
