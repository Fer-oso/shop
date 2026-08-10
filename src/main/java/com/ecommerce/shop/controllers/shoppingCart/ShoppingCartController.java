package com.ecommerce.shop.controllers.shoppingCart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.controllers.responsesModels.ResponseSuccessModel;

import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;
import com.ecommerce.shop.services.sales.shoppingcart.IShoppingCartService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseSuccessModel<ShoppingCartDTO>> createShoppingCart(
            @Valid @RequestBody ShoppingCartDTO shoppingCartDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseSuccessModel.<ShoppingCartDTO>builder()
                        .status("CREATED")
                        .code(201)
                        .response(shoppingCartService.save(shoppingCartDTO))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping()
    public ResponseEntity<ResponseSuccessModel<List<ShoppingCartDTO>>> getShoppingCarts() {
        return ResponseEntity.ok(ResponseSuccessModel.<List<ShoppingCartDTO>>builder()
                .status("OK")
                .code(200)
                .response(shoppingCartService.findAll())
                .timestamp(LocalDateTime.now())
                .build());
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
