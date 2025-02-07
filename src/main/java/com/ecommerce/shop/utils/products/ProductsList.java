package com.ecommerce.shop.utils.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.ecommerce.shop.models.entitys.category.Category;
import com.ecommerce.shop.models.entitys.products.Product;
import com.ecommerce.shop.services.images.IImageService;

public class ProductsList {

    Category category;

    @Autowired
    IImageService imageService;

    public ProductsList(Category category) {
        this.category = category;
    }

    public List<Product> getProductsList() {

        Product product1 = Product.builder().name("Intel i9 10900k").price(500).code("Intel1234").category(category)
                .brand("Intel").stock(10).build();

        Product product2 = Product.builder().name("Intel i10 10900k").price(500).code("Intel1234")
                .category(category)
                .brand("Intel").stock(10).build();

        Product product3 = Product.builder().name("Intel i9 10900k").price(500).code("Intel1234").category(category)
                .brand("Intel").stock(10).build();

        Product product4 = Product.builder().name("Intel i10 10900k").price(500).code("Intel1234")
                .category(category)
                .brand("Intel").stock(10).build();

        Product product5 = Product.builder().name("Intel i9 10900k").price(500).code("Intel1234").category(category)
                .brand("Intel").stock(10).build();

        Product product6 = Product.builder().name("Intel i10 10900k").price(500).code("Intel1234")
                .category(category)
                .brand("Intel").stock(10).build();

        Product product7 = Product.builder().name("Intel i9 10900k").price(500).code("Intel1234").category(category)
                .brand("Intel").stock(10).build();

        Product product8 = Product.builder().name("Intel i10 10900k").price(500).code("Intel1234")
                .category(category)
                .brand("Intel").stock(10).build();

        Product product9 = Product.builder().name("Intel i9 10900k").price(500).code("Intel1234").category(category)
                .brand("Intel").stock(10).build();

        Product product10 = Product.builder().name("Intel i10 10900k").price(500).code("Intel1234")
                .category(category)
                .brand("Intel").stock(10).build();

        return List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                product10);
    }

}
