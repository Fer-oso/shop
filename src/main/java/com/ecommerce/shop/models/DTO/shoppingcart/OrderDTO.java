package com.ecommerce.shop.models.DTO.shoppingcart;

import com.ecommerce.shop.models.entitys.shoppingcart.ShoppingCart;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class OrderDTO {

    private String orderNumber;

    private String status;

    private ShoppingCartDTO shoppingCart;

    private Integer total;
}
