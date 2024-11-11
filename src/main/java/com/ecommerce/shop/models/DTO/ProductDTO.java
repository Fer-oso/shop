package com.ecommerce.shop.models.DTO;

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
public class ProductDTO {

    private Long id;

    private String name;

    private String brand;

    private String description;

    private double price;

    private int stock;

   // private boolean available;

    private String code;

    private CategoryDTO category;
}
