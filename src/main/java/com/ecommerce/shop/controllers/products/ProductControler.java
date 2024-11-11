package com.ecommerce.shop.controllers.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.services.products.IProductService;

@RestController
@RequestMapping("/api/shop/products")
public class ProductControler {

    IProductService productService;

    public ProductControler(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDTO));

    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.update(productDTO, id));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findProductById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }
    
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteById(id));
    }

    @GetMapping()
    ResponseEntity<?> findAllProducts() {

        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("/names/{name}")
    ResponseEntity<?> findProductsByName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductsByName(name));
    }

    @GetMapping("/brand/{brand}")
    ResponseEntity<?> findProductsByBrand(@PathVariable String brand){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductsByBrand(brand));
    }

    @GetMapping("/categories/{category}")
    ResponseEntity<?> findProductsByCategoryName(@PathVariable String category) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductsByCategoryName(category));
    }

    @GetMapping("/brand/{brand}/name/{name}")
    ResponseEntity<?> findProductsByBrandAndName(@PathVariable String brand, @PathVariable String name){

        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductsByBrandAndName(brand, name));
    }
}
