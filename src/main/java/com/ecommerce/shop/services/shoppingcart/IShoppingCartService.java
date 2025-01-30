package com.ecommerce.shop.services.shoppingcart;

import java.util.List;

import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;


public interface IShoppingCartService {
    
    ShoppingCartDTO save(OrderDTO orderDTO);

    ShoppingCartDTO findById(Long id);

    ShoppingCartDTO update(ShoppingCartDTO shoppingCartDTO, Long id);

    String deleteById(Long id) ;

    List<ShoppingCartDTO> findAll();
}
