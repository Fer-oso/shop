package com.ecommerce.shop.models.shoppingcart;

import java.util.List;

import com.ecommerce.shop.models.buyer.Buyer;
import com.ecommerce.shop.models.products.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

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
@Entity
public class ShoppingCart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id",referencedColumnName = "id",nullable = false)
    private Buyer buyer;

    @ManyToMany
    @JoinTable(name = " shopping_cart_product", joinColumns = @JoinColumn(name = "shopping_cart_id"),inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<Product> productsList;

    private Long finalPrice;
}
