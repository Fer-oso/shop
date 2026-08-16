package com.ecommerce.shop.models.DTO.shoppingcart;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.shop.models.DTO.buyer.BuyerDTO;
import com.ecommerce.shop.models.DTO.buyer.UserBuyerDTO;
import com.ecommerce.shop.models.DTO.product.ProductShoppingCartDTO;

import com.ecommerce.shop.models.entitys.shoppingcart.ShoppingCartStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ShoppingCartDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_-]{21}$", message = "shoppingCartId inválido")
    private String shoppingCartId;

    private UserBuyerDTO user;

    @Builder.Default
    private ShoppingCartStatus status = ShoppingCartStatus.ACTIVE;

    @Valid
    private List<ProductShoppingCartDTO> products;

    @Valid
    @JsonIgnoreProperties("shopping-cart")
    private BuyerDTO buyer;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
