package com.ecommerce.shop.services.sales.order;

import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;

public interface IOrderService {

    OrderDTO save(OrderDTO orderDTO);

    OrderDTO findByOrderNumber(String orderNumber);

}
