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

    private String fullname;

    private String email;

    private String address;

    @JsonProperty("phonenumber")
    private int phoneNumber;

    @JsonProperty("shoppingcart")
    @JsonIgnoreProperties("buyer")
    private List<ShoppingCartDTO> shoppingCart;

    private UserBuyerDTO user;
}
