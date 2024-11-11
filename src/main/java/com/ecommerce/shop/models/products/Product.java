package com.ecommerce.shop.models.products;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.shop.models.category.Category;
import com.ecommerce.shop.models.image.Image;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "products")
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name",nullable = false)
    private String name;

    private String brand;

    @Column(length = 500)
    private String description;

    private BigDecimal price;

    private int stock;

    @Builder.Default
    private boolean available = true;

    @Column(name = "product_code", nullable = false)
    private String code;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    //if the product is deleted images be deleted along
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> images;
}
