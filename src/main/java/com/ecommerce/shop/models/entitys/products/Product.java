package com.ecommerce.shop.models.entitys.products;

import java.util.List;

import com.ecommerce.shop.models.entitys.category.Category;
import com.ecommerce.shop.models.entitys.image.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "products")
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(length = 2000)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "available")
    private final boolean available = true;

    @Column(name = "code", nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // if the product is deleted images be deleted along
    // @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval =
    // true)
    @OneToMany(orphanRemoval = true)
    @JoinTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<Image> images;
}
