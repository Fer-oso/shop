package com.ecommerce.shop.repository.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shop.models.entitys.orders.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNumber(String orderNumber);

}
