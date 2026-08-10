package com.ecommerce.shop.services.sales.shoppingcart;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.product.ProductDTO;
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
import com.ecommerce.shop.services.products.IProductService;
import com.ecommerce.shop.services.sales.buyer.IBuyerService;
import com.ecommerce.shop.services.sales.shoppingcart.exceptions.ShoppingCartAlreadyExistsException;
import com.ecommerce.shop.services.sales.shoppingcart.exceptions.ShoppingCartNotFoundException;
import com.ecommerce.shop.services.sales.shoppingcart.exceptions.ShoppingCartPersistenceException;
import com.ecommerce.shop.services.sales.shoppingcart.productsShoppingCart.IProductShoppingCartService;

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

    IProductService<ProductDTO> productService;
    ProductMapper productMapper;

    public ShoppingCartServiceImp(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper,
            IBuyerService buyerService, BuyerMapper buyerMapper,
            IProductShoppingCartService productShoppingCartService,
            ProductShoppingCartMapper productShoppingCartMapper, IProductService<ProductDTO> productService,
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

        Buyer buyer = buyerService.saveAndGetEntity(shoppingCartDTO.getBuyer());

        List<ProductShoppingCart> productShoppingCartList = createProductShoppingCartList(
                shoppingCartDTO.getProducts());

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .shoppingCartId(shoppingCartDTO.getShoppingCartId())
                .status(shoppingCartDTO.getStatus())
                .buyer(buyer)
                .products(productShoppingCartList)
                .build();

        try {
            shoppingCart = shoppingCartRepository.save(shoppingCart);
        } catch (DataIntegrityViolationException ex) {
            throw new ShoppingCartAlreadyExistsException(shoppingCartDTO.getShoppingCartId());
        } catch (DataAccessException ex) {
            Logger.getLogger(ShoppingCartServiceImp.class.getName())
                    .severe("Error saving shopping cart: " + ex.getMessage());
            throw new ShoppingCartPersistenceException("No se pudo guardar el carrito", ex);
        }

        return shoppingCartMapper.mapEntityToDTO(shoppingCart);

    }

    @Override
    public ShoppingCartDTO findByShoppingCartId(String shoppingCartId) {

        return shoppingCartRepository.findByShoppingCartId(
                shoppingCartId).map(shoppingCartMapper::mapEntityToDTO)
                .orElseThrow(
                        () -> new ShoppingCartNotFoundException("Shopping cart not found with ID: " + shoppingCartId));
    }

    @Override
    public ShoppingCartDTO update(ShoppingCartDTO shoppingCartDTO, String id) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByShoppingCartId(id)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found with ID: " + id));

        List<ProductShoppingCart> productShoppingCartList = createProductShoppingCartList(
                shoppingCartDTO.getProducts());

        Buyer buyer = buyerMapper.mapDTOToEntity(buyerService
                .update(buyerMapper.mapEntityToDTO(shoppingCart.getBuyer()), shoppingCart.getBuyer().getId()));

        shoppingCart.setBuyer(buyer);
        shoppingCart.setProducts(productShoppingCartList);
        shoppingCart.setStatus(shoppingCartDTO.getStatus());

        return shoppingCartMapper.mapEntityToDTO(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public String deleteById(String shoppingCartId) {

        return shoppingCartRepository.findByShoppingCartId(shoppingCartId).map(shoppingCart -> {

            shoppingCartRepository.delete(shoppingCart);

            return "Shopping Cart: " + shoppingCart.getShoppingCartId() + " deleted succesfully with id: "
                    + shoppingCartId;

        }).orElseThrow(
                () -> new ShoppingCartNotFoundException("Shopping cart not found with ID: " + shoppingCartId));
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
                    .build();

            return productShoppingCart;

        }).collect(Collectors.toList());

        return shoppingCartList;
    }

}
