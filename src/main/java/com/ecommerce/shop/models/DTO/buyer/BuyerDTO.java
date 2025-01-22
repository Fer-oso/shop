package com.ecommerce.shop.models.DTO.buyer;

import com.ecommerce.shop.models.DTO.users.UserDTO;
import com.ecommerce.shop.models.shoppingcart.ShoppingCart;

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
public class BuyerDTO {

    private Long id;

    private String fullName;

    private String email;

    private String address;

    private int phoneNumber;

    private ShoppingCart shoppingCart;

    private UserDTO user;
}
