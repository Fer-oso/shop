package com.ecommerce.shop.models.DTO.buyer;

import java.util.List;

import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
public class BuyerDTO {

    @Size(max = 100)
    private String firstname;

    @Size(max = 100)
    private String lastname;

    @Email(message = "Email inválido")
    private String email;

    @Size(max = 255)
    private String address; // si es obligatoria, agregale @NotBlank

    @Valid
    private PhoneDTO phone; // @Valid para que cascadee las validaciones propias de PhoneDTO

    @JsonProperty("shopping-cart")
    @JsonIgnoreProperties("buyer")
    private List<ShoppingCartDTO> shoppingCarts;

    private UserBuyerDTO user;
}
