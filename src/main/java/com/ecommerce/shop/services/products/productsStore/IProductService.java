package com.ecommerce.shop.services.products.productsStore;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.DTO.product.ProductDTO;

public interface IProductService {

    ProductDTO save(ProductDTO productDTO, List<MultipartFile> files);

    ProductDTO findById(Long productId);

    ProductDTO update(ProductDTO productDTO, List<MultipartFile> files, Long productId);

    String deleteById(Long id);

    List<ProductDTO> findAll();

    List<ProductDTO> findProductsByName(String name);

    List<ProductDTO> findProductsByCategoryName(String category);

    List<ProductDTO> findProductsByBrand(String brand);

    List<ProductDTO> findProductsByBrandAndName(String brand, String name);

    List<ProductDTO> findProductsByCategoryAndBrand(String category, String brand);

    Long countProductsByBrandAndName(String brand, String name);


}
