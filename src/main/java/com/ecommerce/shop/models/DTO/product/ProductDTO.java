package com.ecommerce.shop.models.DTO.product;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.shop.models.DTO.category.CategoryDTO;
import com.ecommerce.shop.models.DTO.image.ImageDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@SuperBuilder
public class ProductDTO {

    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String name;

    @NotBlank(message = "La marca del producto es obligatorio")
    @Size(max = 50, message = "La marca no puede superar los 50 caracteres")
    private String brand;

    @Size(max = 2000, message = "La descripcion no puede superar los 2000 caracteres")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private boolean available;

    @NotBlank(message = "El codigo del producto es obligatorio")
    @Size(max = 40, message = "El codigo no puede superar los 40 caracteres")
    private String code;

    private CategoryDTO category;

    private Integer weight;

    private String destacable;

    private String rating;

    private List<ImageDTO> images;
}
