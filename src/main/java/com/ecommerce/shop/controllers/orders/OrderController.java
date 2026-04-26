package com.ecommerce.shop.controllers.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.services.sales.order.IOrderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.prefix}/orders")
@PreAuthorize("isAuthenticated()")
public class OrderController {

    IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> save(@RequestBody OrderDTO orderDTO) {

        return ResponseEntity.status(201).body(orderService.save(orderDTO));
    }

}
