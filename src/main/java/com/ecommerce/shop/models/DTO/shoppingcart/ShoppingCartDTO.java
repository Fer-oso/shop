package com.ecommerce.shop.models.DTO.shoppingcart;

import java.util.List;

import com.ecommerce.shop.models.DTO.buyer.BuyerDTO;
import com.ecommerce.shop.models.DTO.product.ProductShoppingCartDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class ShoppingCartDTO {
    
    private Long id;
    @JsonIgnoreProperties("shopping-cart")
    private BuyerDTO buyer;

    @JsonProperty("products")
    private List<ProductShoppingCartDTO> products;

    private int total;
}
