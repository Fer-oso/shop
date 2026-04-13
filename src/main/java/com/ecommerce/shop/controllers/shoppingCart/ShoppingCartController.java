package com.ecommerce.shop.controllers.shoppingCart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;
import com.ecommerce.shop.services.shoppingcart.IShoppingCartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.prefix}/shoppingcart")
public class ShoppingCartController {

    IShoppingCartService shoppingCartService;

    public ShoppingCartController(IShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping()
    public ResponseEntity<?> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {

        return ResponseEntity.status(201).body(shoppingCartService.save(shoppingCartDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getShoppingCarts() {
        return ResponseEntity.ok(shoppingCartService.findAll());
    }

    @GetMapping("/{shoppingCartId}")
    public ResponseEntity<?> findByShoppingCartId(@PathVariable String shoppingCartId) {
        return ResponseEntity.ok(shoppingCartService.findByShoppingCartId(shoppingCartId));
    }

    @PutMapping()
    public ResponseEntity<?> updateShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {

        return ResponseEntity.ok(shoppingCartService.update(shoppingCartDTO, shoppingCartDTO.getShoppingCartId()));
    }

    @DeleteMapping("/{shoppingCartId}")
    public ResponseEntity<?> deleteShoppingCart(@PathVariable String shoppingCartId) {

        return ResponseEntity.ok(shoppingCartService.deleteById(shoppingCartId));
    }
}
