package com.ecommerce.shop.controllers.shoppingCart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.services.shoppingcart.IShoppingCartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("${api.prefix}/shoppingCart")
public class ShoppingCartController {

    IShoppingCartService shoppingCartService;

    public ShoppingCartController(IShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping()
    public ResponseEntity<?> createShoppingCart(@RequestBody OrderDTO orderDTO) {
        

        return ResponseEntity.ok(shoppingCartService.save(orderDTO));
    }
    
    @GetMapping()
    public ResponseEntity<?> getShoppingCarts(){
        return ResponseEntity.ok(shoppingCartService.findAll());
    }
    
}
