package com.ecommerce.shop.models.DTO.shoppingcart;

import java.util.List;

import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.models.DTO.buyer.BuyerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ShoppingCartDTO {
    
    private Long id;

    private BuyerDTO buyer;

    private List<ProductDTO> productsList;

    private Long finalPrice;
}
