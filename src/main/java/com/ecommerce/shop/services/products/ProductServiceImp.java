package com.ecommerce.shop.services.products;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.repository.products.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ProductServiceImp implements IProductService {

    ProductRepository productRepository;

    ProductMapper productMapper;

    public ProductServiceImp(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {

        return Optional.ofNullable(productDTO)
                .map(dto -> productRepository.save(productMapper.mapDTOToEntity(dto)))
                .map(product -> productMapper.mapEntityToDTO(product))
                .orElseThrow(() -> new IllegalArgumentException("Product DTO canot be null"));
    }

    @Override
    public ProductDTO update(ProductDTO productDTO, Long id) {

        return productRepository.findById(id)
                .map(product -> {
                    productMapper.updateEntityFromDTO(productDTO, product);

                    product.setId(id);

                    return productRepository.save(product);
                })
                .map(product -> productMapper.mapEntityToDTO(product))
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id"));
    }

    @Override
    public ProductDTO findById(Long id) {

        return productRepository.findById(id)
                .map(product -> productMapper.mapEntityToDTO(product))
                .orElseThrow(() -> new EntityNotFoundException("Product not found with that id"));
    }

    @Override
    public void deleteById(Long id) {

        productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> findAll() {

        return productRepository.findAll().stream().map(product -> productMapper.mapEntityToDTO(product)).toList();
    }

}
