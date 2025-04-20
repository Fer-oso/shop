package com.ecommerce.shop.controllers.shoppingCart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;
import com.ecommerce.shop.services.shoppingcart.IShoppingCartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.prefix}/shoppingcart")
public class ShoppingCartController {

    IShoppingCartService shoppingCartService;

    public ShoppingCartController(IShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping()
    public ResponseEntity<?> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCart) {

        return ResponseEntity.ok(shoppingCartService.save(shoppingCart));
    }

    @GetMapping()
    public ResponseEntity<?> getShoppingCarts() {
        return ResponseEntity.ok(shoppingCartService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findShoppingCartById(@PathVariable String id) {
        return ResponseEntity.ok(shoppingCartService.findById(id));
    }
}
