package com.ecommerce.shop.models.entitys.products;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "bakeables")
@DiscriminatorValue("BAKEABLES")
public abstract class Bakeable extends Product {

    private int servings;

    @ElementCollection
    @CollectionTable(name = "bakeable_ingredients", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "ingredients")
    private List<String> ingredients;

    @ElementCollection
    @CollectionTable(name = "bakeable_features", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "feature")
    private List<String> features;
}
