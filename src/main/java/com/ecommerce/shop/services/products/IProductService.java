package com.ecommerce.shop.services.products;

import java.util.List;

import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.services.interfaces.ICrudService;

public interface IProductService extends ICrudService<ProductDTO, Long> {

    List<ProductDTO> findProductsByName(String name);
    List<ProductDTO> findProductsByCategory(String category);
    List<ProductDTO> findProductsByBrand(String brand);

    List<ProductDTO> findProductsByBrandAndName(String brand, String name);
    List<ProductDTO> findProductsByCategoryAndBrand(String category, String brand);

    Long countProductsByBrandAndName(String brand, String name);

}
