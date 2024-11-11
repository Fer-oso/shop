package com.ecommerce.shop.services.products;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.models.category.Category;
import com.ecommerce.shop.models.mappers.CategoryMapper;
import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.models.products.Product;
import com.ecommerce.shop.repository.products.ProductRepository;

import com.ecommerce.shop.services.category.ICategoryService;
import com.ecommerce.shop.services.products.exceptions.NoProductsFoundException;
import com.ecommerce.shop.services.products.exceptions.NullProductRequestException;
import com.ecommerce.shop.services.products.exceptions.ProductNotFoundException;

@Service
@Transactional
public class ProductServiceImp implements IProductService {

    ProductRepository productRepository;

    ICategoryService categoryService;

    ProductMapper productMapper;
    CategoryMapper categoryMapper;

    public ProductServiceImp(ProductRepository productRepository, ICategoryService categoryService,
            ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {

        return Optional.of(productDTO).map(dto -> {

            Product product = productMapper.mapDTOToEntity(dto);

            Category category = categoryService.findCategoryByName(dto.getCategory().getName());

            product.setCategory(category);

            return productMapper.mapEntityToDTO(productRepository.save(product));

        }).orElseThrow(() -> new NullPointerException("Product cant be null properties"));

    }

    @Override
    public ProductDTO update(ProductDTO productDTO, Long id) {

        return productRepository.findById(id)
                .map(product -> {

                    Category category = categoryService.findCategoryByName(productDTO.getCategory().getName());

                    product.setCategory(category);

                    return productRepository.save(product);
                })
                .map(product -> productMapper.mapEntityToDTO(product))

                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public ProductDTO findById(Long id) {

        return productRepository.findById(id)
                .map(product -> productMapper.mapEntityToDTO(product))
                .orElseThrow(() -> new ProductNotFoundException("Product not found with that id: " + id));
    }

    @Override
    public String deleteById(Long id) {

        return productRepository.findById(id).map(product -> {

            productRepository.deleteById(id);

            return "Product: " + product.getName() + " deleted succesfully with id: " + id;

        }).orElseThrow(() -> new ProductNotFoundException("Product not found with that id: " + id));
    }

    @Override
    public List<ProductDTO> findAll() {

        List<Product> productList = productRepository.findAll();

        if (productList.size() == 0) {

            throw new NoProductsFoundException("No products in database");
        }

        return productList.stream().map(product -> productMapper.mapEntityToDTO(product)).toList();
    }

    @Override
    public List<ProductDTO> findProductsByName(String name) {
        return findAll().stream().filter(product -> product.getName().toLowerCase().contains(name)).toList();
    }

    @Override
    public List<ProductDTO> findProductsByBrand(String brand) {
        return productRepository.findProductsByBrand(brand).stream()
                .map(product -> productMapper.mapEntityToDTO(product)).toList();
    }

    @Override
    public List<ProductDTO> findProductsByCategoryName(String category) {
        return productRepository.findProductsByCategoryName(category).stream()
                .map(product -> productMapper.mapEntityToDTO(product)).toList();
    }

    @Override
    public List<ProductDTO> findProductsByBrandAndName(String brand, String name) {
        return productRepository.findProductsByBrandAndName(brand, name).stream()
                .map(product -> productMapper.mapEntityToDTO(product)).toList();
    }

    @Override
    public List<ProductDTO> findProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findProductsByCategoryNameAndBrand(category, brand).stream()
                .map(product -> productMapper.mapEntityToDTO(product)).toList();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductsByBrandAndName(brand, name);
    }

}
