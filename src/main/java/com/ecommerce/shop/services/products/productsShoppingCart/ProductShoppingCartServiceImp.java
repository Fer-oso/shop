package com.ecommerce.shop.services.products.productsShoppingCart;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.product.ProductShoppingCartDTO;
import com.ecommerce.shop.models.entitys.products.ProductShoppingCart;
import com.ecommerce.shop.models.mappers.product.ProductShoppingCartMapper;
import com.ecommerce.shop.repository.products.ProductShoppingCartRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductShoppingCartServiceImp implements IProductShoppingCartService{

    ProductShoppingCartRepository productShoppingCartRepository;

    ProductShoppingCartMapper productShoppingCartMapper;

    public ProductShoppingCartServiceImp(ProductShoppingCartRepository productShoppingCartRepository,
            ProductShoppingCartMapper productShoppingCartMapper) {
        this.productShoppingCartRepository = productShoppingCartRepository;
        this.productShoppingCartMapper = productShoppingCartMapper;
        
    }

    @Override
    public ProductShoppingCartDTO save(ProductShoppingCartDTO productShoppingCartDTO) {
        
        return Optional.of(productShoppingCartDTO).map( dto ->{

            ProductShoppingCart productShoppingCart = productShoppingCartMapper.mapDTOToEntity(dto);
            
            return productShoppingCartMapper.mapEntityToDTO(productShoppingCartRepository.save(productShoppingCart));
            
        }).orElseThrow(() -> new UnsupportedOperationException("Error saving productShoppingCart"));
    }

    @Override
    public ProductShoppingCartDTO findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ProductShoppingCartDTO update(ProductShoppingCartDTO t, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public String deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public List<ProductShoppingCartDTO> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
