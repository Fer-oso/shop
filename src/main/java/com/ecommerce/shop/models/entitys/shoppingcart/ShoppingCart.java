package com.ecommerce.shop.models.entitys.shoppingcart;

import java.util.List;

import com.ecommerce.shop.models.entitys.buyer.Buyer;
import com.ecommerce.shop.models.entitys.products.ProductShoppingCart;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private Buyer buyer;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinTable(name = " shopping_cart_product", joinColumns = @JoinColumn(name = "shopping_cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductShoppingCart> products;

    private int total;
}
