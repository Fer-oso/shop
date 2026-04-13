package com.ecommerce.shop.models.DTO.buyer;

import java.util.List;

import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;
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
public class BuyerDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String address;

    @JsonProperty("phone")
    private PhoneDTO phone;

    @JsonProperty("shopping-cart")
    @JsonIgnoreProperties("buyer")
    private List<ShoppingCartDTO> shoppingCart;

    private UserBuyerDTO user;
}
