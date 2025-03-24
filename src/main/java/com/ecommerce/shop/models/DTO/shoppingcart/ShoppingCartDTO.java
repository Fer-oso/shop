package com.ecommerce.shop.models.DTO.shoppingcart;

import com.ecommerce.shop.models.DTO.buyer.BuyerDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ShoppingCartDTO extends ShoppingCartBaseDTO {

    private Long id;

    @JsonIgnoreProperties("shopping-cart")
    private BuyerDTO buyer;

    private int total;
}
