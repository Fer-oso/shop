package com.ecommerce.shop.services.products;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.models.category.Category;
import com.ecommerce.shop.models.image.Image;
import com.ecommerce.shop.models.mappers.CategoryMapper;
import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.models.products.Product;
import com.ecommerce.shop.repository.products.ProductRepository;

import com.ecommerce.shop.services.category.ICategoryService;
import com.ecommerce.shop.services.images.IImageService;
import com.ecommerce.shop.services.products.exceptions.ProductsNotFoundException;
import com.ecommerce.shop.services.products.exceptions.ProductNotFoundException;

@Service
@Transactional
public class ProductServiceImp implements IProductService {

    ProductRepository productRepository;

    ICategoryService categoryService;

    IImageService imageService;

    ProductMapper productMapper;
    CategoryMapper categoryMapper;

    public ProductServiceImp(ProductRepository productRepository, ICategoryService categoryService,
            ProductMapper productMapper, CategoryMapper categoryMapper, IImageService imageService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.imageService = imageService;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO, List<MultipartFile> filesImage) {

        return Optional.of(productDTO).map(dto -> {

            Product product = productMapper.mapDTOToEntity(dto);

            Category category = categoryService.findCategoryByName(dto.getCategory().getName());

            List<Image> images = imageService.saveImage(filesImage);

            product.setCategory(category);

            product.setImages(images);

            return productMapper.mapEntityToDTO(productRepository.save(product));

        }).orElseThrow(() -> new NullPointerException("Product cant be null properties"));

    }

    @Override
    public ProductDTO update(ProductDTO productDTO, List<MultipartFile> filesImage, Long productId) {

        return productRepository.findById(productId)
                .map(product -> {

                    System.out.println(product);

                    product = productMapper.mapDTOToEntity(productDTO);

                    if (productDTO.getCategory() != null) {

                        Category category = categoryService.findCategoryByName(productDTO.getCategory().getName());

                        product.setCategory(category);
                    }

                    if (productDTO.getImages() == null) {

                        product.setImages(productRepository.findById(productId).get().getImages());
                    }

                    List<Image> images = imageService.saveImage(filesImage);

                    product.getImages().addAll(images);

                    return productMapper.mapEntityToDTO(productRepository.save(product));
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
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

        if (productList.isEmpty()) {

            throw new ProductsNotFoundException("No products in database");
        }

        return productList.stream().map(product -> productMapper.mapEntityToDTO(product)).toList();
    }

    @Override
    public List<ProductDTO> findProductsByName(String name) {

        if ("__ALL__".equalsIgnoreCase(name)) {

            return findAll();
        }

        List<Product> productList = productRepository.findProductsByName(name);

        if (productList.isEmpty()) {

            throw new ProductsNotFoundException("No products with the name " + name);
        }

        return productList.stream()
                .map(product -> productMapper.mapEntityToDTO(product))
                .toList();
    }

    @Override
    public List<ProductDTO> findProductsByBrand(String brand) {

        if ("__ALL__".equalsIgnoreCase(brand)) {

            return findAll();
        }

        List<Product> productList = productRepository.findProductsByBrand(brand);

        if (productList.size() == 0) {

            throw new ProductsNotFoundException("No products in database with that brand " + brand);
        }

        return productList.stream()
                .map(product -> productMapper.mapEntityToDTO(product)).toList();
    }

    @Override
    public List<ProductDTO> findProductsByCategoryName(String category) {

        if ("__ALL__".equalsIgnoreCase(category)) {

            return findAll();
        }

        List<Product> productList = productRepository.findProductsByCategoryName(category);

        if (productList.size() == 0) {

            throw new ProductsNotFoundException("No products in database with that Category");
        }

        return productList.stream()
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
