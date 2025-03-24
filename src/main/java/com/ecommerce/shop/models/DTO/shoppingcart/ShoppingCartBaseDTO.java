package com.ecommerce.shop.models.DTO.shoppingcart;

import java.util.List;

import com.ecommerce.shop.models.DTO.product.ProductShoppingCartDTO;
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
@ToString
@EqualsAndHashCode
@Builder
public class ShoppingCartBaseDTO {

    @JsonProperty("products")
    private List<ProductShoppingCartDTO> products;
}
