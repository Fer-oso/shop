package com.ecommerce.shop.models.DTO.mercadopago;

import java.util.List;

import com.mercadopago.resources.preference.PreferenceItem;

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
public class PaymentOrderDTO {

    private Long id;

    private String shoppingCartId;

    private List<PreferenceItem> products;

    private BuyerOrderDTO buyer;

    private Integer total;
}
