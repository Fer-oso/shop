package com.ecommerce.shop.models.DTO.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class ProductShoppingCartDTO {

    private Long id;
    
    @JsonIgnoreProperties({"stock","available"})
    private ProductDTO product;

    private int quantity;

    private int subtotal;
}
