package com.ecommerce.shop.services.sales.order;

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

        Order order = orderMapper.mapDTOToEntity(orderDTO);

        order.setOrderNumber("ORD-" + Math.round(Math.random() * 1000000));

        return orderMapper.mapEntityToDTO(orderRepository.save(order));

    }

    public OrderDTO findByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        return orderMapper.mapEntityToDTO(order);
    }

}
