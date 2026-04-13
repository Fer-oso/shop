package com.ecommerce.shop.services.order;

import com.ecommerce.shop.models.DTO.mercadopago.PaymentOrderDTO;
import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;

public interface IOrderService {

    OrderDTO save(PaymentOrderDTO paymentOrderDTO);

    OrderDTO findByOrderNumber(String orderNumber);

}
