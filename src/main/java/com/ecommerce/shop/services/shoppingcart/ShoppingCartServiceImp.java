package com.ecommerce.shop.services.shoppingcart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.shoppingcart.OrderDTO;
import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;
import com.ecommerce.shop.models.entitys.buyer.Buyer;
import com.ecommerce.shop.models.entitys.products.Product;
import com.ecommerce.shop.models.entitys.products.ProductShoppingCart;
import com.ecommerce.shop.models.entitys.shoppingcart.ShoppingCart;
import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.models.mappers.ShoppingCartMapper;
import com.ecommerce.shop.models.mappers.buyer.BuyerMapper;
import com.ecommerce.shop.models.mappers.product.ProductShoppingCartMapper;
import com.ecommerce.shop.repository.buyers.BuyerRepository;
import com.ecommerce.shop.repository.shoppingcart.ShoppingCartRepository;
import com.ecommerce.shop.services.buyer.IBuyerService;
import com.ecommerce.shop.services.products.productsShoppingCart.IProductShoppingCartService;
import com.ecommerce.shop.services.products.productsStore.IProductService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShoppingCartServiceImp implements IShoppingCartService {

    ShoppingCartRepository shoppingCartRepository;
    ShoppingCartMapper shoppingCartMapper;

    IBuyerService buyerService;
    BuyerMapper buyerMapper;
    BuyerRepository buyerRepository;

    IProductShoppingCartService productShoppingCartService;
    ProductShoppingCartMapper productShoppingCartMapper;

    IProductService productService;
    ProductMapper productMapper;

    public ShoppingCartServiceImp(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper,
            IBuyerService buyerService, BuyerMapper buyerMapper, BuyerRepository buyerRepository,
            IProductShoppingCartService productShoppingCartService,
            ProductShoppingCartMapper productShoppingCartMapper, IProductService productService,
            ProductMapper productMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.buyerService = buyerService;
        this.buyerMapper = buyerMapper;
        this.buyerRepository = buyerRepository;
        this.productShoppingCartService = productShoppingCartService;
        this.productShoppingCartMapper = productShoppingCartMapper;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Override
    public ShoppingCartDTO save(OrderDTO orderDTO) {

        return Optional.of(orderDTO).map(dto -> {

            Buyer buyer = buyerMapper.mapDTOToEntity(buyerService.save(orderDTO.getBuyer()));

            ShoppingCart shoppingCart = shoppingCartMapper.mapDTOToEntity(dto.getShoppingCart());

            if (buyer.getShoppingCart() == null) {

                buyer.setShoppingCart(new ArrayList<ShoppingCart>());
            }

            buyer.getShoppingCart().add(shoppingCart);

            shoppingCart.setBuyer(buyer);

            shoppingCartRepository.save(shoppingCart);

            List<ProductShoppingCart> productShoppingCartList = orderDTO.getShoppingCart().getProductsList().stream()
                    .map(productShoppingCartDTO -> {

                        Product product = productMapper
                                .mapDTOToEntity(productService.findById(productShoppingCartDTO.getProduct().getId()));

                        System.out.println("PRODUCTO ENCONTRADO" + product);

                        ProductShoppingCart productShoppingCart = ProductShoppingCart.builder()
                                .product(product)
                                .shoppingCart(shoppingCart)
                                .quantity(productShoppingCartDTO.getQuantity())
                                .subtotal(productShoppingCartDTO.getSubtotal())
                                .build();

                        return productShoppingCart;

                    }).collect(Collectors.toList());

            shoppingCart.setProductsList(productShoppingCartList);

            shoppingCartRepository.save(shoppingCart);

            return (shoppingCartMapper.mapEntityToDTO(shoppingCart));

        }).orElseThrow(() -> new UnsupportedOperationException("Error saving shoppingCart"));
    }

    @Override
    public ShoppingCartDTO findById(Long id) {

        return shoppingCartRepository.findById(id).map(shoppingCartMapper::mapEntityToDTO)
                .orElseThrow(() -> new UnsupportedOperationException("ShoppingCart not found"));
    }

    @Override
    public ShoppingCartDTO update(ShoppingCartDTO t, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public String deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public List<ShoppingCartDTO> findAll() {

        return shoppingCartRepository.findAll().stream()
                .map(shoppingCart -> shoppingCartMapper.mapEntityToDTO(shoppingCart)).collect(Collectors.toList());
    }

}
