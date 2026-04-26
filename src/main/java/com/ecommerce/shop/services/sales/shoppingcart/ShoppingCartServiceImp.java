package com.ecommerce.shop.services.sales.shoppingcart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.product.ProductShoppingCartDTO;
import com.ecommerce.shop.models.DTO.shoppingcart.ShoppingCartDTO;
import com.ecommerce.shop.models.entitys.buyer.Buyer;
import com.ecommerce.shop.models.entitys.products.Product;
import com.ecommerce.shop.models.entitys.products.ProductShoppingCart;
import com.ecommerce.shop.models.entitys.shoppingcart.ShoppingCart;
import com.ecommerce.shop.models.mappers.ProductMapper;
import com.ecommerce.shop.models.mappers.ShoppingCartMapper;
import com.ecommerce.shop.models.mappers.buyer.BuyerMapper;
import com.ecommerce.shop.models.mappers.product.ProductShoppingCartMapper;
import com.ecommerce.shop.repository.shoppingcart.ShoppingCartRepository;
import com.ecommerce.shop.services.products.productsShoppingCart.IProductShoppingCartService;
import com.ecommerce.shop.services.products.productsStore.IProductService;
import com.ecommerce.shop.services.sales.buyer.IBuyerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShoppingCartServiceImp implements IShoppingCartService {

    ShoppingCartRepository shoppingCartRepository;
    ShoppingCartMapper shoppingCartMapper;

    IBuyerService buyerService;
    BuyerMapper buyerMapper;

    IProductShoppingCartService productShoppingCartService;
    ProductShoppingCartMapper productShoppingCartMapper;

    IProductService productService;
    ProductMapper productMapper;

    public ShoppingCartServiceImp(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper,
            IBuyerService buyerService, BuyerMapper buyerMapper,
            IProductShoppingCartService productShoppingCartService,
            ProductShoppingCartMapper productShoppingCartMapper, IProductService productService,
            ProductMapper productMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.buyerService = buyerService;
        this.buyerMapper = buyerMapper;
        this.productShoppingCartService = productShoppingCartService;
        this.productShoppingCartMapper = productShoppingCartMapper;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Override
    public ShoppingCartDTO save(ShoppingCartDTO shoppingCartDTO) {

        return Optional.of(shoppingCartDTO).map(dto -> {

            Buyer buyer = buyerService.saveAndGetEntity(dto.getBuyer());

            List<ProductShoppingCart> productShoppingCartList = createProductShoppingCartList(
                    shoppingCartDTO.getProducts());

            ShoppingCart shoppingCart = ShoppingCart.builder()
                    .shoppingCartId(dto.getShoppingCartId())
                    .buyer(buyer)
                    .products(productShoppingCartList)
                    .total(dto.getTotal())
                    .build();

            shoppingCartRepository.save(shoppingCart);

            return shoppingCartMapper.mapEntityToDTO(shoppingCart);

        }).orElseThrow(() -> new UnsupportedOperationException("Error saving shoppingCart"));
    }

    @Override
    public ShoppingCartDTO findByShoppingCartId(String shoppingCartId) {

        return shoppingCartRepository.findByShoppingCartId(
                shoppingCartId).map(shoppingCartMapper::mapEntityToDTO)
                .orElseThrow(() -> new UnsupportedOperationException("ShoppingCart not found"));
    }

    @Override
    public ShoppingCartDTO update(ShoppingCartDTO shoppingCartDTO, String id) {

        return shoppingCartRepository.findByShoppingCartId(id).map(shoppingCart -> {

            List<ProductShoppingCart> productShoppingCartList = createProductShoppingCartList(
                    shoppingCartDTO.getProducts());

            Buyer buyer = buyerMapper.mapDTOToEntity(
                    buyerService.update(shoppingCartDTO.getBuyer(), shoppingCartDTO.getBuyer().getUser().getId()));

            shoppingCart.setBuyer(buyer);

            shoppingCart.setProducts(productShoppingCartList);

            shoppingCart.setTotal(shoppingCartDTO.getTotal());

            return shoppingCartMapper.mapEntityToDTO(shoppingCartRepository.save(shoppingCart));

        }).orElseThrow(() -> new UnsupportedOperationException("ShoppingCart not found"));
    }

    @Override
    public String deleteById(String shoppingCartId) {

        return shoppingCartRepository.findByShoppingCartId(shoppingCartId).map(shoppingCart -> {

            shoppingCartRepository.delete(shoppingCart);

            return "Shopping Cart: " + shoppingCart.getShoppingCartId() + " deleted succesfully with id: "
                    + shoppingCartId;

        }).orElseThrow(
                () -> new UnsupportedOperationException("Shopping Cart not found with that id: " + shoppingCartId));
    }

    @Override
    public List<ShoppingCartDTO> findAll() {

        return shoppingCartRepository.findAll().stream()
                .map(shoppingCart -> shoppingCartMapper.mapEntityToDTO(shoppingCart)).collect(Collectors.toList());
    }

    private List<ProductShoppingCart> createProductShoppingCartList(
            List<ProductShoppingCartDTO> productShoppingCartListDTO) {

        List<ProductShoppingCart> shoppingCartList = productShoppingCartListDTO.stream().map(productShoppingCartDTO -> {

            Product product = productMapper
                    .mapDTOToEntity(productService.findById(productShoppingCartDTO.getProduct().getId()));

            ProductShoppingCart productShoppingCart = ProductShoppingCart.builder()
                    .product(product)
                    .quantity(productShoppingCartDTO.getQuantity())
                    .subtotal(productShoppingCartDTO.getSubtotal())
                    .build();

            return productShoppingCart;

        }).collect(Collectors.toList());

        return shoppingCartList;
    }

}
