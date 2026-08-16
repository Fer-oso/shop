package com.ecommerce.shop.services.products;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.entitys.products.Product;

public interface IProductService<T> {

    Product findEntityById(Long id);

    T save(T productDTO, List<MultipartFile> files);

    T findById(Long productId);

    T update(T productDTO, List<MultipartFile> files, Long productId);

    String deleteById(Long id);

    T findByName(String name);

    List<T> findAll();

    List<T> findProductsByName(String name);

    List<T> findProductsByCategoryName(String category);

    List<T> findProductsByBrand(String brand);

    List<T> findProductsByBrandAndName(String brand, String name);

    List<T> findProductsByCategoryAndBrand(String category, String brand);

    Long countProductsByBrandAndName(String brand, String name);

}
