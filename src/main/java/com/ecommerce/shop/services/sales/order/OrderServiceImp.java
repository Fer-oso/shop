package com.ecommerce.shop.services.sales.order;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.models.entitys.orders.Order;
import com.ecommerce.shop.models.mappers.OrderMapper;
import com.ecommerce.shop.repository.orders.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImp implements IOrderService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;

    public OrderServiceImp(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public OrderDTO save(OrderDTO orderDTO) {

        return Optional.of(orderDTO).map(dto -> {

            Order order = orderMapper.mapDTOToEntity(dto);

            order.setOrderNumber("ORD-" + Math.round(Math.random() * 1000000));

            return orderMapper.mapEntityToDTO(orderRepository.save(order));

        }).orElseThrow(() -> new RuntimeException("Error al guardar la orden"));

    }

    public OrderDTO findByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        return orderMapper.mapEntityToDTO(order);
    }

}
