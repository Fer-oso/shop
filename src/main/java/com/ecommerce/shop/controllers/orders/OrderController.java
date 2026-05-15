package com.ecommerce.shop.controllers.orders;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.controllers.responsesModels.ResponseSuccessModel;
import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.services.sales.order.IOrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<ResponseSuccessModel> save(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseSuccessModel.builder()
                .status("CREATED")
                .code(201)
                .response(orderService.save(orderDTO))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<ResponseSuccessModel> findByOrderNumber(@PathVariable String orderNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code(200)
                .response(orderService.findByOrderNumber(orderNumber))
                .timestamp(LocalDateTime.now())
                .build());
    }

}
