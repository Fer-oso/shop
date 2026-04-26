package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.models.entitys.orders.Order;

public class OrderMapper implements IObjectMapper<Order, OrderDTO> {

    ModelMapper mapper;

    public OrderMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Order mapDTOToEntity(OrderDTO orderDTO) {
        return mapper.map(orderDTO, Order.class);
    }

    @Override
    public OrderDTO mapEntityToDTO(Order order) {
        return mapper.map(order, OrderDTO.class);
    }

}
