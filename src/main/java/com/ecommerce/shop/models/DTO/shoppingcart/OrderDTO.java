package com.ecommerce.shop.models.DTO.shoppingcart;

import com.ecommerce.shop.models.DTO.buyer.BuyerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDTO {

    private BuyerDTO buyer;

    private ShoppingCartDTO productsList;

    private int total;
}
