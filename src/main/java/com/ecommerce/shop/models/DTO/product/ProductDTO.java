package com.ecommerce.shop.models.DTO.product;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.shop.models.DTO.category.CategoryDTO;
import com.ecommerce.shop.models.DTO.image.ImageDTO;

import jakarta.persistence.Column;
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

    private int price;

    private int stock;

    private boolean available;

    private String code;

    private CategoryDTO category;

    private List<ImageDTO> images;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt ;

    private LocalDateTime deletedAt;
}
