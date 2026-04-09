package com.ecommerce.shop.models.DTO.mercadopago;

import com.mercadopago.client.common.PhoneRequest;

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
public class BuyerOrderDTO {
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String address;

    private PhoneRequest phone;
}
