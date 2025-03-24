package com.ecommerce.shop.models.DTO.mercadopago;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class MPResponseDTO {

    private Integer statusCode;
    private Map<String, List<String>> headers;
    private String content;

}
